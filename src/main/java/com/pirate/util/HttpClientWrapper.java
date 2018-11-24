package com.pirate.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class HttpClientWrapper.
 */
public class HttpClientWrapper {

	/**
	 * Instantiates a new http client wrapper.
	 */
	private HttpClientWrapper() {

	}

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientWrapper.class);

	private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjb2duaXRpdmVzY2FsZS5jb20iLCJhdWQiOiJjb3J0ZXgiLCJzdWIiOiJoZ3VwdGEiLCJ0ZW5hbnQiOiJjc2luZGlhIiwiYmVhcmVyIjoicHVibGljIiwia2V5IjoiM056MzFIYzBoUjBNbWZZVzlDTnR0dmZLbk8wU0JmYVciLCJleHAiOjE1NDQyNDE1ODQsImFjbCI6eyIvdjIvYWNjb3VudHMvLioiOlsiREVOWSJdLCIvdjIvdGVuYW50cy8uKiI6WyJERU5ZIl0sIi92Mi90ZW5hbnRzL2N1cnJlbnQtdXNlci1kZXRhaWxzIjpbIlJFQUQiXSwiL3YzL2FnZW50cy9lbnZpcm9ubWVudHMvLioiOlsiREVOWSJdLCIvdjIvYWRtaW4vLioiOlsiREVOWSJdLCIvdjIvYWNjb3VudHMvdG9rZW5zLy4qIjpbIlJFQUQiLCJSVU4iLCJXUklURSJdLCIvdjMvY2F0YWxvZy8uKiI6WyJSRUFEIiwiUlVOIiwiV1JJVEUiLCJERUxFVEUiXSwiL3YzL2FnZW50cy9lbnZpcm9ubWVudHMvY29ydGV4L2RlZmF1bHQiOlsiUkVBRCIsIlJVTiIsIldSSVRFIl0sIi92Mi90ZW5hbnRzL3NlY3JldHMvLioiOlsiUkVBRCIsIlJVTiIsIldSSVRFIl0sIi4qIjpbIlJFQUQiLCJSVU4iLCJXUklURSJdfSwiaWF0IjoxNTQzMDMxOTg0fQ.krT3-iIrrzsRN3UEgwHnZIRiF0qBkZMNKgWJ47D1zUs";

	/**
	 * Do raw get.
	 *
	 * @param url
	 *            the url
	 * @param loginId
	 *            the login id
	 * @return the string
	 */
	public static String doRawGet(String url) {
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			request.setHeader(HttpHeaders.AUTHORIZATION, TOKEN);
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new CustomException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			LOG.error("Services not accesible - GET {}", url, e);
		}
		return null;
	}

	/**
	 * Do raw post.
	 *
	 * @param url
	 *            the url
	 * @param data
	 *            the data
	 * @param loginId
	 *            the login id
	 * @return the string
	 */
	public static String doRawPost(String url, Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(url);
			StringEntity postingString = new StringEntity(mapper.writeValueAsString(data));
			request.setEntity(postingString);
			request.setHeader("Content-type", "application/json");
			request.setHeader(HttpHeaders.AUTHORIZATION, TOKEN);
			request.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
			HttpResponse response = client.execute(request);
			System.out.println(response);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new CustomException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			LOG.error("Services not accesible - POST {}", url, e);
		}
		return null;
	}
}
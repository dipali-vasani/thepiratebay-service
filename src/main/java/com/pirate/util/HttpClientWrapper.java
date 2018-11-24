package com.pirate.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

	/**
	 * Do raw get.
	 *
	 * @param url
	 *            the url
	 * @param loginId
	 *            the login id
	 * @return the string
	 */
	public static String doRawGet(String url, String loginId) {
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url + "?loginid=" + loginId);
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
			LOG.error("Services not accesible - POST {}", url, e);
		}
		return null;
	}
}
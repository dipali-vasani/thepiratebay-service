package com.pirate.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;
import com.pirate.helper.RequestEntity;
import com.pirate.util.HttpClientWrapper;

public class Main {

	private static String URL = "https://api.cortex.insights.ai/v3/agents/";

	public static void main(String args[]) throws ApiException, InterruptedException, IOException, JSONException {
		RequestEntity req = new RequestEntity();
		Map<String, String> user = new HashMap<>();
		Map<String, Map<String, String>> testdata = new HashMap<>();
		user.put("user", "dipali");
		testdata.put("test_data", user);
		req.setInstanceId("5bf8bf079b9de0677dcca244");
		req.setPayload(testdata);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(req));

		String responsePost = HttpClientWrapper.doRawPost(URL + "default/store-recommendations/services/recommend",
				req);
		System.out.println(responsePost);
		JSONObject jsonObject = new JSONObject(responsePost);
		String responseGet = HttpClientWrapper.doRawGet(URL + "services/activations/" + jsonObject.get("activationId"));
		System.out.println(responseGet);
		if (responseGet != null) {
			JSONObject json = new JSONObject(responseGet);
			JSONObject responseObj = ((JSONObject) json.get("activation")).getJSONObject("response");
			JSONArray jsonArray = responseObj.getJSONArray("items");
			System.out.println(jsonArray.toString());
		}
	}

}

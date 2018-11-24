package com.pirate.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;

public class Main {

	public static void main(String args[]) throws ApiException, InterruptedException, IOException {
		Map<String, Map<String, Map<String, String>>> payload = new HashMap<>();
		Map<String, String> user = new HashMap<>();
		Map<String, Map<String, String>> testdata = new HashMap<>();
		user.put("user", "dipali");
		testdata.put("test_data", user);
		payload.put("payload", testdata);
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(payload));
		
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//System.out.println(gson.toJson(PlaceApi.getPlaces(17.4501509, 78.3584017, "Bata", "SHOE_STORE")));

	}

}

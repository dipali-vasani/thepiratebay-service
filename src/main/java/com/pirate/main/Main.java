package com.pirate.main;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.errors.ApiException;
import com.pirate.util.PlaceApi;

public class Main {

	public static void main(String args[]) throws ApiException, InterruptedException, IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(PlaceApi.getPlaces(17.4501509, 78.3584017, "Bata", "SHOE_STORE")));

	}

}

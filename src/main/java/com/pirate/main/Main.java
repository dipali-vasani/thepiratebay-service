package com.pirate.main;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.RankBy;
import com.pirate.util.ApiKey;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Param;
import se.walkercrou.places.Place;

public class Main {

	public static void main(String args[]) throws ApiException, InterruptedException, IOException {
		String apikey = System.getenv("apikey");
		ApiKey key = new ApiKey(apikey);
		// GeocodingResult[] results = getResults(key, "1600 Amphitheatre Parkway
		// Mountain View, CA 94043");

		// Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// System.out.println(gson.toJson(results[0].addressComponents));
		// getPlaces(key);

		LatLng location = new LatLng(17.4501509, 78.3584017);
		PlacesSearchResponse req = PlacesApi.nearbySearchQuery(key.context(), location)
				.radius(1000).rankby(RankBy.PROMINENCE).language("en")
				// .minPrice(PriceLevel.INEXPENSIVE)
				// .maxPrice(PriceLevel.EXPENSIVE)
				.name("Bata").openNow(true)
				.type(PlaceType.SHOE_STORE)
				// .type(PlaceType.AIRPORT)
				// .pageToken("next-page-token")
				.await();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(req.results));
		//System.out.println(req);

	}

	private static void getPlaces(ApiKey key) throws ApiException, InterruptedException, IOException {
		List<Place> places = places(key, "SLN Terminus");
		for (Place place : places) {
			Place detailedEmpireStateBuilding = place.getDetails(); // sends a GET request for more
			// details
			// Just an example of the amount of information at your disposal:
			System.out.println("Name: " + detailedEmpireStateBuilding.getName());
			System.out.println("Phone: " + detailedEmpireStateBuilding.getPhoneNumber());
			System.out.println("International Phone: " + detailedEmpireStateBuilding.getInternationalPhoneNumber());
			System.out.println("Website: " + detailedEmpireStateBuilding.getWebsite());
			System.out.println("Always Opened: " + detailedEmpireStateBuilding.isAlwaysOpened());
			System.out.println("Status: " + detailedEmpireStateBuilding.getStatus());
			System.out.println("Google Place URL: " + detailedEmpireStateBuilding.getGoogleUrl());
			System.out.println("Price: " + detailedEmpireStateBuilding.getPrice());
			System.out.println("Address: " + detailedEmpireStateBuilding.getAddress());
			System.out.println("Vicinity: " + detailedEmpireStateBuilding.getVicinity());
			System.out.println("Reviews: " + detailedEmpireStateBuilding.getReviews().size());
			System.out.println("Hours:\n " + detailedEmpireStateBuilding.getHours());
		}
	}

	private static GeocodingResult[] getResults(ApiKey key, String address)
			throws ApiException, InterruptedException, IOException {
		return GeocodingApi.geocode(key.context(), address).await();
	}

	private static List<Place> places(ApiKey key, String query) throws ApiException, InterruptedException, IOException {
		return key.client().getPlacesByQuery(query, GooglePlaces.MAXIMUM_RESULTS, Param.name("opennow").value(true));
	}

}

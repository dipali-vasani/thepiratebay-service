package com.pirate.util;

import java.io.IOException;

import com.google.maps.NearbySearchRequest;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.google.maps.model.RankBy;

public class PlaceApi {
	private PlaceApi() {

	}

	private static String apikey = System.getenv("apikey");
	private static ApiKey key = new ApiKey(apikey);
	private static int radius = 1000;

	public static PlacesSearchResult[] getPlaces(double lat, double lng, String search, String type)
			throws ApiException, InterruptedException, IOException {
		LatLng location = new LatLng(lat, lng);
		NearbySearchRequest nearbySearchRequest = PlacesApi.nearbySearchQuery(key.context(), location).radius(radius)
				.rankby(RankBy.PROMINENCE).language("en").name(search);
		PlacesSearchResponse req;
		if (type != null) {
			req = nearbySearchRequest.type(PlaceType.valueOf(type)).await();
		} else {
			req = nearbySearchRequest.await();
		}
		return req.results;
	}

	public static PlaceDetails getPlace(String placeid) throws ApiException, InterruptedException, IOException {
		return PlacesApi.placeDetails(key.context(), placeid).await();
	}

}

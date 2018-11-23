package com.pirate.util;

import com.google.maps.GeoApiContext;

public class ApiKey {
	/** internal context used to access the wrapped Google Service API */
	private static GeoApiContext context;

	/**
	 * Private constructor for creating an {@code ApiKey}
	 * 
	 * @param key
	 */
	public ApiKey(String key) {
		context = new GeoApiContext.Builder().apiKey(key).build();
	}

	/**
	 * Creates a new {@link ApiKey}
	 * 
	 * @param apiKey
	 */
	public static ApiKey create(String apiKey) {
		return new ApiKey(apiKey);
	}

	/**
	 * Returns the {@link GeoApiContext}
	 * 
	 * @return
	 */
	public GeoApiContext context() {
		return context;
	}
}
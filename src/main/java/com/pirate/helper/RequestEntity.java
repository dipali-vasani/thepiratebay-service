package com.pirate.helper;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
/**
 * Instantiates a new request entity.
 *
 * @param data
 *            the data
 */
@AllArgsConstructor
/**
 * Instantiates a new request entity.
 */
@NoArgsConstructor
public class RequestEntity {

	String instanceId;
	
	Map<String, Map<String, String>> payload;
}

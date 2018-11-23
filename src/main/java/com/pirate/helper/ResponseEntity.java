package com.pirate.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Instantiates a new response entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity {

	/** The data. */
	Object data;

	int statusCode;

	String errormessage;
}

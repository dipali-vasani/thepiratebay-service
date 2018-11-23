package com.pirate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pirate.service.WishListService;

@RestController
public class ItemController {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);

	/** The application audit service. */
	@Autowired
	private WishListService wishListService;

	/** The mapper. */
	private ObjectMapper mapper = new ObjectMapper();

}
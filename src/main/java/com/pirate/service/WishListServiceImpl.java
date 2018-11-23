package com.pirate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pirate.repository.WishListRepository;

@Service
public class WishListServiceImpl implements WishListService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(WishListServiceImpl.class);

	@Autowired
	WishListRepository wishListRepository;

	
}

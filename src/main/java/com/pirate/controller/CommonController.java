package com.pirate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pirate.helper.ItemDto;
import com.pirate.helper.PlacesDto;
import com.pirate.helper.ResponseEntity;
import com.pirate.helper.ResultDto;
import com.pirate.service.CommonService;

@RestController
@RequestMapping("/api")
public class CommonController {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CommonController.class);

	/** The application audit service. */
	@Autowired
	private CommonService commonService;

	@RequestMapping(value = "/saveitem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity saveItem(@RequestBody ItemDto itemDto) {
		return commonService.save(itemDto);
	}

	@RequestMapping(value = "/getplaces", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultDto getPlaces(@RequestBody PlacesDto placesDto, @RequestParam(value = "itemname") String itemname) {
		return commonService.getPlaces(placesDto, itemname);
	}

	@RequestMapping(value = "/getnotification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultDto getNotifications(@RequestBody PlacesDto placesDto) {
		return commonService.getNotifications(placesDto);
	}
}
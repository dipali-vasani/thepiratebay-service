package com.pirate.service;

import com.pirate.helper.ItemDto;
import com.pirate.helper.PlacesDto;
import com.pirate.helper.ResponseEntity;
import com.pirate.helper.ResultDto;

public interface CommonService {

	ResponseEntity save(ItemDto itemDto);

	ResultDto getPlaces(PlacesDto placesDto, String itemname);

	ResultDto getNotifications(PlacesDto placesDto);


}

package com.pirate.helper;

import java.util.List;

import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResult;
import com.pirate.entity.StoreOffers;

import lombok.Data;

@Data
public class ResultDto {

	PlacesSearchResult[] places;
	List<StoreOffers> ads;
	List<PlaceDetails> ad_details;
}

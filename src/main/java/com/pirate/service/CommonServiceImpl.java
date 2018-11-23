package com.pirate.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResult;
import com.pirate.entity.ItemTypeMapping;
import com.pirate.entity.WishList;
import com.pirate.helper.ItemDto;
import com.pirate.helper.PlacesDto;
import com.pirate.helper.ResponseEntity;
import com.pirate.helper.ResultDto;
import com.pirate.repository.ItemTypeMappingRepository;
import com.pirate.repository.PurchaseHistoryRepository;
import com.pirate.repository.StoreOffersRepository;
import com.pirate.repository.WishListRepository;
import com.pirate.util.PlaceApi;

@Service
public class CommonServiceImpl implements CommonService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Autowired
	WishListRepository wishListRepository;

	@Autowired
	StoreOffersRepository storeOffersRepository;

	@Autowired
	PurchaseHistoryRepository purchaseHistoryRepository;

	@Autowired
	ItemTypeMappingRepository itemTypeMappingRepository;

	@Override
	public ResponseEntity save(ItemDto itemDto) {
		ResponseEntity result = new ResponseEntity();
		result.setStatusCode(HttpStatus.OK.value());
		WishList entity = new WishList();
		entity.setItemName(itemDto.getItemName());
		entity.setUserid(itemDto.getUserid());
		entity.setIsDeleted(itemDto.getIsDone());
		wishListRepository.save(entity);
		result.setData(wishListRepository.findByIsDeleted(false));
		return result;
	}

	@Override
	public ResultDto getPlaces(PlacesDto placesDto, String itemname) {
		ResultDto resultDto = new ResultDto();
		try {
			resultDto.setPlaces(findPlaces(placesDto, itemname));
			resultDto.setAds(storeOffersRepository.findByItemNameOrderByRevenue(itemname));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultDto;
	}

	private PlacesSearchResult[] findPlaces(PlacesDto placesDto, String itemname)
			throws ApiException, InterruptedException, IOException {
		String type = getItemType(itemname);
		PlacesSearchResult[] result = PlaceApi.getPlaces(placesDto.getLat(), placesDto.getLng(), itemname, type);
		if (type == null && result.length > 0) {
			setItemType(itemname, result[0].types[0].toUpperCase());
		}
		return result;
	}

	private String getItemType(String itemname) {
		Optional<ItemTypeMapping> item = itemTypeMappingRepository.findByItemName(itemname);
		if (item.isPresent()) {
			return item.get().getType();
		} else {
			return null;
		}
	}

	private void setItemType(String itemname, String type) {
		ItemTypeMapping item = new ItemTypeMapping();
		item.setType(type);
		item.setItemName(itemname);
		itemTypeMappingRepository.save(item);
	}

	@Override
	public ResultDto getNotifications(PlacesDto placesDto) {
		ResultDto resultDto = new ResultDto();
		try {
			List<WishList> result = wishListRepository.findByIsDeleted(false);
			List<PlacesSearchResult> places = new ArrayList<>();
			for (WishList wish : result) {
				places.addAll(Arrays.asList(findPlaces(placesDto, wish.getItemName())));
			}
			PlacesSearchResult[] a = new PlacesSearchResult[places.size()];
			resultDto.setPlaces(places.toArray(a));
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		// List<StoreOffers> offers =
		// storeOffersRepository.findByItemNameAndBrandOrderByRevenue(itemname, brand);
		// resultDto.setAds(offers);
		return resultDto;
	}

}

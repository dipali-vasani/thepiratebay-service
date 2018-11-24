package com.pirate.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResult;
import com.pirate.entity.ItemTypeMapping;
import com.pirate.entity.PurchaseHistory;
import com.pirate.entity.StoreOffers;
import com.pirate.entity.WishList;
import com.pirate.helper.ItemDto;
import com.pirate.helper.PlacesDto;
import com.pirate.helper.ResponseEntity;
import com.pirate.helper.ResultDto;
import com.pirate.repository.ItemTypeMappingRepository;
import com.pirate.repository.PurchaseHistoryRepository;
import com.pirate.repository.StoreOffersRepository;
import com.pirate.repository.WishListRepository;
import com.pirate.util.HttpClientWrapper;
import com.pirate.util.PlaceApi;

@Service
public class CommonServiceImpl implements CommonService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CommonServiceImpl.class);

	private static String URL = "";

	@Autowired
	private WishListRepository wishListRepository;

	@Autowired
	private StoreOffersRepository storeOffersRepository;

	@Autowired
	private PurchaseHistoryRepository purchaseHistoryRepository;

	@Autowired
	private ItemTypeMappingRepository itemTypeMappingRepository;

	@Override
	public ResponseEntity save(ItemDto itemDto) {
		ResponseEntity result = new ResponseEntity();
		result.setStatusCode(HttpStatus.OK.value());
		Optional<WishList> entity = wishListRepository.findByUseridAndItemName(itemDto.getUserid(),
				itemDto.getItemName());
		WishList wishList = new WishList();
		if (entity.isPresent()) {
			wishList = entity.get();
			wishList.setIsDeleted(itemDto.getIsDone());
		} else {
			wishList.setItemName(itemDto.getItemName());
			wishList.setUserid(itemDto.getUserid());
			wishList.setIsDeleted(itemDto.getIsDone());
		}
		wishListRepository.save(wishList);
		result.setData(wishListRepository.findByUseridAndIsDeleted(itemDto.getUserid(), false));
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
			List<PlacesSearchResult> places = new ArrayList<>();
			List<StoreOffers> offers = new ArrayList<>();
			Map<String, Map<String, Map<String, String>>> payload = new HashMap<>();
			Map<String, String> user = new HashMap<>();
			Map<String, Map<String, String>> testdata = new HashMap<>();
			user.put("user", placesDto.getUserid());
			testdata.put("test_data", user);
			payload.put("payload", testdata);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValueAsString(payload);
			String itemIds = HttpClientWrapper.doRawPost(URL, payload);
			if (itemIds != null) {
				JSONArray jsonArray = new JSONArray(itemIds);
				for (int i = 0; i < jsonArray.length(); i++) {
					int id = Integer.parseInt(jsonArray.getString(i));
					Optional<PurchaseHistory> history = purchaseHistoryRepository.findById(id);
					if (history.isPresent()) {
						Optional<WishList> wishlist = wishListRepository.findByUseridAndItemNameAndIsDeleted(
								history.get().getUserid(), history.get().getItemName(), false);
						if (wishlist.isPresent()) {
							places.addAll(Arrays.asList(findPlaces(placesDto, wishlist.get().getItemName())));
							offers.addAll(storeOffersRepository.findByItemNameAndBrandOrderByRevenue(
									history.get().getItemName(), history.get().getBrand()));
						}
					}
				}
			}
			resultDto.setAds(offers);
			PlacesSearchResult[] a = new PlacesSearchResult[places.size()];
			resultDto.setPlaces(places.toArray(a));
		} catch (ApiException | InterruptedException | IOException | JSONException e) {
			e.printStackTrace();
		}
		return resultDto;
	}

}

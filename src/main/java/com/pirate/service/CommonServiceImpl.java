package com.pirate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pirate.entity.ItemTypeMapping;
import com.pirate.entity.WishList;
import com.pirate.helper.ItemDto;
import com.pirate.helper.ResponseEntity;
import com.pirate.repository.PurchaseHistoryRepository;
import com.pirate.repository.StoreOffersRepository;
import com.pirate.repository.WishListRepository;

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
	ItemTypeMapping itemTypeMapping;

	@Override
	public ResponseEntity save(ItemDto itemDto) {
		ResponseEntity result = new ResponseEntity();
		result.setStatusCode(HttpStatus.OK.value());
		WishList entity = new WishList();
		entity.setItemName(itemDto.getItemName());
		entity.setUserid(itemDto.getUserid());
		entity.setIsDeleted(itemDto.getIsDone());
		wishListRepository.save(entity);
		return result;
	}

}

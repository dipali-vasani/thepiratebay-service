package com.pirate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pirate.entity.WishList;
import com.pirate.helper.ItemDto;
import com.pirate.helper.ResponseEntity;

@Repository
public interface WishListRepository extends CrudRepository<WishList, Integer> {

}
package com.pirate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pirate.entity.WishList;

@Repository
public interface WishListRepository extends CrudRepository<WishList, Integer> {

	List<WishList> findByIsDeleted(Boolean isDeleted);
	
	List<WishList> findByUseridAndIsDeleted(String userid, Boolean isDeleted);

	Optional<WishList> findByUseridAndItemName(String userid, String itemName);

	Optional<WishList> findByUseridAndItemNameAndIsDeleted(String userid, String itemName, Boolean isDeleted);
}
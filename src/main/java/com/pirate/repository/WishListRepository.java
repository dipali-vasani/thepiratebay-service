package com.pirate.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pirate.entity.WishList;

@Repository
public interface WishListRepository extends CrudRepository<WishList, Integer> {

	List<WishList> findByIsDeleted(Boolean isDeleted);
}
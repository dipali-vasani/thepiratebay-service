package com.pirate.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pirate.entity.StoreOffers;

@Repository
public interface StoreOffersRepository extends CrudRepository<StoreOffers, Integer> {

	List<StoreOffers> findByItemNameAndBrandOrderByRevenue(String itemName, String brand);

	List<StoreOffers> findByItemNameOrderByRevenue(String itemname);

}
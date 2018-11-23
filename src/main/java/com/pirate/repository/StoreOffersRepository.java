package com.pirate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pirate.entity.StoreOffers;

@Repository
public interface StoreOffersRepository extends CrudRepository<StoreOffers, Integer> {

}
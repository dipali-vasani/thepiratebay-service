package com.pirate.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pirate.entity.ItemTypeMapping;

@Repository
public interface ItemTypeMappingRepository extends CrudRepository<ItemTypeMapping, Integer> {

	Optional<ItemTypeMapping> findByItemName(String itemname);

}
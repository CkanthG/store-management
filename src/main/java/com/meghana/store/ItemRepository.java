package com.meghana.store;

import com.meghana.store.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This Item Repository is used for all CRUD and DB operation on Item table in DB.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}

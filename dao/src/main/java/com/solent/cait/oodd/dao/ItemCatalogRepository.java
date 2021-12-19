package com.solent.cait.oodd.dao;

import com.solent.cait.oodd.dto.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.solent.cait.oodd.dto.Item;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ItemCatalogRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT * FROM Item WHERE type = :type", nativeQuery = true)
    public List<Item> FindByType(@Param("type") String type);

    @Query(value = "SELECT * FROM Item WHERE name = :name", nativeQuery = true)
    public List<Item> FindByName(@Param("name") String name);
}

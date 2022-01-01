package com.solent.cait.oodd.dao;

import com.solent.cait.oodd.dto.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.solent.cait.oodd.dto.Item;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 
 * @author caitlyn
 * ItemCatalog repo is the SQL database that will store all the items that people can buy from the storefront
 * 
 */
@Repository
public interface ItemCatalogRepository extends JpaRepository<Item, Long> {

    /**
     * 
     * @param type
     * @return 
     */
    @Query(value = "SELECT * FROM Item WHERE type = :type", nativeQuery = true)
    public List<Item> FindByType(@Param("type") String type);

    /**
     * 
     * @param name
     * @return 
     */
    @Query(value = "SELECT * FROM Item WHERE name = :name", nativeQuery = true)
    public List<Item> FindByName(@Param("name") String name);
}

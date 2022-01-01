package com.solent.cait.oodd.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.solent.cait.oodd.dto.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.solent.cait.oodd.dto.User;
import java.util.List;

/**
 * 
 * @author caitlyn
 * Invoice repository stores the Invoices at runtime in a JPA repo to be accessed 
 * by and admin or a user of that order
 */
@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice,Long>{
    
    /**
     * 
     * @param userIn
     * @return 
     */
    @Query(value = "SELECT * FROM Invoice WHERE username = :userIn", nativeQuery = true)
    public List<Invoice> FindByUsername(@Param("userIn")String userIn);
    
    /**
     * 
     * @param invoiceNum
     * @return 
     */
    @Query(value = "SELECT * FROM Invoice WHERE invoiceNumber = :invoiceNum", nativeQuery = true)
    public List<Invoice> FindByInoviceNum(@Param("invoiceNum")String invoiceNum);
}

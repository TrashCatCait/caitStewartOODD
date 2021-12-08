package com.solent.cait.oodd.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.solent.cait.oodd.dto.Invoice;

@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice,Long>{
    
}
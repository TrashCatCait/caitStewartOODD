package com.solent.cait.oodd.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.solent.cait.oodd.dto.Invoice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice,Long>{
}

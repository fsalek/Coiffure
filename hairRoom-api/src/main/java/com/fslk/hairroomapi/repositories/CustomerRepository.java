package com.fslk.hairroomapi.repositories;

import com.fslk.hairroomapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //List<Customer> findByHairdresser(Long idHair);
    //@Query("SELECT cust FROM Customer cust INNER JOIN cust.hairdresser hairdrs WHERE hairdrs.id = :id")
    //public List<Customer> findByHairdresser(@Param("id") Long idLevel);
}

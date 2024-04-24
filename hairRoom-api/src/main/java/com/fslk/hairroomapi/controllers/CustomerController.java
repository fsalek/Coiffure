package com.fslk.hairroomapi.controllers;

import com.fslk.hairroomapi.entities.Customer;
import com.fslk.hairroomapi.exception.ResourceNotFoundException;
import com.fslk.hairroomapi.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class CustomerController {
    public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerRepository repository;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        System.out.println("Get all Customers...");

        List<Customer> Customers = new ArrayList<>();
        repository.findAll().forEach(Customers::add);

        return Customers;
    }
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long customerId)
            throws ResourceNotFoundException {
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        return ResponseEntity.ok().body(customer);
    }
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer _customer = repository
                    .save(new Customer(null, customer.getFirstname(), customer.getLastname(),
                            customer.getPhone(), customer.getHairdresser()));
            return new ResponseEntity<>(_customer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        logger.info("Updating Customer with id {}", id);

        Customer currentCustomer = repository.findById(id).get();

        if (currentCustomer == null) {
            logger.error("Unable to update. Customer with id {} not found.", id);
            return new ResponseEntity(new ResourceNotFoundException("Unable to upate. Customer with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentCustomer.setFirstname(customer.getFirstname());
        currentCustomer.setLastname(customer.getLastname());
        currentCustomer.setPhone(customer.getPhone());
        currentCustomer.setHairdresser(customer.getHairdresser());

        repository.save(currentCustomer);
        return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);

    }
    @DeleteMapping("/customers/{id}")
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "id") Long CustomerId)
            throws ResourceNotFoundException {
        Customer Customer = repository.findById(CustomerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found  id :: " + CustomerId));
        repository.delete(Customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/customers/delete")
    public ResponseEntity<String> deleteAllcustomers() {
        System.out.println("Delete All customers...");
        repository.deleteAll();
        return new ResponseEntity<>("All customers have been deleted!", HttpStatus.OK);
    }


}

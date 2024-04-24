package com.fslk.hairroomapi.controllers;

import com.fslk.hairroomapi.entities.Client;
import com.fslk.hairroomapi.exception.ResourceNotFoundException;
import com.fslk.hairroomapi.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ClientController {
    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    ClientRepository repository;

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        System.out.println("Get all Clients...");

        List<Client> Clients = new ArrayList<>();
        repository.findAll().forEach(Clients::add);

        return Clients;
    }
    @GetMapping("/clients/{id}")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Long clientId)
            throws ResourceNotFoundException {
        Client client = repository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));
        return ResponseEntity.ok().body(client);
    }
    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        try {
            Client _client = repository
                    .save(new Client(null, client.getFirstname(), client.getLastname(), client.getPhone()));
            return new ResponseEntity<>(_client, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*@PutMapping(value = "/clients/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client tod) {
        System.out.println("Update Client with ID = " + id + "...");
        Optional<Client> ClientInfo = repository.findById(id);
        if (ClientInfo.isPresent()) {
            Client client = ClientInfo.get();
            client.setTitle(tod.getTitle());
            client.setState(tod.getState());
            client.setDescription(tod.getDescription());
            return new ResponseEntity<>(repository.save(tod), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
        logger.info("Updating Client with id {}", id);

        Client currentClient = repository.findById(id).get();

        if(currentClient == null) {
            logger.error("Unable to update. Client with id {} not found.", id);
            return new ResponseEntity<>(new ResourceNotFoundException("Unable to upate. Client with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentClient.setFirstname(client.getFirstname());
        currentClient.setLastname(client.getLastname());
        currentClient.setPhone(client.getPhone());

        repository.save(currentClient);
        return new ResponseEntity<Client>(currentClient, HttpStatus.OK);

    }
    /*@Autowired
    ClientRepository repository;

    @GetMapping(value = "/clients")
    public List<Client> listClients() {
        System.out.println("Get all hairdressers...");
        List<Client> clients = new ArrayList<>();
        repository.findAll().forEach(clients::add);
        return clients;
    }

    @GetMapping(value = "/clients/{id}")
    public Client getClientById(@PathVariable("id") Long id) {
        return repository.findById(id).get();
    }

    @PostMapping(value = "/clients")
    public Client createClient(@Validated @RequestBody Client client) {
        return repository.save(client);
    }

    @DeleteMapping("/clients/{id}")
    public Map<String, Boolean> deleteClient(@PathVariable(value = "id") Long ClientId)
            throws ResourceNotFoundException {
        Client Client = repository.findById(ClientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found  id :: " + ClientId));
        repository.delete(Client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/clients/delete")
    public ResponseEntity<String> deleteAllclients() {
        System.out.println("Delete All clients...");
        repository.deleteAll();
        return new ResponseEntity<>("All clients have been deleted!", HttpStatus.OK);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client cust) {
        System.out.println("Update Client with ID = " + id + "...");
        Optional<Client> ClientInfo = repository.findById(id);
        if (ClientInfo.isPresent()) {
            Client client = ClientInfo.get();
            client.setFirstname(cust.getFirstname());
            client.setLastname(cust.getLastname());
            client.setPhone(cust.getPhone());
            return new ResponseEntity<>(repository.save(cust), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
*/
}

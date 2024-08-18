package com.ds.customerservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api/v1")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	//@Autowired
	//Producer producer;

	@GetMapping("logout/{username}")
	public ResponseEntity<String> logout(@PathVariable String username) throws JsonProcessingException {

		//producer.sendEvent("LOGOUT", username);
		return ResponseEntity.ok(username + " LOGGED OUT");

	}
	
	@PostMapping("add/customer")
	public ResponseEntity<Customer> updateUserDetails(@RequestBody Customer customer) throws JsonProcessingException {
		if (customerRepository.findById(customer.getUsername()).isEmpty()) {
			customerRepository.save(customer);
			ObjectMapper objectMapper = new ObjectMapper();
			//producer.sendEvent("ADD_CUSTOMER", customer.getUsername());			
		}
		return ResponseEntity.ok(customer);
	}

	@GetMapping("customerids")
	public ResponseEntity<List<String>> getAllCustomerIds() {
		List<String> customers = customerRepository.findAll().stream().map(ids -> ids.getUsername())
				.collect(Collectors.toList());
		return ResponseEntity.ok(customers);
	}
	
	@GetMapping("customers")
	public ResponseEntity<String> getAllCustomers() throws JsonProcessingException {
		List<Customer> customers = customerRepository.findAll();
		ObjectMapper objectMapper = new ObjectMapper();
		String data =  objectMapper.writeValueAsString(customers);	
		return ResponseEntity.ok(data);
	}

}

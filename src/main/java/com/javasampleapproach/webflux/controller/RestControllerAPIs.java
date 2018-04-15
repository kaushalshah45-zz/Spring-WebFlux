package com.javasampleapproach.webflux.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.javasampleapproach.webflux.model.PRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.webflux.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping(value="/api/customer")
public class RestControllerAPIs {

	@Autowired
	DBConnExample dbConnExample;

	@Autowired
	CustRepo repository;

	private static final Logger log = LoggerFactory.getLogger(RestControllerAPIs.class);
	

    @GetMapping
    public Mono<List<PRef>> getAll() throws Exception{

		Thread t = Thread.currentThread();
		log.info(t.getId() + "   id   name: " + t.getName() + "  active:" + Thread.activeCount());
		Mono blockingWrapper = Mono.fromCallable(() -> dbConnExample.getCount());
		blockingWrapper = blockingWrapper.subscribeOn(Schedulers.elastic());
		return blockingWrapper;
    }

	@GetMapping(path = "/all")
	public List<PRef> getAllCust() throws Exception{

		Thread t = Thread.currentThread();
		log.info(t.getId() + "   id   name: " + t.getName());
		return dbConnExample.getCount();
	}

	@GetMapping(path = "/stream", produces = "application/stream+json")
	public Flux<Customer> getCustomerStream() throws Exception{
		Thread t = Thread.currentThread();
		log.info(t.getId() + "   id   name: " + t.getName() + "  active:" + Thread.activeCount());
		return this.repository.findCustomerByAge().log();
	}
	
	

}

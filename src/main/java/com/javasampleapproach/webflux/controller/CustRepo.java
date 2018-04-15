package com.javasampleapproach.webflux.controller;

import com.javasampleapproach.webflux.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface CustRepo extends ReactiveMongoRepository<Customer, Long>
{

    @Tailable
    default Flux<Customer> findCustomerByAge() throws Exception
    {
        Thread.sleep(15000);
        Thread t = Thread.currentThread();
        System.out.println(t.getId() + "   id   name: " + t.getName() + "  active:" + Thread.activeCount() + " inside dao");
        return findCustomerBy();
    }

    Flux<Customer> findCustomerBy();
}

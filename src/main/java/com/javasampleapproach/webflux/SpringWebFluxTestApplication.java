package com.javasampleapproach.webflux;

import com.javasampleapproach.webflux.model.Customer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import reactor.core.publisher.Flux;

import javax.sql.DataSource;
import java.time.Duration;


@SpringBootApplication
public class SpringWebFluxTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebFluxTestApplication.class, args);
	}


    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {

        return DataSourceBuilder.create().build();

    }


    @Bean
    public CommandLineRunner initData(MongoOperations mongo) {
        return (String... args) ->
        {
            mongo.dropCollection(Customer.class);
            mongo.createCollection(Customer.class, CollectionOptions.empty().size(1000000).capped());
            Flux.range(1, 100)
                    .map(i -> new Customer(i,"Person " + i, "lastname " + i, i+12))
                    .doOnNext(mongo::save)
                    .blockLast(Duration.ofSeconds(5));
        };
    }


}



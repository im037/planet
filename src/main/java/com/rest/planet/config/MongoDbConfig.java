package com.rest.planet.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public class MongoDbConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new MongoClient("localhost"), "b2w");
    }
}

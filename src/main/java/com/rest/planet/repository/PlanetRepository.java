package com.rest.planet.repository;

import com.rest.planet.domain.Planet;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlanetRepository extends MongoRepository<Planet, String> {

    Planet findByName(String name);

    Planet findById(ObjectId id);
}

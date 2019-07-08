package com.rest.planet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rest.planet.domain.Planet;
import com.rest.planet.repository.PlanetRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PlanetService {

    @Autowired
    private ApparitionsService apparitionsService;

    @Autowired
    PlanetRepository planetRepository;

    public Planet createPlanet(Map bodyMap) throws IOException {
        bodyMap.put("apparitions", apparitionsService.countApparitionsByPlanet((String) bodyMap.get("nome")));

        translateFields(bodyMap);

        Gson gson = new Gson();
        String json = gson.toJson(bodyMap);

        ObjectMapper mapper = new ObjectMapper();
        Planet planet = mapper.readValue(json, Planet.class);

        return planetRepository.save(planet);
    }

    public List<Planet> findAll() {
        return planetRepository.findAll();
    }

    public Planet findByName(String name) {
        return planetRepository.findByName(name);
    }

    public Planet findById(ObjectId planetId) {
        return planetRepository.findById(planetId);
    }

    public Planet findById(String planetId) {
        return findById(new ObjectId(planetId));
    }

    public Planet deletePlanet(String name) {
        Planet planet = planetRepository.findByName(name);
        if(planet != null) {
            planetRepository.delete(planet);
        }
        return planet;
    }

    public void translateFields(Map body){
        if(body.containsKey("nome")){
            body.put("name", body.get("nome"));
            body.remove("nome");
        }
        if(body.containsKey("clima")){
            body.put("climate", body.get("clima"));
            body.remove("clima");
        }
        if(body.containsKey("terreno")){
            body.put("terrain", body.get("terreno"));
            body.remove("terreno");
        }
    }
}

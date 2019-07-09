package com.rest.planet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rest.planet.domain.Planet;
import com.rest.planet.repository.PlanetRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class PlanetService {

    @Autowired
    private ApparitionsService apparitionsService;

    @Autowired
    PlanetRepository planetRepository;

    public Planet createPlanet(Map bodyMap) throws IOException {
        Map newBodyMap = translateFields(bodyMap);

        newBodyMap.put("apparitions", apparitionsService.countApparitionsByPlanet((String) newBodyMap.get("name")));

        Gson gson = new Gson();
        String json = gson.toJson(newBodyMap);

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

    public Planet deletePlanet(String nameOrId) {
        Planet planet = findByIdOrName(nameOrId);
        if(planet != null) {
            planetRepository.delete(planet);
        }
        return planet;
    }

    private Planet findByIdOrName(String nameOrId){
        try {
            Planet planet = planetRepository.findByName(nameOrId);
            if (planet == null) {
                planet = findById(nameOrId);
            }
            return planet;
        } catch (IllegalArgumentException iae) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map translateFields(Map<String, String> body){

        Map newBody = new HashMap();

        for(String key : body.keySet()){
            newBody.put(key.toLowerCase(), body.get(key));
        }

        if(newBody.containsKey("nome")){
            newBody.put("name", newBody.get("nome"));
            newBody.remove("nome");
        }
        if(newBody.containsKey("clima")){
            newBody.put("climate", newBody.get("clima"));
            newBody.remove("clima");
        }
        if(newBody.containsKey("terreno")){
            newBody.put("terrain", newBody.get("terreno"));
            newBody.remove("terreno");
        }

        return newBody;
    }
}

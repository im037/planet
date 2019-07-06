package com.rest.planet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlanetService {

    @Autowired
    private ApparitionsService apparitionsService;

    public Map addPlanet(Map body) {
        body.put("apparitions", apparitionsService.countApparitionsByPlanet((String) body.get("nome")));
        return body;
    }

    public String findAll() {
        return "Listar planetas";
    }

    public String findByName(String name) {
        return "Buscar por nome - " + name;
    }

    public String findById(Integer planetId) {
        return "Buscar por ID - " + planetId;
    }

    public String deletePlanet() {
        return "Remover planeta";
    }
}

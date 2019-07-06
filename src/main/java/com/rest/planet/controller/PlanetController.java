package com.rest.planet.controller;

import com.rest.planet.service.ApparitionsService;
import com.rest.planet.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/planet")
@RestController
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll() {
        return planetService.findAll();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Map addPlanet(Map body) {
        return planetService.addPlanet(body);
    }

    @RequestMapping(path = "/findByName/{name}", method = RequestMethod.GET)
    public String findByName(@PathVariable("name") String name) {
        return planetService.findByName(name);
    }

    @RequestMapping(path = "/findById/{planetId}", method = RequestMethod.GET)
    public String findById(@PathVariable("planetId") Integer planetId) {
        return planetService.findById(planetId);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public String deletePlanet() {
        return planetService.deletePlanet();
    }

}

package com.rest.planet.controller;

import com.rest.planet.service.ApparitionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/planet")
@RestController
public class PlanetController {

    @Autowired
    private ApparitionsService apparitionsService;

    @RequestMapping(path = "/apparitions/{planetName}", method = RequestMethod.GET)
    public Integer apparitions(@PathVariable("planetName") String planetName) {
        // Return some cliched textual content
        //Integer countApparitions = apparitionsService.countApparitionsByFilms(planetName);
        return apparitionsService.countApparitionsByPlanet(planetName);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Map addPlanet(Map body) {
        // Return some cliched textual content
        return body;
    }

    @RequestMapping(path = "/findAll", method = RequestMethod.GET)
    public String findAll() {
        // Return some cliched textual content
        return "Listar planetas";
    }

    @RequestMapping(path = "/findByName/{name}", method = RequestMethod.GET)
    public String findByName(@PathVariable("name") String name) {
        // Return some cliched textual content
        return "Buscar por nome - " + name;
    }

    @RequestMapping(path = "/findById/{planetId}", method = RequestMethod.GET)
    public String findById(@PathVariable("planetId") Integer planetId) {
        // Return some cliched textual content
        return "Buscar por ID - " + planetId;
    }

    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public String deletePlanet() {
        // Return some cliched textual content
        return "Remover planeta";
    }

}

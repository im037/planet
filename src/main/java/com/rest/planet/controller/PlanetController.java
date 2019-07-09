package com.rest.planet.controller;

import com.rest.planet.domain.Planet;
import com.rest.planet.service.PlanetService;
import com.rest.planet.utils.PlanetResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * RestController to access game B2w
 *
 * @author leonardo.souza
 *
 * return <ResponseEntity> for all methods in this class
 */
@RequestMapping("/planet")
@RestController
public class PlanetController {

    @Autowired
    private PlanetService planetService;


    /**
     * get all Planets in Database
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity findAll() {

        PlanetResponse planetResponse = new PlanetResponse();

        try {
            List<Planet> planetList = planetService.findAll();
            if(planetList.isEmpty()){
                planetResponse.setDescription("Nenhum Planeta Encontrado");
                return ResponseEntity.status(HttpStatus.OK).body(planetResponse);
            } else {
                planetResponse.setPlanets(planetList);
                planetResponse.setDescription("Foram Encontrados " + planetList.size() + " Planetas");
                return  ResponseEntity.status(HttpStatus.OK).body(planetResponse);
            }
        } catch (Exception e) {
            planetResponse.setDescription("Erro ao processar sua requisição");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(planetResponse);
        }
    }

    /**
     * create Planet in Database
     *
     * @param body is a {@link Map} collection that contains {@link String} as key
     * and {@link String} as value.
     *
     * keys can be only {"nome", "terreno", "clima"} or {"name", "terrain", "climate"}
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity createPlanet(@RequestBody Map body) {
        PlanetResponse planetResponse = new PlanetResponse();

        try {
            Planet planet = planetService.createPlanet(body);
            List<Planet> planetList = new ArrayList<>();
            planetList.add(planet);
            planetResponse.setPlanets(planetList);
            planetResponse.setDescription("Planeta Criado com Sucesso");
            return ResponseEntity.status(HttpStatus.OK).body(planetResponse);
        } catch (DuplicateKeyException dke){
            planetResponse.setDescription("Planeta já cadastrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(planetResponse);
        } catch (ConstraintViolationException cve){
            planetResponse.setDescription(cve.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(planetResponse);
        } catch (Exception e){
            e.printStackTrace();
            planetResponse.setDescription("Erro ao processar sua requisição. Verifique os dados enviados.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(planetResponse);
        }
    }

    /**
     * Find Planet in Database
     *
     * @param name is a {@link String} to find Planet by Name
     *
     */
    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public ResponseEntity getByName(@PathVariable("name") String name) {
        PlanetResponse planetResponse = new PlanetResponse();

        try {
            Planet planet = planetService.findByName(name);
            return responseForNameOrId(planet, name);
        } catch (Exception e) {
            planetResponse.setDescription("Erro ao processar sua requisição");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(planetResponse);
        }
    }

    /**
     * Find Planet in Database
     *
     * @param name is a {@link String} to find Planet by Name
     *
     */
    @RequestMapping(path = "/findByName/{name}", method = RequestMethod.GET)
    public ResponseEntity findByName(@PathVariable("name") String name) {
        return getByName(name);
    }

    /**
     * Find Planet in Database
     *
     * @param planetId is a {@link String} to find Planet by ID
     *
     */
    @RequestMapping(path = "/findById/{planetId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable("planetId") String planetId) {
        PlanetResponse planetResponse = new PlanetResponse();

        try {
            Planet planet = planetService.findById(planetId);
            return responseForNameOrId(planet, planetId);
        } catch (Exception e) {
            planetResponse.setDescription("Erro ao processar sua requisição");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(planetResponse);
        }
    }

    /**
     * Delete Planet in Database
     *
     * @param nameOrId is a {@link String} to delete Planet by name or ID
     *
     */
    @RequestMapping(path = "/{nameOrId}", method = RequestMethod.DELETE)
    public ResponseEntity deletePlanet(@PathVariable("nameOrId") String nameOrId) {

        PlanetResponse planetResponse = new PlanetResponse();

        Planet planet = planetService.deletePlanet(nameOrId);
        if(planet != null){
            List<Planet> planetList = new ArrayList<>();
            planetList.add(planet);
            planetResponse.setPlanets(planetList);
            planetResponse.setDescription("Planeta Deletado com Sucesso");
            return ResponseEntity.status(HttpStatus.OK).body(planetResponse);
        } else {
            planetResponse.setDescription("Nenhum Planeta Encontrado com este Nome");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(planetResponse);
        }
    }

    private ResponseEntity responseForNameOrId(Planet planet, String nameOrId){
        PlanetResponse planetResponse = new PlanetResponse();
        if(planet != null){
            List<Planet> planetList = new ArrayList<>();
            planetList.add(planet);
            planetResponse.setPlanets(planetList);
            planetResponse.setDescription(planet.getName());
            return ResponseEntity.status(HttpStatus.OK).body(planetResponse);
        } else {
            planetResponse.setDescription("Planeta " + nameOrId + " não encontrado");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(planetResponse);
        }
    }

}

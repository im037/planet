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

import java.util.List;
import java.util.Map;

@RequestMapping("/planet")
@RestController
public class PlanetController {

    @Autowired
    private PlanetService planetService;

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

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public ResponseEntity createPlanet(@RequestBody Map body) {
        PlanetResponse planetResponse = new PlanetResponse();

        try {
            Planet planet = planetService.createPlanet(body);
            planetResponse.setPlanet(planet);
            planetResponse.setDescription("Planeta Criado com Sucesso");
            return ResponseEntity.status(HttpStatus.OK).body(planetResponse);
        } catch (DuplicateKeyException dke){
            planetResponse.setDescription("Planeta já cadastrado");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(planetResponse);
        } catch (Exception e){
            e.printStackTrace();
            planetResponse.setDescription("Erro ao processar sua requisição. Verifique os dados enviados.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(planetResponse);
        }
    }
    /*
    @RequestMapping(path = "/{name}", method = RequestMethod.GET)
    public ResponseEntity getByName(@PathVariable("name") String name) {
        return planetService.findByName(name);
    }

    @RequestMapping(path = "/findByName/{name}", method = RequestMethod.GET)
    public ResponseEntity findByName(@PathVariable("name") String name) {
        return planetService.findByName(name);
    }

    @RequestMapping(path = "/findById/{planetId}", method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable("planetId") String planetId) {
        return planetService.findById(planetId);
    }
    */
    @RequestMapping(path = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity deletePlanet(@PathVariable("name") String name) {

        PlanetResponse planetResponse = new PlanetResponse();

        Planet planet = planetService.deletePlanet(name);
        if(planet != null){
            planetResponse.setPlanet(planet);
            planetResponse.setDescription("Planeta Deletado com Sucesso");
            return ResponseEntity.status(HttpStatus.OK).body(planetResponse);
        } else {
            planetResponse.setDescription("Nenhum Planeta Encontrado com este Nome");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(planetResponse);
        }
    }

}

package com.rest.planet.service;

import org.springframework.stereotype.Service;

@Service
public class ApparitionsService {

    //realizar o contador de aparições por filmes é mais fácil que por planeta
    public Integer countApparitionsByFilms(String planetName){
        Integer countApparitions = 1024;
        //https://swapi.co/api/films/
        return countApparitions;
    }

    public Integer countApparitionsByPlanet(String planetName){
        Integer countApparitions = 0;
        //https://swapi.co/api/planets/
        return countApparitions;
    }
}

package com.rest.planet.utils;

import com.rest.planet.domain.Planet;

import java.util.List;

public class PlanetResponse {

    private String description;

    private List<Planet> planets;

    private Integer totalCount;

    public PlanetResponse() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
        this.totalCount = planets.size();
    }

    public Integer getTotalCount() {
        return totalCount;
    }
}

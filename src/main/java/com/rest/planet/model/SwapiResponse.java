package com.rest.planet.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class SwapiResponse {

    private Integer count;
    private String next;
    private String previous;
    private List<SwapiPlanet> results;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<SwapiPlanet> getResults() {
        return results;
    }

    public void setResults(List<SwapiPlanet> results) {
        this.results = results;
    }

    public static SwapiResponse buildSwapiResponse(byte[] bytes) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(bytes, SwapiResponse.class);
    }
}

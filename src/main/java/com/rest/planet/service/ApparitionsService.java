package com.rest.planet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.planet.model.SwapiPlanet;
import com.rest.planet.model.SwapiResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class ApparitionsService {

    @Value("${external.base.url}")
    private String externalBaseUrl;

    @Value("${external.resource.planets}")
    private String externalResourcePlanet;

    @Value("${external.resource.search}")
    private String externalResourceSearch;

    //https://swapi.co/api/planets/?search=
    private String buildExternalUrl() {
        return this.externalBaseUrl + this.externalResourcePlanet + this.externalResourceSearch;
    }

    private Response executeExternalUrlMethodGet(String externalUrl) throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(externalUrl).get().build();
        return client.newCall(request).execute();
    }

    public Integer countApparitionsByPlanet(String planetName){

        String externalUrl = this.buildExternalUrl() + planetName;
        Integer countApparitions = 0;

        try {
            Response response = executeExternalUrlMethodGet(externalUrl);

            if(response.isSuccessful()) {
                SwapiResponse swapiResponse = SwapiResponse.buildSwapiResponse(response.body().bytes());

                if(swapiResponse.getCount() > 0) {
                    List<SwapiPlanet> swapiPlanetList = swapiResponse.getResults();
                    for (SwapiPlanet swapiPlanet : swapiPlanetList) {
                        if(swapiPlanet.getName().equals(planetName))
                            countApparitions = swapiPlanet.getApparitions();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return countApparitions;
    }
}

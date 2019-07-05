package com.rest.planet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@PropertySource("classpath:application.properties")
public class ApparitionsService {

    @Value("${external.base.url}")
    private String externalBaseUrl;

    @Value("${external.resource.planets}")
    private String externalResourcePlanet;

    @Value("${external.resource.search}")
    private String externalResourceSearch;

    private String getExternalUrlResourceSearch() {
        //https://swapi.co/api/planets/?search=
        return this.externalBaseUrl + this.externalResourcePlanet + this.externalResourceSearch;
    }

    public Integer countApparitionsByPlanet(String planetName){// throws Exception {

        String url = this.getExternalUrlResourceSearch() + planetName;
        Integer countApparitions = 0;

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url).get().build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {

                ObjectMapper mapper = new ObjectMapper();

                Map mapResult = mapper.readValue(response.body().bytes(), Map.class);

//                result = {LinkedHashMap@9599}  size = 4
//                "count" -> {Integer@7781} 0
//                "next" -> null
//                "previous" -> null
//                "results" -> {ArrayList@9608}  size = 0
                //if(mapResult)
                countApparitions=3;
                /*if(swapiPlanetResponseBody.count > 0) {

                    List<SwapiPlanet> swapiPlanetList = swapiPlanetResponseBody.getResults();

                    externalPlanet = swapiPlanetList.get(0);

                    return externalPlanet;

                } else {

                    throw new SwapiValidationException(
                            HttpStatus.NOT_FOUND,
                            "Planeta inexistente na API Star Wars"
                    );
                }
                */
            } else {
                /*throw new SwapiValidationException(
                        HttpStatus.valueOf(response.code()),
                        response.message()
                );*/
                countApparitions=6;
            }

        /*} catch (SwapiValidationException SwapiException) {

            throw new SwapiValidationException(
                    SwapiException.getHttpStatus(),
                    SwapiException.getMessage(),
                    SwapiException
            );
        */
        }  catch (Exception exception) {

            /*throw new SwapiValidationException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro interno ao consultar a API Star Wars",
                    exception
            );*/
            countApparitions=9;
        }
        return countApparitions;
    }
}

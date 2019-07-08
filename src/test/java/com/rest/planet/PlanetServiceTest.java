package com.rest.planet;

import com.google.gson.Gson;
import com.rest.planet.domain.Planet;
import com.rest.planet.repository.PlanetRepository;
import com.rest.planet.service.ApparitionsService;
import com.rest.planet.service.PlanetService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MongoConfigTest.class, ApparitionsService.class, PlanetService.class,
        PlanetRepository.class})
public class PlanetServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    PlanetService planetService;

    @Autowired
    PlanetRepository planetRepository;

    @Before
    public void init(){
        planetRepository.deleteAll();
    }

    @Test
    public void translateFields() {
        Map body = new HashMap<String, String>();
        body.put("nome", "Terra");
        body.put("clima", "Temperado");
        body.put("terreno", "Florestal");


        planetService.translateFields(body);

        System.out.println(body);

        assertThat(body.get("nome")).isNull();
        assertThat(body.get("name")).isEqualTo("Terra");
        assertThat(body.get("clima")).isNull();
        assertThat(body.get("climate")).isEqualTo("Temperado");
        assertThat(body.get("terreno")).isNull();
        assertThat(body.get("terrain")).isEqualTo("Florestal");
    }

    @Test
    public void createPlanetShouldPersistData() throws Exception{
        String planetName = "Terra";
        Map body = new HashMap<String, String>();
        body.put("nome", planetName);
        body.put("clima", "Temperado");
        body.put("terreno", "Florestal");

        Planet planet = planetService.createPlanet(body);

        Planet planetSaved = planetRepository.findByName(planetName);
        assertThat(planet.getId()).isNotNull();
        assertThat(planetSaved.getName()).isEqualTo(planetName);
    }

    @Test
    public void findAllPlanetShouldGetAll(){
        Planet planet1 = new Planet("Hoth", "frozen",
                "tundra, ice caves, mountain ranges", 1);
        Planet planet2 = new Planet("Dagobah", "murky", "swamp, jungles", 3);
        Planet planet3 = new Planet("Terra", "Temperado", "Florestal", 0);

        planetRepository.save(planet1);
        planetRepository.save(planet2);
        planetRepository.save(planet3);

        List<Planet> planetList = planetService.findAll();

        assertThat(planetList.size()).isEqualTo(3);
    }

    @Test
    public void findByNameShouldGetPlanet(){
        String planetName = "Dagobah";
        Planet planet = new Planet(planetName, "murky", "swamp, jungles", 3);
        planetRepository.save(planet);

        Planet planetFound = planetService.findByName(planetName);
        assertThat(planetFound.getId()).isNotNull();
        assertThat(planetFound.getName()).isEqualTo(planetName);
    }

    @Test
    public void findByIdForStringShouldGetPlanet(){
        Planet planet = new Planet("Dagobah", "murky", "swamp, jungles", 3);
        planetRepository.save(planet);

        String _id = planet.getId();

        Planet planetFound = planetService.findById(_id);
        assertThat(planetFound.getId()).isEqualTo(planet.getId());
    }


    /*@Test
    public void findByIdForObjectIdShouldGetPlanet(){
        String planetName = "Dagobah";
        Planet planet = new Planet(planetName, "murky", "swamp, jungles", 3);
        planetRepository.save(planet);

        Map planetFound = planetService.findByName(planetName);
        assertThat(planetFound.get("id")).isNotNull();
        assertThat(planetFound.get("name")).isEqualTo(planetName);
    }*/
}

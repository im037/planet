package com.rest.planet;

import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.rest.planet.config.MongoDbConfig;
import com.rest.planet.domain.Planet;
import com.rest.planet.repository.PlanetRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MongoConfigTest.class})
public class PlanetTest {

    @Autowired
    PlanetRepository planetRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init(){
        planetRepository.deleteAll();
    }

    @Test
    public void createDagobahShouldPersistData(){
        Planet planet = new Planet("Dagobah", "murky", "swamp, jungles", 3);

        Planet planetSaved = planetRepository.save(planet);

        assertThat(planet.getId()).isEqualTo(planetSaved.getId());
    }

    @Test
    public void createDagobahShouldDuplicateKeyException(){
        expectedException.expect(DuplicateKeyException.class);
        Planet planet = new Planet("Dagobah", "murky", "swamp, jungles", 3);
        Planet planet2 = new Planet("Dagobah", "murky", "swamp, jungles", 3);

        planetRepository.save(planet);
        planetRepository.save(planet2);
    }

    @Test
    public void createHothShouldPersistData(){
        Planet planet = new Planet("Hoth", "frozen",
                "tundra, ice caves, mountain ranges", 1);

        Planet planetSaved = planetRepository.save(planet);

        assertThat(planet.getId()).isEqualTo(planetSaved.getId());
    }

    @Test
    public void createHothShouldConstraintsViolation(){
        expectedException.expect(ConstraintViolationException.class);
        Planet planet = new Planet();
        planet.setName("Hoth");

        Planet planetSaved = planetRepository.save(planet);
    }

    @Test
    public void updateHothShouldPersistData(){
        Planet planet = new Planet("Hoth", "frozen",
                "tundra, ice caves, mountain ranges", 3);

        Planet planetSaved = planetRepository.save(planet);
        planetSaved.setApparitions(1);
        planetRepository.save(planetSaved);

        assertThat(planet.getApparitions()).isEqualTo(1);
    }

    @Test
    public void deleteHothShouldPersistData(){
        Planet planet = new Planet("Hoth", "frozen",
                "tundra, ice caves, mountain ranges", 1);

        Planet planetSaved = planetRepository.save(planet);
        planetRepository.delete(planetSaved);

        assertThat(planetRepository.findById(planetSaved.getId())).isNull();
    }
}

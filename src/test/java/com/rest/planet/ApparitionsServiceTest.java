package com.rest.planet;

import com.rest.planet.service.ApparitionsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApparitionsServiceTest {

    @Autowired
    ApparitionsService apparitionsService;

    @Test
    public void countApparitionShouldZero(){
        String planetName = "a";
        Integer apparitions = apparitionsService.countApparitionsByPlanet(planetName);
        assertThat(apparitions).isEqualTo(0);
    }

    @Test
    public void countApparitionShouldOne(){
        String planetName = "Yavin IV";
        Integer apparitions = apparitionsService.countApparitionsByPlanet(planetName);
        assertThat(apparitions).isEqualTo(1);
    }

    @Test
    public void countApparitionShouldThree(){
        String planetName = "Dagobah";
        Integer apparitions = apparitionsService.countApparitionsByPlanet(planetName);
        assertThat(apparitions).isEqualTo(3);
    }
}

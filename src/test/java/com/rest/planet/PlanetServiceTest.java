package com.rest.planet;

import com.rest.planet.service.PlanetService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MongoConfigTest.class})
public class PlanetServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    PlanetService planetService;

    @Before
    public void init(){

    }
}

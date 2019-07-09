package com.rest.planet.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "planet")
public class Planet {

    public Planet(){}

    public Planet(String name, String climate, String terrain, Integer apparitions){
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
        this.apparitions = apparitions;
    }

    @Id
    ObjectId id;

    @Indexed(unique = true)
    @NotNull(message = "Nome não pode ser null")
    private String name;

    @NotNull(message = "Clima não pode ser null")
    private String climate;

    @NotNull(message = "Terreno não pode ser null")
    private String terrain;

    private Integer apparitions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Integer getApparitions() {
        return apparitions;
    }

    public void setApparitions(Integer apparitions) {
        this.apparitions = apparitions;
    }

    public String getId(){
        return id.toString();
    }

    /*@Override
    public String toString() {
        return "Planet{" +
                ", name='" + name + '\'' +
                ", climate='" + climate + '\'' +
                ", terrain='" + terrain + '\'' +
                ", apparitions=" + apparitions +
                '}';
    }*/
}

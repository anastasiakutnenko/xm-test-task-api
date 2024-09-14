
package org.xm.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class Character {
    @JsonProperty("birth_year")
    private String birthYear;
    @JsonProperty("created")
    private String created;
    @JsonProperty("edited")
    private String edited;
    @JsonProperty("eye_color")
    private String eyeColor;
    @JsonProperty("films")
    private List<String> films;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("hair_color")
    private String hairColor;
    @JsonProperty("height")
    private String height;
    @JsonProperty("homeworld")
    private String homeworld;
    @JsonProperty("mass")
    private String mass;
    @JsonProperty("name")
    private String name;
    @JsonProperty("skin_color")
    private String skinColor;
    @JsonProperty("species")
    private List<Object> species;
    @JsonProperty("starships")
    private List<String> starships;
    @JsonProperty("url")
    private String url;
    @JsonProperty("vehicles")
    private List<String> vehicles;
}


package org.xm.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(fluent = true)
public class Film {
    @JsonProperty("characters")
    private List<String> characters;
    @JsonProperty("created")
    private String created;
    @JsonProperty("director")
    private String director;
    @JsonProperty("edited")
    private String edited;
    @JsonProperty("episode_id")
    private Long episodeId;
    @JsonProperty("opening_crawl")
    private String openingCrawl;
    @JsonProperty("planets")
    private List<String> planets;
    @JsonProperty("producer")
    private String producer;
    @JsonProperty("release_date")
    private Date releaseDate;
    @JsonProperty("species")
    private List<String> species;
    @JsonProperty("starships")
    private List<String> starships;
    @JsonProperty("title")
    private String title;
    @JsonProperty("url")
    private String url;
    @JsonProperty("vehicles")
    private List<String> vehicles;
}

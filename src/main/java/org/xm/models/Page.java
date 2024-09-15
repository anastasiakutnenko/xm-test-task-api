
package org.xm.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;
@Data
@Accessors(fluent = true)
public class Page <T> {
    @JsonProperty
    private Long count;
    @JsonProperty
    private String next;
    @JsonProperty
    private String previous;
    @JsonProperty
    private List<Character> results;
}

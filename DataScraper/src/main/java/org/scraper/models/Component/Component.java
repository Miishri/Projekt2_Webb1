package org.scraper.models.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Component {
    private UUID id;
    private String title;
    private String image;
    private String description;
    private String producer;
    private String rating;
    @JsonProperty
    private String price;

    @Override
    public String toString() {
        return "Component{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", producer='" + producer + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}


package org.scraper.models.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Component {
    private String title;
    private String image;
    private String description;
    private String producer;
    private String rating;

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


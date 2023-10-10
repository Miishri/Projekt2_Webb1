package org.scraper.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.scraper.models.Component.Component;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
public class Ram extends Component {

    public static final String endpoint = "/rams";

    private String ramType;
    private String size;
    private Integer clock;
    private Integer sticks;
}

package org.scraper.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.scraper.models.Component.Component;

@Setter
@SuperBuilder
@Getter
@AllArgsConstructor
public class PSU extends Component {

    public static final String endpoint = "/psus";

    private Integer watt;
    private String size;
    private String efficiencyRating;
    private Integer pcie8;
    private Integer pcie6;
}

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

    private String watt;
    private String size;
    private String efficiencyRating;
    private String pcie8;
    private String pcie6;
}

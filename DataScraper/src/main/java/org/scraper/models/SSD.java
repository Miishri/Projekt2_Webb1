package org.scraper.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.scraper.models.Component.Component;

@SuperBuilder
@Getter
@Setter
public class SSD extends Component {

    public static final String endpoint = "/ssds";

    private String formFactor;
    private String protocol;
    private Integer size;
    private String NAND;
    private String controller;
}

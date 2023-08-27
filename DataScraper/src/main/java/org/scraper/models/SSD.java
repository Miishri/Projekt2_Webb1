package org.scraper.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class SSD extends Component{

    public static final String endpoint = "/ssds";

    private String formFactor;
    private String protocol;
    private Integer size;
    private String NAND;
    private String controller;

    public SSD(String title, String image, String description, String producer, String rating,
               String formFactor, String protocol, Integer size, String NAND, String controller) {
        super(title, image, description, producer, rating);
        this.formFactor = formFactor;
        this.protocol = protocol;
        this.size = size;
        this.NAND = NAND;
        this.controller = controller;
    }
}

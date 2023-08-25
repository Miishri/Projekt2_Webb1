package org.example.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class SSD extends Component{

    private String formFactor;
    private String protocol;
    private Integer size;
    private String NAND;
    private String controller;

    public SSD(String title, String image, String description,
               String producer, String formFactor, String protocol,
               Integer size, String NAND, String controller) {
        super(title, image, description, producer);
        this.formFactor = formFactor;
        this.protocol = protocol;
        this.size = size;
        this.NAND = NAND;
        this.controller = controller;
    }
}

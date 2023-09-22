package org.scraper.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.scraper.models.Component.Component;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
public class GPU extends Component {

    public static final String endpoint = "/gpus";

    private String length;
    private Double slots;
    private Integer eightPinConnectors;
    private Integer sixPinConnectors;
    private Integer HDMI;
    private Integer DP;
    private Integer DVI;
    private Integer VGA;
    private String MHZ;
    private String VRAM;
    private String TDP;
}


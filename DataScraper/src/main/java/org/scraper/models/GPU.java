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

    private Double slots;
    private Integer DP;
    private Integer DVI;
    private String MHZ;
    private String VRAM;
    private String TDP;
}


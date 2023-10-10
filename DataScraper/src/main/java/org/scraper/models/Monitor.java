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
public class Monitor extends Component {

    public static final String endpoint = "/displays";

    private String resolution;
    private String refreshRate;
    private String size;
    private String panelType;
    private String responseMS;
    private Integer HDMI;
    private Integer DP;
    private Integer DVI;
    private String sync;
}

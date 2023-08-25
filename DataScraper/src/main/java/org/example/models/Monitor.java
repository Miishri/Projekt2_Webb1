package org.example.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Monitor extends Component{

    private String resolution;
    private Integer refreshRate;
    private Integer size;
    private String panelType;
    private Integer responseMS;
    private Integer HDMI;
    private Integer DP;
    private Integer DVI;
    private Integer VGA;
    private Boolean speakers;
    private Boolean Curved;
    private String adjustableHeight;
    private String sync;
}

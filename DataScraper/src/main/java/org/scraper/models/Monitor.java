package org.scraper.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Monitor extends Component{

    public static final String endpoint = "/displays";

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

    public Monitor(String title, String image, String description, String producer, String rating, String resolution,
                   Integer refreshRate, Integer size, String panelType, Integer responseMS, Integer HDMI,
                   Integer DP, Integer DVI, Integer VGA, Boolean speakers, Boolean curved, String adjustableHeight,
                   String sync) {
        super(title, image, description, producer, rating);
        this.resolution = resolution;
        this.refreshRate = refreshRate;
        this.size = size;
        this.panelType = panelType;
        this.responseMS = responseMS;
        this.HDMI = HDMI;
        this.DP = DP;
        this.DVI = DVI;
        this.VGA = VGA;
        this.speakers = speakers;
        Curved = curved;
        this.adjustableHeight = adjustableHeight;
        this.sync = sync;
    }
}

package org.scraper.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.scraper.models.Component.Component;

@SuperBuilder
@Getter
@Setter
public class GPU extends Component {

    public static final String endpoint = "/gpus";

    private Integer length;
    private Integer slots;
    private Integer eightPinConnectors;
    private Integer sixPinConnectors;
    private Integer HDMI;
    private Integer DP;
    private Integer DVI;
    private Integer VGA;
    private String MHZ;
    private Integer VRAM;
    private Integer TDP;

    public GPU(String title, String image, String description, String producer,
               String rating, Integer length, Integer slots, Integer eightPinConnectors,
               Integer sixPinConnectors, Integer HDMI, Integer DP, Integer DVI, Integer VGA,
               String MHZ, Integer VRAM, Integer TDP) {
        super(title, image, description, producer, rating);
        this.length = length;
        this.slots = slots;
        this.eightPinConnectors = eightPinConnectors;
        this.sixPinConnectors = sixPinConnectors;
        this.HDMI = HDMI;
        this.DP = DP;
        this.DVI = DVI;
        this.VGA = VGA;
        this.MHZ = MHZ;
        this.VRAM = VRAM;
        this.TDP = TDP;
    }
}

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
public class Motherboard extends Component {
    public static final String endpoint = "/motherboards";

    private String socket;
    private String chipset;
    private String memoryType;
    private String memoryCapacity;
    private String ramSlots;
    private String sata;
    private String m2Storage;
    private Boolean pcie;
    private String usbSlots;
    private String VGA;
    private String DVI;
    private String DP;
    private String HDMI;
}

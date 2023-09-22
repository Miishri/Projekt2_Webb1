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
    private Integer memoryCapacity;
    private Integer ramSlots;
    private Integer sata;
    private Integer m2Storage;
    private Boolean pcie;
    private Integer usbSlots;
    private Integer VGA;
    private Integer DVI;
    private Integer DP;
    private Integer HDMI;
}

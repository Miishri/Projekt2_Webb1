package org.example.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Motherboards extends Component {
    private String socket;
    private String chipset;
    private String memoryType;
    private Integer memoryCapacity;
    private Integer ramSlots;
    private Integer sata;
    private Integer m2Storage;
    private Integer pcie;
    private Integer usbSlots;
    private Integer VGA;
    private Integer DVI;
    private Integer DP;
    private Integer HDMI;

    public Motherboards(String title, String image, String description,
                        String producer, String socket, String chipset, String memoryType,
                        Integer memoryCapacity, Integer ramSlots, Integer sata, Integer m2Storage,
                        Integer pcie, Integer usbSlots, Integer VGA, Integer DVI, Integer DP, Integer HDMI) {
        super(title, image, description, producer);
        this.socket = socket;
        this.chipset = chipset;
        this.memoryType = memoryType;
        this.memoryCapacity = memoryCapacity;
        this.ramSlots = ramSlots;
        this.sata = sata;
        this.m2Storage = m2Storage;
        this.pcie = pcie;
        this.usbSlots = usbSlots;
        this.VGA = VGA;
        this.DVI = DVI;
        this.DP = DP;
        this.HDMI = HDMI;
    }
}

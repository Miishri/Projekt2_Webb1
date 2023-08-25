package org.example.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Cases extends Component{
    public static final String endpoint = "/cases";

    private Integer width;
    private Integer depth;
    private Integer height;
    private String motherboard;
    private String powerSupply;
    private Integer gpuLength;
    private Integer supportedCpuCooler;
    private Boolean fanSupport;
    private Boolean radiatorSupport;
    private Boolean window;
    private Boolean dustFilter;
    private Boolean cableManagement;
    private Boolean noiseIsolation;

    public Cases(String title, String image, String description, String producer,
                 Integer width, Integer depth, Integer height, String motherboard,
                 String powerSupply, Integer gpuLength, Integer supportedCpuCooler,
                 Boolean fanSupport, Boolean radiatorSupport, Boolean window, Boolean dustFilter,
                 Boolean cableManagement, Boolean noiseIsolation) {
        super(title, image, description, producer);
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.motherboard = motherboard;
        this.powerSupply = powerSupply;
        this.gpuLength = gpuLength;
        this.supportedCpuCooler = supportedCpuCooler;
        this.fanSupport = fanSupport;
        this.radiatorSupport = radiatorSupport;
        this.window = window;
        this.dustFilter = dustFilter;
        this.cableManagement = cableManagement;
        this.noiseIsolation = noiseIsolation;
    }
}

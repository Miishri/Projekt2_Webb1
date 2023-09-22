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
public class Cases extends Component {
    public static final String endpoint = "/cases";

    private String width;
    private String depth;
    private String height;
    private String motherboard;
    private String powerSupply;
    private String supportedGpuLength;
    private String supportedCpuCooler;
    private Boolean fanSupport;
    private Boolean radiatorSupport;
    private Boolean window;
    private Boolean dustFilter;
    private Boolean cableManagement;
    private Boolean noiseIsolation;
}

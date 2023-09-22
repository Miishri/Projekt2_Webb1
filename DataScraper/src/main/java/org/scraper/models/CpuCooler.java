package org.scraper.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.scraper.models.Component.Component;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class CpuCooler extends Component {
    public static final String endpoint = "/cpucoolers";

    private List<String> socketSupports;
    private Boolean fanSupport;
    private Boolean extraFanSupport;
    private String TDP;
    private String height;
}

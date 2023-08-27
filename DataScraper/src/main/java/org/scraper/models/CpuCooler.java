package org.scraper.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class CpuCooler extends Component{
    public static final String endpoint = "/cpucoolers";

    private List<String> socketSupports;
    private String fanSupport;
    private Boolean extraFanSupport;
    private Integer TDP;
    private Integer height;

    public CpuCooler(String title, String image, String description, String producer, String rating,
                     List<String> socketSupports, String fanSupport, Boolean extraFanSupport, Integer TDP,
                     Integer height) {
        super(title, image, description, producer, rating);
        this.socketSupports = socketSupports;
        this.fanSupport = fanSupport;
        this.extraFanSupport = extraFanSupport;
        this.TDP = TDP;
        this.height = height;
    }
}

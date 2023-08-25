package org.example.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;

@Getter
@Setter
@SuperBuilder
public class CpuCooler extends Component{
    private ArrayList<String> socketSupports;
    private String fanSupport;
    private Boolean extraFanSupport;
    private Integer TDP;
    private Integer height;

    public CpuCooler(String title, String image, String description, String producer,
                     ArrayList<String> socketSupports, String fanSupport, Boolean extraFanSupport,
                     Integer TDP, Integer height) {
        super(title, image, description, producer);
        this.socketSupports = socketSupports;
        this.fanSupport = fanSupport;
        this.extraFanSupport = extraFanSupport;
        this.TDP = TDP;
        this.height = height;
    }
}

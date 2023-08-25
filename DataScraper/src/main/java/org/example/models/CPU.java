package org.example.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CPU extends Component{
    public static final String endpoint = "/cpus";

    private Integer cores;
    private Integer threads;
    private Integer baseClock;
    private Integer turboClick;
    private String socket;
    private Integer TDP;

}

package org.scraper.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
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

    public CPU(String title, String image, String description, String producer, String rating,
               Integer cores, Integer threads, Integer baseClock, Integer turboClick, String socket,
               Integer TDP) {
        super(title, image, description, producer, rating);
        this.cores = cores;
        this.threads = threads;
        this.baseClock = baseClock;
        this.turboClick = turboClick;
        this.socket = socket;
        this.TDP = TDP;
    }

    @Override
    public String toString() {
        return "CPU{" +
                "cores=" + cores +
                ", threads=" + threads +
                ", baseClock=" + baseClock +
                ", turboClick=" + turboClick +
                ", socket='" + socket + '\'' +
                ", TDP=" + TDP +
                '}';
    }
}

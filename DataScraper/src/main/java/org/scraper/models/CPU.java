package org.scraper.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.scraper.models.Component.Component;

@SuperBuilder
@Getter
@Setter
public class CPU extends Component {
    public static final String endpoint = "/cpus";

    private Integer cores;
    private Integer threads;
    private String baseClock;
    private String turboClick;
    private String socket;
    private String TDP;

    public CPU(String title, String image, String description, String producer, String rating,
               Integer cores, Integer threads, String baseClock, String turboClick, String socket,
               String TDP) {
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
                "title='" + getTitle() + '\'' +
                ", image='" + getImage() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", producer='" + getProducer() + '\'' +
                ", rating='" + getRating() + '\'' +
                ", cores=" + cores +
                ", threads=" + threads +
                ", baseClock='" + baseClock + '\'' +
                ", turboClick='" + turboClick + '\'' +
                ", socket='" + socket + '\'' +
                ", TDP='" + TDP + '\'' +
                '}';
    }
}

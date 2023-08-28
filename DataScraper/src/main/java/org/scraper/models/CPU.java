package org.scraper.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.scraper.models.Component.Component;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CPU extends Component {
    public static final String endpoint = "/cpus";
    public static final String jsonDatabase = "src/main/resources/DatabaseJSON/cpus_database.json";

    @JsonProperty("Cores")
    private Integer cores;
    @JsonProperty("threads")
    private Integer threads;
    @JsonProperty("baseClock")
    private String baseClock;
    @JsonProperty("turboClick")
    private String turboClick;
    @JsonProperty("socket")
    private String socket;
    @JsonProperty("tdp")
    private String TDP;
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

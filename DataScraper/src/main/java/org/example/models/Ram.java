package org.example.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Ram extends Component{

    public static final String endpoint = "/rams";

    private String ramType;
    private Integer size;
    private Integer clock;
    private String timings;
    private Integer sticks;

    public Ram(String title, String image, String description,
               String producer, String rating, String ramType, Integer size,
               Integer clock, String timings, Integer sticks) {
        super(title, image, description, producer, rating);
        this.ramType = ramType;
        this.size = size;
        this.clock = clock;
        this.timings = timings;
        this.sticks = sticks;
    }
}

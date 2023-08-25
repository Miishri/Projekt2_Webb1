package org.example.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Ram extends Component{

    private String ramType;
    private Integer size;
    private Integer clock;
    private String timings;
    private Integer sticks;

    public Ram(String title, String image, String description,
               String producer, String ramType, Integer size, Integer clock,
               String timings, Integer sticks) {
        super(title, image, description, producer);
        this.ramType = ramType;
        this.size = size;
        this.clock = clock;
        this.timings = timings;
        this.sticks = sticks;
    }
}

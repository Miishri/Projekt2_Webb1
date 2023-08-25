package org.example.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@SuperBuilder
@Getter
public class PSU extends Component{

    private Integer watt;
    private String size;
    private String efficiencyRating;
    private Integer pcie8;
    private Integer pcie6;

    public PSU(String title, String image, String description,
               String producer, Integer watt, String size,
               String efficiencyRating, Integer pcie8, Integer pcie6) {
        super(title, image, description, producer);
        this.watt = watt;
        this.size = size;
        this.efficiencyRating = efficiencyRating;
        this.pcie8 = pcie8;
        this.pcie6 = pcie6;
    }
}
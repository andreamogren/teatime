package org.example.teatime.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Tea {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String type;
    private int temperature;
    private int steepingTimeMin;
    private int steepingTimeMax;
    private String desc;
    private String imageUrl;
}

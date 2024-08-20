package org.example.teatime.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
@Entity
public class Tea {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 50, message = "Type should not exceed 50 characters")
    private String type;

    @Min(value = 0, message = "Temperature should be more than 0")
    @Max(value = 100, message = "Temperature should not exceed 100")
    private int temperature;

    @Min(value = 1, message = "Steeping time should be at least 1 minute")
    private int steepingTimeMin;

    @Min(value = 1, message = "Steeping time should be at least 1 minute")
    private int steepingTimeMax;

    @Size(max = 500, message = "Description should not exceed 500 characters")
    private String desc;

    @NotBlank(message = "Image URL is mandatory")
    private String imageUrl;
}

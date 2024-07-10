package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    @JsonProperty("coordinatex")
    private Double CoordinateX;
    @JsonProperty("coordinatey")
    private Double CoordinateY;
    private String photo;
    @JsonProperty("is_del")
    private boolean isDel;
}

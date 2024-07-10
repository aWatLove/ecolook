package org.example.dto.payment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceCreateRequest {
    private String title;
    private String description;
    @JsonProperty("XCoordinate")
    private Double XCoordinate;
    @JsonProperty("YCoordinate")
    private Double YCoordinate;
    private List<Long> tags;
    private List<Long> tariffs;
    private String photo;
}

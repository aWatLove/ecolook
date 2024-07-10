package org.example.dto.payment.response.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.dynalink.linker.LinkerServices;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlacePreviewDTO {
    private Long id;
    private String title;
    @JsonProperty("XCoordinate")
    private Double XCoordinate;
    @JsonProperty("YCoordinate")
    private Double YCoordinate;


    public PlacePreviewDTO(Long id, String title, Double XCoordinate, Double YCoordinate) {
        this.id = id;
        this.title = title;
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
    }
}

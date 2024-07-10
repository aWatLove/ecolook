package org.example.dto.payment.response.place;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.dto.payment.response.tariff.TariffDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PlaceDetailsResponse {
    private Long id;
    private String title;
    private String description;
    @JsonProperty("XCoodinate")
    private Double XCoordinate;
    @JsonProperty("YCoodinate")
    private Double YCoordinate;
    private List<String> tags;
    private List<TariffDTO> tariffs;
    private String photo;
}

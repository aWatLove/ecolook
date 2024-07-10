package org.example.dto.payment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TariffSaveRequest {
    private String title;
    private String description;
    @JsonProperty("price_per_day")
    private Double pricePerDay;
    private String photo;
}

package org.example.dto.payment.response.tariff;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TariffDTO {
    private Long id;
    private String title;
    private String description;
    @JsonProperty("price_per_day")
    private Double pricePerDay;
    private String photo;

    // Constructor for the query
    public TariffDTO(Long id, String title, String description, Double pricePerDay, String photo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.photo = photo;
    }
}

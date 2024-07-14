package org.example.dto.payment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderCreateRequest {
    @JsonProperty("place_id")
    private Long placeId;
    @JsonProperty("days_count")
    private Integer daysCount;
    @JsonProperty("tariff_id")
    private Long tariffId;
    @JsonProperty("optional_ids")
    private List<Long> optionalIds;
    private LocalDateTime date;
}




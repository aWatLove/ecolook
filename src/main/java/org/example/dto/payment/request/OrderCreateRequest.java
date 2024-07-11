package org.example.dto.payment.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreateRequest {
    private Long placeId;
    private Long daysCount;
    private Long tariffId;
    private List<Long> optionalIds;
}




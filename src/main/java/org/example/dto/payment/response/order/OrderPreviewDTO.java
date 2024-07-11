package org.example.dto.payment.response.order;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.payment.response.place.PlacePreviewDTOOrder;

@Getter
@Setter
public class OrderPreviewDTO {
    private Long id;
    private PlacePreviewDTOOrder place;
    private String status;
    private String paymentStatus;
    private Long daysCount;
    private Double totalPrice;
}



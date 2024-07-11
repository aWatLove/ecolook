package org.example.dto.payment.response.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderPreviewsResponse {
    List<OrderPreviewDTO> orders;
}

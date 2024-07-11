package org.example.dto.payment.response.option;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionDetailDTO {
    private Long id;
    private String title;
    private Double price;
    private Long count;
}

package org.example.dto.payment.response.order;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.payment.response.option.OptionDetailDTO;
import org.example.dto.payment.response.place.PlacePreviewDTO;
import org.example.dto.payment.response.tariff.TariffDTO;
import org.example.dto.payment.response.user.UserInfoDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private UserInfoDTO user;
    private PlacePreviewDTO place;
    private TariffDTO tariff;
    private Integer daysCount;
    private String status;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private List<OptionDetailDTO> additionalOptions;
    private Double totalPrice;
    private String paymentStatus;
}

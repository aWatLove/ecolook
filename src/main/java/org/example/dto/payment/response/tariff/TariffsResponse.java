package org.example.dto.payment.response.tariff;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TariffsResponse {
    List<TariffDTO> tariffs;
}

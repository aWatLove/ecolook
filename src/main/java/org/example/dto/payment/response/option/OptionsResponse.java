package org.example.dto.payment.response.option;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OptionsResponse {
    List<OptionDTO> options;
}

package org.example.dto.payment.response.place;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlacePreviewsResponse {
    private List<PlacePreviewDTO> places;

    public PlacePreviewsResponse(List<PlacePreviewDTO> places) {
        this.places = places;
    }
}

package org.example.dto.payment.response.tag;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class TagsResponse {
    private List<TagDTO> tags;
}

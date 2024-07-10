package org.example.service;

import org.example.dto.payment.response.option.OptionDTO;
import org.example.dto.payment.response.option.OptionsResponse;
import org.example.model.Option;
import org.example.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OptionService {
    @Autowired
    OptionRepository optionRepository;

    public OptionsResponse getAllOptions() {
        List<Option> options = optionRepository.findAll();
        List<OptionDTO> dtos = new ArrayList<>();
        for(Option opt : options) {
            if(!opt.isDel()) {
                OptionDTO d = new OptionDTO();
                d.setId(opt.getId());
                d.setTitle(opt.getTitle());
                d.setPrice((double) (opt.getPrice()/100));
                dtos.add(d);
            }
        }
        OptionsResponse resp = new OptionsResponse();
        resp.setOptions(dtos);
        return resp;
    }

    public OptionDTO createOption(String title, Double price) {
        if (title.isEmpty() || price == null) {
            throw new IllegalArgumentException("queries cant be null");
        }
        Option option = new Option();
        option.setTitle(title);
        option.setPrice((long) (price * 100));
        option = optionRepository.saveAndFlush(option);
        OptionDTO resp = new OptionDTO();
        resp.setId(option.getId());
        resp.setTitle(title);
        resp.setPrice(price);
        return resp;
    }

    public void deleteOption(Long id) {
        Option option = optionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        option.setDel(true);
        optionRepository.saveAndFlush(option);
    }
}

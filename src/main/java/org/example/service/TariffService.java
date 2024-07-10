package org.example.service;

import org.example.dto.payment.request.TariffSaveRequest;
import org.example.dto.payment.response.option.OptionDTO;
import org.example.dto.payment.response.option.OptionsResponse;
import org.example.dto.payment.response.tariff.TariffDTO;
import org.example.dto.payment.response.tariff.TariffsResponse;
import org.example.model.Option;
import org.example.model.Tariff;
import org.example.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TariffService {
    @Autowired
    TariffRepository tariffRepository;

    public TariffsResponse getAllTariffs() {
        List<Tariff> tariffs = tariffRepository.findAll();
        List<TariffDTO> dtos = new ArrayList<>();
        for (Tariff tariff : tariffs) {
            if (!tariff.isDel()) {
                TariffDTO d = new TariffDTO(tariff.getId(), tariff.getTitle(), tariff.getDescription(), (double) (tariff.getPricePerDay() / 100), tariff.getPhoto());
                dtos.add(d);
            }
        }
        TariffsResponse resp = new TariffsResponse();
        resp.setTariffs(dtos);
        return resp;
    }

    public TariffDTO createTariff(TariffSaveRequest body) {
        if (body.getTitle() == null || body.getPricePerDay() == null) {
            throw new IllegalArgumentException("title and price cant be null");
        }

        Tariff tariff = new Tariff();
        tariff.setTitle(body.getTitle());
        tariff.setDescription(body.getDescription());
        tariff.setPricePerDay((int) (body.getPricePerDay() * 100));
        tariff.setPhoto(body.getPhoto());
        tariff = tariffRepository.saveAndFlush(tariff);
        return new TariffDTO(tariff.getId(), tariff.getTitle(), tariff.getDescription(), (double) (tariff.getPricePerDay() / 100), tariff.getPhoto());
    }

    public TariffDTO updateTariff(Long id, TariffSaveRequest body) {
        Tariff tariff = tariffRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (body.getTitle() != null) {
            tariff.setTitle(body.getTitle());
        }
        if (body.getDescription() != null) {
            tariff.setDescription(body.getDescription());
        }
        if (body.getPricePerDay() != null) {
            tariff.setPricePerDay((int) (body.getPricePerDay() * 100));
        }
        if (body.getPhoto() != null) {
            tariff.setPhoto(body.getPhoto());
        }
        tariff = tariffRepository.saveAndFlush(tariff);
        return new TariffDTO(tariff.getId(), tariff.getTitle(), tariff.getDescription(), (double) (tariff.getPricePerDay() / 100), tariff.getPhoto());
    }


    public void deleteTariff(Long id) {
        Tariff tariff = tariffRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        tariff.setDel(true);
        tariffRepository.saveAndFlush(tariff);
    }
}

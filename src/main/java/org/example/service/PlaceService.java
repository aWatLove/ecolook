package org.example.service;

import org.example.dto.payment.request.PlaceCreateRequest;
import org.example.dto.payment.response.place.PlaceDetailsResponse;
import org.example.dto.payment.response.place.PlacePreviewDTO;
import org.example.dto.payment.response.place.PlacePreviewsResponse;
import org.example.dto.payment.response.tariff.TariffDTO;
import org.example.model.Place;
import org.example.model.Tag;
import org.example.model.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    private static final double defaultRadius = 50;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    TagRepository tagRepository;
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    PlaceTagRepository placeTagRepository;
    @Autowired
    PlaceTariffRepository placeTariffRepository;

    public PlacePreviewsResponse getPlacesAround(Double XCoordinate, Double YCoordinate, Double radius) {
        if (XCoordinate == null || YCoordinate == null) {
            throw new IllegalArgumentException("coordinates cant be null");
        }


        if (radius == 0) {
            radius = defaultRadius;
        }
        List<Place> places = placeRepository.findPlacesWithinRadius(XCoordinate, YCoordinate, radius);

        List<PlacePreviewDTO> prevs = new ArrayList<>();

        for (Place p : places) {
            PlacePreviewDTO dto = new PlacePreviewDTO(
                    p.getId(), p.getTitle(), p.getCoordinateX(), p.getCoordinateY()
            );
            prevs.add(dto);
        }


        return new PlacePreviewsResponse(prevs);
    }

    public PlaceDetailsResponse getPlaceDetailsById(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Место с id " + id + " не найдено"));

        List<Tag> tags = placeTagRepository.findByPlaceId(id)
                .stream()
                .map(PlaceTag::getTag)
                .collect(Collectors.toList());

        List<Tariff> tariffs = placeTariffRepository.findByPlaceId(id)
                .stream()
                .map(PlaceTariff::getTariff)
                .collect(Collectors.toList());

        PlaceDetailsResponse response = new PlaceDetailsResponse();
        response.setId(place.getId());
        response.setTitle(place.getTitle());
        response.setDescription(place.getDescription());
        response.setXCoordinate(place.getCoordinateX());
        response.setYCoordinate(place.getCoordinateY());
        response.setPhoto(place.getPhoto());

        List<String> tagNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        response.setTags(tagNames);

        List<TariffDTO> tariffDTOs = tariffs.stream()
                .map(tariff -> new TariffDTO(
                        tariff.getId(),
                        tariff.getTitle(),
                        tariff.getDescription(),
                        (double) tariff.getPricePerDay() / 100,
                        tariff.getPhoto()
                ))
                .collect(Collectors.toList());
        response.setTariffs(tariffDTOs);

        return response;
    }

    public PlaceDetailsResponse createPlace(PlaceCreateRequest body) {
        Place place = new Place();

        if (body.getTitle() == null || body.getXCoordinate() == null || body.getYCoordinate() == null) {
            throw new IllegalArgumentException();
        }

        place.setTitle(body.getTitle());
        place.setDescription(body.getDescription());
        place.setCoordinateX(body.getXCoordinate());
        place.setCoordinateY(body.getYCoordinate());
        place.setPhoto(body.getPhoto());

        place = placeRepository.saveAndFlush(place);

        for (Long tag_id: body.getTags()) {
            Optional<Tag> tag = tagRepository.findById(tag_id);
            if (tag.isPresent()) {
                if (!tag.get().isDel()){
                    PlaceTag pt = new PlaceTag();
                    pt.setPlace(place);
                    pt.setTag(tag.get());
                    placeTagRepository.saveAndFlush(pt);
                }
            }
        }


        for (Long tariff_id: body.getTariffs()) {
            Optional<Tariff> tariff = tariffRepository.findById(tariff_id);
            if (tariff.isPresent()) {
                if (!tariff.get().isDel()) {
                    PlaceTariff pt = new PlaceTariff();
                    pt.setPlace(place);
                    pt.setTariff(tariff.get());
                    placeTariffRepository.saveAndFlush(pt);
                }
            }
        }

        return getPlaceDetailsById(place.getId());
    }

    public PlaceDetailsResponse updatePlace(Long id, PlaceCreateRequest body) {
        Place place = placeRepository.findById(id).orElseThrow(EntityNotFoundException::new);


        if (body.getTitle() != null) {
            place.setTitle(body.getTitle());
        }
        if (body.getDescription() != null) {
            place.setDescription(body.getDescription());
        }
        if (body.getXCoordinate() != null) {
            place.setCoordinateX(body.getXCoordinate());
        }
        if (body.getYCoordinate() != null) {
            place.setCoordinateY(body.getYCoordinate());
        }
        if (body.getPhoto() != null) {
            place.setPhoto(body.getPhoto());
        }

        place = placeRepository.saveAndFlush(place);


        placeTagRepository.deleteAll(placeTagRepository.findByPlaceId(id));
        placeTariffRepository.deleteAll(placeTariffRepository.findByPlaceId(id));

        for (Long tag_id: body.getTags()) {
            Optional<Tag> tag = tagRepository.findById(tag_id);
            if (tag.isPresent()) {
                if (!tag.get().isDel()){
                    PlaceTag pt = new PlaceTag();
                    pt.setPlace(place);
                    pt.setTag(tag.get());
                    placeTagRepository.saveAndFlush(pt);
                }
            }
        }


        for (Long tariff_id: body.getTariffs()) {
            Optional<Tariff> tariff = tariffRepository.findById(tariff_id);
            if (tariff.isPresent()) {
                if (!tariff.get().isDel()) {
                    PlaceTariff pt = new PlaceTariff();
                    pt.setPlace(place);
                    pt.setTariff(tariff.get());
                    placeTariffRepository.saveAndFlush(pt);
                }
            }
        }

        return getPlaceDetailsById(id);
    }

    public void deletePlace(Long id) {
        Place place = placeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        place.setDel(true);
        placeRepository.saveAndFlush(place);
    }
}

package org.example.repository;

import org.example.model.PlaceTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceTariffRepository extends JpaRepository<PlaceTariff, Long> {
    List<PlaceTariff> findByPlaceId(Long placeId);
}

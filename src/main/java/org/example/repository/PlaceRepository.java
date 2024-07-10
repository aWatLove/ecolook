package org.example.repository;

import org.example.dto.payment.response.place.PlaceDetailsResponse;
import org.example.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("SELECT p " +
            "FROM Place p " +
            "WHERE 6371 * acos(cos(radians(:latitude)) * cos(radians(p.CoordinateX)) * cos(radians(p.CoordinateY) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(p.CoordinateX))) <= :radius")
    List<Place> findPlacesWithinRadius(@Param("latitude") double latitude,
                                       @Param("longitude") double longitude,
                                       @Param("radius") double radius);



}
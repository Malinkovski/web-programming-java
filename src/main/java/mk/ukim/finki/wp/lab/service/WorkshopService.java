package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Workshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface WorkshopService {

    List<Workshop> listAll();

    List<Workshop> search(String text, Double maxPrice);

    Optional<Workshop> findById(Long id);

    Workshop create(String title, String category, Double price,
                    Integer capacity, Long instructorId);

    Workshop update(Long id, String title, String category, Double price,
                    Integer capacity, Long instructorId);

    void deleteById(Long id);

    default void addRating(Long workshopId, Integer rating) {
        Workshop workshop = findById(workshopId).orElseThrow(() -> new IllegalArgumentException("Workshop not found"));
        if (workshop.getRatings() == null) {
            workshop.setRatings(new ArrayList<>());
        }
        workshop.getRatings().add(rating);
    }
}
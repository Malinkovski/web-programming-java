package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Instructor;
import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.repository.InstructorRepository;
import mk.ukim.finki.wp.lab.repository.WorkshopRepository;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;
    private final InstructorRepository instructorRepository;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository,
                               InstructorRepository instructorRepository) {
        this.workshopRepository = workshopRepository;
        this.instructorRepository = instructorRepository;
    }

    @Override
    public List<Workshop> listAll() {
        return workshopRepository.findAll();
    }

    @Override
    public List<Workshop> search(String text, Double maxPrice) {
        return workshopRepository.search(text, maxPrice);
    }

    @Override
    public Optional<Workshop> findById(Long id) {
        return workshopRepository.findById(id);
    }

    @Override
    public Workshop create(String title, String category, Double price,
                           Integer capacity, Long instructorId) {

        validateWorkshop(title, category, price, capacity);

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        Long newId = workshopRepository.findAll().stream()
                .mapToLong(Workshop::getId)
                .max()
                .orElse(0) + 1;

        Workshop workshop = new Workshop(
                newId, title, category, price, capacity, instructor, new ArrayList<>()
        );

        return workshopRepository.save(workshop);
    }

    @Override
    public Workshop update(Long id, String title, String category, Double price,
                           Integer capacity, Long instructorId) {

        Workshop existingWorkshop = workshopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workshop not found"));

        validateWorkshop(title, category, price, capacity);

        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor not found"));

        Workshop updatedWorkshop = new Workshop(
                id, title, category, price, capacity, instructor, new ArrayList<>()
        );

        return workshopRepository.save(updatedWorkshop);
    }

    @Override
    public void deleteById(Long id) {
        workshopRepository.deleteById(id);
    }

    private void validateWorkshop(String title, String category,
                                  Double price, Integer capacity) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category is required");
        }

        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be zero or greater");
        }

        if (capacity == null || capacity < 0) {
            throw new IllegalArgumentException("Capacity must be zero or greater");
        }
    }
}
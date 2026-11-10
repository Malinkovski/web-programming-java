package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Workshop;
import mk.ukim.finki.wp.lab.service.InstructorService;
import mk.ukim.finki.wp.lab.service.WorkshopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class WorkshopController {

    private final WorkshopService workshopService;
    private final InstructorService instructorService;

    public WorkshopController(WorkshopService workshopService,
                              InstructorService instructorService) {
        this.workshopService = workshopService;
        this.instructorService = instructorService;
    }

    @GetMapping("/")
    public String redirectToWorkshops() {
        return "redirect:/workshops";
    }

    @GetMapping("/workshops")
    public String getWorkshopsPage(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String errorMessage,
            Model model) {

        if ((text != null && !text.isBlank()) || maxPrice != null) {
            model.addAttribute("workshops",
                    workshopService.search(text, maxPrice));
        } else {
            model.addAttribute("workshops", workshopService.listAll());
        }

        model.addAttribute("errorMessage", errorMessage);

        return "listWorkshops";
    }

    @GetMapping("/workshops/workshop-form")
    public String getAddWorkshopPage(Model model) {
        model.addAttribute("instructors", instructorService.findAll());
        model.addAttribute("workshop", null);

        return "workshop-form";
    }

    @GetMapping("/workshops/workshop-form/{id}")
    public String getEditWorkshopPage(@PathVariable Long id, Model model) {

        Workshop workshop = workshopService.findById(id).orElse(null);

        if (workshop == null) {
            return "redirect:/workshops?errorMessage=WorkshopNotFound";
        }

        model.addAttribute("workshop", workshop);
        model.addAttribute("instructors", instructorService.findAll());

        return "workshop-form";
    }

    @PostMapping("/workshops/add")
    public String addWorkshop(
            @RequestParam String title,
            @RequestParam String category,
            @RequestParam Double price,
            @RequestParam Integer capacity,
            @RequestParam Long instructorId) {

        try {
            workshopService.create(title, category, price, capacity, instructorId);
            return "redirect:/workshops";
        } catch (IllegalArgumentException exception) {
            String message = URLEncoder.encode(
                    exception.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/workshops?errorMessage=" + message;
        }
    }

    @PostMapping("/workshops/edit/{workshopId}")
    public String editWorkshop(
            @PathVariable Long workshopId,
            @RequestParam String title,
            @RequestParam String category,
            @RequestParam Double price,
            @RequestParam Integer capacity,
            @RequestParam Long instructorId) {

        try {
            workshopService.update(
                    workshopId, title, category, price, capacity, instructorId);
            return "redirect:/workshops";
        } catch (IllegalArgumentException exception) {
            String message = URLEncoder.encode(
                    exception.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/workshops?errorMessage=" + message;
        }
    }

    @PostMapping("/workshops/delete/{id}")
    public String deleteWorkshop(@PathVariable Long id) {
        workshopService.deleteById(id);
        return "redirect:/workshops";
    }
    @PostMapping("/workshops/rate/{id}")
    public String rateWorkshop(
            @PathVariable Long id,
            @RequestParam Integer rating) {
        try {
            workshopService.addRating(id, rating);
            return "redirect:/workshops";
        } catch (IllegalArgumentException exception) {
            String message = URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8);
            return "redirect:/workshops?errorMessage=" + message;
        }
    }
}
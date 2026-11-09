
package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public String register(
            @RequestParam String workshopTitle,
            @RequestParam String participantName,
            @RequestParam String participantEmail,
            @RequestParam Integer numSeats,
            HttpServletRequest request,
            @RequestHeader("User-Agent") String userAgent,
            Model model) {

        String clientIpAddress = request.getRemoteAddr();

        try {
            Registration registration = registrationService.register(
                    workshopTitle,
                    participantName,
                    participantEmail,
                    numSeats
            );

            model.addAttribute("registration", registration);
            model.addAttribute("clientIp", clientIpAddress);
            model.addAttribute("userAgent", userAgent);

            return "registrationConfirmation";

        } catch (IllegalArgumentException exception) {
            String message = URLEncoder.encode(
                    exception.getMessage(), StandardCharsets.UTF_8);

            return "redirect:/workshops?errorMessage=" + message;
        }
    }
}

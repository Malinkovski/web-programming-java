package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Registration;
import java.util.List;

public interface RegistrationService {

    Registration register(
            String workshopTitle,
            String participantName,
            String participantEmail,
            int numberOfSeats
    );

    List<Registration> listAll();

    void deleteById(Long id);
}
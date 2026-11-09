package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Instructor {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String expertise;
}
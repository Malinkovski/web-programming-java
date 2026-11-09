package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Workshop {

    private Long id;
    private String title;
    private String category;
    private double price;
    private int capacity;
    private Instructor instructor;
}
package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Workshop {

    private Long id;
    private String title;
    private String category;
    private double price;
    private int capacity;
    private Instructor instructor;
    private List<Integer> ratings;

    public double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) return 0;
        double sum = 0;
        for (int r : ratings) {
            sum += r;
        }
        return sum / ratings.size();
    }
}
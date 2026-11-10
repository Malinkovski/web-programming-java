
package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Instructor;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Workshop> workshops = null;
    public static List<Registration> registrations = null;
    public static List<Instructor> instructors = null;

    @PostConstruct
    public void init() {

        instructors = new ArrayList<>(List.of(
                new Instructor(1L, "John", "Smith", "john@example.com", "Programming"),
                new Instructor(2L, "Jane", "Doe", "jane@example.com", "Design"),
                new Instructor(3L, "Michael", "Brown", "michael@example.com", "Technology")
        ));

        workshops = new ArrayList<>(List.of(
                new Workshop(1L, "Java Basics", "Programming", 1500, 20, instructors.get(0), new ArrayList<>()),
                new Workshop(2L, "Spring Boot", "Programming", 2000, 15, instructors.get(1), new ArrayList<>()),
                new Workshop(3L, "Web Development", "Development", 1800, 10, instructors.get(2), new ArrayList<>()),
                new Workshop(4L, "React Workshop", "Frontend", 1700, 12, instructors.get(0),new ArrayList<>()),
                new Workshop(5L, "Database Design", "Database", 1200, 0, instructors.get(1),new ArrayList<>()),
                new Workshop(6L, "Python Basics", "Programming", 1400, 18, instructors.get(2),new ArrayList<>()),
                new Workshop(7L, "UI/UX Design", "Design", 1600, 14, instructors.get(0),new ArrayList<>()),
                new Workshop(8L, "HTML and CSS", "Frontend", 1000, 25, instructors.get(1),new ArrayList<>()),
                new Workshop(9L, "JavaScript", "Programming", 1900, 16, instructors.get(2),new ArrayList<>()),
                new Workshop(10L, "Cloud Computing", "Technology", 2200, 8, instructors.get(0),new ArrayList<>())
        ));

        registrations = new ArrayList<>();
    }
}

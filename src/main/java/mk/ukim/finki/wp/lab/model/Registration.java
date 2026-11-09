
package mk.ukim.finki.wp.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Registration {
    private Long id;
    private String workshopTitle;
    private String participantName;
    private String participantEmail;
    private int numberOfSeats;
}

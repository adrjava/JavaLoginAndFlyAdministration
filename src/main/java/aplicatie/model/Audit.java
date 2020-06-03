package aplicatie.model;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Audit {
    int user_id;
    String register_date;
    String frame_page;
}

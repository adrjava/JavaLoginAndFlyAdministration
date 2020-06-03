package aplicatie.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    int id;
    String userName;
    String userPassword;
    String userEmail;
}

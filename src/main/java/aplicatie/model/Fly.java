package aplicatie.model;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fly {
    int id;
    String sursa;
    String destinatie;
    String oraPlecare;
    String oraSosire;
    String zile;
    int pret;
}

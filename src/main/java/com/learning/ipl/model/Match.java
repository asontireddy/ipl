package com.learning.ipl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Match {
    @Id
    private long id;
    private String city;
    private LocalDate date;
    private String playerOfMatch;
    private String venue;
    private String team1;
    private String team2;
    private String tossWinner;
    private String tossDecision;
    private String matchWinner;
    private String result;
    private String resultMargin;
    private String umpire1;
    private String umpire2;
}

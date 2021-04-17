package com.learning.ipl.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Team {
    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", totalMatches=" + totalMatches +
                ", totalWins=" + totalWins +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String teamName;
    private long totalMatches;
    private long totalWins;

    @Transient
    private List<Match> matches;

    public Team() {

    }

    public Team(String teamName, long totalMatches) {
        this.teamName = teamName;
        this.totalMatches = totalMatches;
    }
}

package com.learning.ipl.data;

import com.learning.ipl.model.Team;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Log4j2
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {


    private final EntityManager entityManager;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            Map<String, Team> teamData = new HashMap<>();

            entityManager.createQuery("SELECT m.team1, count(*) from Match m group by m.team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(e -> new Team((String)e[0], (long)e[1]))
                    .forEach(team -> teamData.put(team.getTeamName(), team));

            entityManager.createQuery("SELECT m.team2, count(*) from Match m group by m.team2", Object[].class)
                    .getResultList()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
                    });

            entityManager.createQuery("SELECT m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
                    .getResultList()
                    .forEach(e -> {
                        Team team = teamData.get((String) e[0]);
                        if(Objects.nonNull(team)) {
                            team.setTotalWins((long) e[1]);
                        }
                    });

            teamData.values().forEach(entityManager::persist);
            teamData.values().forEach(System.out::println);

        }
    }
}

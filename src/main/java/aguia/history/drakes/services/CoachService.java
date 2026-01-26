package aguia.history.drakes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.Coach;
import aguia.history.drakes.domain.Team;
import aguia.history.drakes.repositories.CoachRepository;
import aguia.history.drakes.repositories.TeamRepository;

@Service
public class CoachService {
    
    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private TeamRepository teamRepository;

    // novo tecnico
    public Coach create(Coach coach) {
        return coachRepository.save(coach);
    }   

    //listar todos os tecnicos
    public List<Coach> findAll() {
        return coachRepository.findAll();
    }   

    public Team hireCoach(Long teamId, Long coachId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Time não encontrado"));
        Coach coach = coachRepository.findById(coachId).orElseThrow(() -> new RuntimeException("Técnico não encontrado"));

        team.setCurrentCoach(coach);
        return teamRepository.save(team);
    }

    public Team fireCoach(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Time não encontrado"));

        team.setCurrentCoach(null);
        return teamRepository.save(team);
    }
}

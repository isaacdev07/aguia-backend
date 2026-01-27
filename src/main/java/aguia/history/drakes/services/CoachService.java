package aguia.history.drakes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import aguia.history.drakes.domain.Coach;
import aguia.history.drakes.domain.Team;
import aguia.history.drakes.domain.User;
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

    // listar todos os tecnicos
    public List<Coach> findAll() {
        return coachRepository.findAll();
    }

    public Team hireCoach(Long teamId, Long coachId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Time não encontrado"));
       
        // valida se o usuário logado é o dono do time
        validarDono(team);

        Coach coach = coachRepository.findById(coachId)
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado"));

        team.setCurrentCoach(coach);
        return teamRepository.save(team);
    }

    public Team fireCoach(Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Time não encontrado"));

        // valida se o usuário logado é o dono do time
        validarDono(team);

        team.setCurrentCoach(null);
        return teamRepository.save(team);
    }

    // metodo auxiliar para validar dono do time
    private void validarDono(Team team) {
        String emailLogado = getEmailLogado();
        
        // Verifica se o time tem dono e se o email bate
        if (team.getOwner() == null || !team.getOwner().getEmail().equals(emailLogado)) {
            throw new RuntimeException("ACESSO NEGADO: Você não é o dono deste time.");
        }
    }

    // metodo auxiliar para pegar email do usuário logado
    private String getEmailLogado() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}

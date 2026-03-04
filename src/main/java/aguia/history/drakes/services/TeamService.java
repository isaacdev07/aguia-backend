package aguia.history.drakes.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.Coach;
import aguia.history.drakes.domain.Season;
import aguia.history.drakes.domain.Team;
import aguia.history.drakes.domain.User;
import aguia.history.drakes.dtos.TeamCreationDTO;
import aguia.history.drakes.repositories.CoachRepository;
import aguia.history.drakes.repositories.SeasonRepository;
import aguia.history.drakes.repositories.TeamRepository;
import aguia.history.drakes.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Transactional
    public Team createTeam(TeamCreationDTO dto){
        
        String emailLogado = getEmailLogado();
        User dono = userRepository.findByEmail(emailLogado)
                                  .orElseThrow(() -> new RuntimeException("Dono não encontrado no banco"));
        
        // cria o time com os dados do DTO e o dono logado
        Team team = new Team();
        team.setName(dto.getName());
        team.setCity(dto.getCity());
        team.setFoundedYear(dto.getFoundedYear());
        team.setShieldUrl(dto.getShieldUrl());
        team.setOwner(dono);

        // salva o time pra gerar o id 
        Team savedTeam = teamRepository.save(team);

        // cria o tecnico se o nome do técnico veio preenchido no dto
        if (dto.getCoachName() != null && !dto.getCoachName().isBlank()) {
            Coach coach = new Coach();
            coach.setName(dto.getCoachName());
            coach.setTeam(savedTeam);

            Coach savedCoach = coachRepository.save(coach);
            
            // atualiza o time para setar o técnico atual 
            savedTeam.setCurrentCoach(savedCoach);
            savedTeam = teamRepository.save(savedTeam);
        }

        // cria a temporada atual do time
        Season currentSeason = new Season();
        if (dto.getSeasonYear() != null && !dto.getSeasonYear().isBlank()) {
            // se o usuario mandou algo no campo de temporada, tenta usar isso como ano da temporada
            try {
                // tenta salvar o ano vindo do dto
                currentSeason.setYear(Integer.parseInt(dto.getSeasonYear()));
            } catch (NumberFormatException e) {
                // se nao veio um numero valido, cai aqui e seta o ano atual como fallback
                currentSeason.setYear(LocalDate.now().getYear());
            }
            currentSeason.setDescription("Temporada " + dto.getSeasonYear());
        } else {
            // se nao tinha nada no campo de temporada, seta o ano atual como default
            currentSeason.setYear(LocalDate.now().getYear());
            currentSeason.setDescription("Temporada " + currentSeason.getYear());
        }
        
        currentSeason.setTeam(savedTeam);  
        seasonRepository.save(currentSeason);  

        return savedTeam;
    }

    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));
        validarDono(team);
        teamRepository.deleteById(teamId);
    }

    private String getEmailLogado() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    private void validarDono(Team team) {
        String emailLogado = getEmailLogado();
        if (team.getOwner() == null || !team.getOwner().getEmail().equals(emailLogado)) {
            throw new RuntimeException("ACESSO NEGADO: Você não é o dono deste time.");
        }
    }
}
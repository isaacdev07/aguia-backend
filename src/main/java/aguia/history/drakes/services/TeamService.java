package aguia.history.drakes.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.Season;
import aguia.history.drakes.domain.Team;
import aguia.history.drakes.domain.User;
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

    @Transactional
    public Team createTeam(Team team){
        
        //pegar usuário logado (email)
        String emailLogado = getEmailLogado();
        
        // buscar usuário dono do time
        User dono = userRepository.findByEmail(emailLogado)
                                  .orElseThrow(() -> new RuntimeException("Dono não encontrado no banco"));
        
        
        //seta o dono do time antes de salvar
        team.setOwner(dono);

        //salvar time
        Team savedTeam = teamRepository.save(team);
        //cria temporada inicial pegando o ano atual automaticamente
        Season currentSeason = new Season();
        currentSeason.setYear(LocalDate.now().getYear());
        currentSeason.setDescription("Temporada " + currentSeason.getYear());
        currentSeason.setTeam(savedTeam);  

        // salva a temporada atual
        seasonRepository.save(currentSeason);  
        // retorna o time salvo
        return savedTeam;
    }


    // deletar time
    public void deleteTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Time com ID " + teamId + " não encontrado.");
        }

        validarDono(team);

        teamRepository.deleteById(teamId);
    }

    // metodo auxiliar para pegar o email do usuário logado
    private String getEmailLogado() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (principal instanceof User) {
            return ((User) principal).getUsername(); // o metodo getUsername() retorna o email
        } else {
            return principal.toString();
        }
    }

     // metodo auxiliar para validar dono do time
    private void validarDono(Team team) {
        String emailLogado = getEmailLogado();
        
        // Verifica se o time tem dono e se o email bate
        if (team.getOwner() == null || !team.getOwner().getEmail().equals(emailLogado)) {
            throw new RuntimeException("ACESSO NEGADO: Você não é o dono deste time.");
        }
    }
    
}

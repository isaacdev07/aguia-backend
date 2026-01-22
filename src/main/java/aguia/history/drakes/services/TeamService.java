package aguia.history.drakes.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.Season;
import aguia.history.drakes.domain.Team;
import aguia.history.drakes.repositories.SeasonRepository;
import aguia.history.drakes.repositories.TeamRepository;
import jakarta.transaction.Transactional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Transactional
    public Team createTeam(Team team){
        //salvar time
        Team savedTeam = teamRepository.save(team);
        //cria temporada inicial pegando o ano atual automaticamente
        Season currentSeason = new Season();
        currentSeason.setYear(LocalDate.now().getYear());
        currentSeason.setDescription("Temporada inicial " + currentSeason.getYear());
        currentSeason.setTeam(savedTeam);  

        seasonRepository.save(currentSeason);  

        return savedTeam;
    }

    public void deleteTeam(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new RuntimeException("Time com ID " + teamId + " não encontrado.");
        }
        teamRepository.deleteById(teamId);
    }
    
}

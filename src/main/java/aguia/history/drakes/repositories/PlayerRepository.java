package aguia.history.drakes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aguia.history.drakes.domain.Player;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    // metodo para encontrar jogadores por time
    List<Player> findByTeamId(Long teamId);
}
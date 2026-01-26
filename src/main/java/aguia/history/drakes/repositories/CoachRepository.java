package aguia.history.drakes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import aguia.history.drakes.domain.Coach;

public interface CoachRepository extends JpaRepository<Coach, Long>{
    
}

package aguia.history.drakes.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_seasons")
public class Season {

    @Id     
    @GeneratedValue(strategy = GenerationType.IDENTITY)// id com auto incremento
    private Long id;

    @Column(nullable = false)
    private Integer year; //ano da temporada

    private String description; //descrição da temporada

    // relacionamento com Team
    @ManyToOne 
    @JoinColumn(name = "team_id", nullable = false) 
    private Team team;
    
}

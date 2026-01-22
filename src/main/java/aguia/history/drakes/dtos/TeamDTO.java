package aguia.history.drakes.dtos;

import java.time.LocalDate;

import aguia.history.drakes.domain.Team;

public class TeamDTO {
    private Long id;
    private String name;
    private String city;
    private LocalDate foundationDate;

    public TeamDTO() {}

    public TeamDTO(Team entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.city = entity.getCity();
        this.foundationDate = entity.getFoundationDate();
    }

    public Team toEntity() {
        Team team = new Team();
        team.setId(this.id);
        team.setName(this.name);
        team.setCity(this.city);
        team.setFoundationDate(this.foundationDate);
        return team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
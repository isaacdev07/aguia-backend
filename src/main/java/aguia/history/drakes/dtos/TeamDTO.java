package aguia.history.drakes.dtos;


import aguia.history.drakes.domain.Team;

public class TeamDTO {
    private Long id;
    private String name;
    private String city;

    public TeamDTO() {}

    public TeamDTO(Team entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.city = entity.getCity();
    }

    public Team toEntity() {
        Team team = new Team();
        team.setId(this.id);
        team.setName(this.name);
        team.setCity(this.city);
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
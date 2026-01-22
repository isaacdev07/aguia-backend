package aguia.history.drakes.dtos;

import aguia.history.drakes.domain.Player;
import aguia.history.drakes.domain.enums.Position;
// DTO para transferência de dados do jogador
public class PlayerDTO {
    private Long id;
    private String name;
    private Position position;
    private Integer shirtNumber;
    private Long teamId; // ID do time associado

    // construtores, getters e setters
    public PlayerDTO() {}

    public PlayerDTO(Player entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.position = entity.getPosition();
        this.shirtNumber = entity.getShirtNumber();
        if (entity.getTeam() != null) {
            this.teamId = entity.getTeam().getId();
        }
    }

    public Player toEntity() {
        Player player = new Player();
        player.setId(this.id);
        player.setName(this.name);
        player.setPosition(this.position);
        player.setShirtNumber(this.shirtNumber);
        return player;
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
    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public Integer getShirtNumber() {
        return shirtNumber;
    }
    public void setShirtNumber(Integer shirtNumber) {
        this.shirtNumber = shirtNumber;
    }
    public Long getTeamId() {
        return teamId;
    }
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
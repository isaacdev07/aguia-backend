package aguia.history.drakes.dtos;

public class TopCleanSheetDTO {

    private String playerName;
    private Long cleanSheets;

    public TopCleanSheetDTO(String playerName, Long cleanSheets) {
        this.playerName = playerName;
        this.cleanSheets = cleanSheets;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Long getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(Long cleanSheets) {
        this.cleanSheets = cleanSheets;
    }
	
}

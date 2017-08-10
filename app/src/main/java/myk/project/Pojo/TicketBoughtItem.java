package myk.project.Pojo;

/**
 * Created by mike on 6/21/17.
 */

public class TicketBoughtItem {
    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketcode() {
        return ticketcode;
    }

    public void setTicketcode(String ticketcode) {
        this.ticketcode = ticketcode;
    }

    public boolean isSeason() {
        return isSeason;
    }

    public void setSeason(boolean season) {
        isSeason = season;
    }

    String matchId;
    String userId;
    String ticketcode;

    boolean isSeason;

}

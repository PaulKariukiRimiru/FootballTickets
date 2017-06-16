package com.example.mike.footballtickets.Pojo;

/**
 * Created by mike on 6/16/17.
 */

public class SeasonObject extends IMainObject {
    String clubname;
    String clubStadium;
    String clubLocation;
    String clubId;


    int price;

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getClubStadium() {
        return clubStadium;
    }

    public void setClubStadium(String clubStadium) {
        this.clubStadium = clubStadium;
    }

    public String getClubLocation() {
        return clubLocation;
    }

    public void setClubLocation(String clubLocation) {
        this.clubLocation = clubLocation;
    }
}

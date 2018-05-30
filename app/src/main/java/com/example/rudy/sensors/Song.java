package com.example.rudy.sensors;

/**
 * Created by Rudy on 26.04.2018.
 */

public class Song {

    private String name, species;
    private int iconId;

    public Song(String name, String species, int iconId){
        this.name = name;
        this.species = species;
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getSongName() {
        return name;
    }

    public void setSongName(String songName) {
        this.name = songName;
    }

    public String getSongSpecies() {
        return species;
    }

    public void setSongSpecies(String songSpecies) {
        this.species = songSpecies;
    }
}

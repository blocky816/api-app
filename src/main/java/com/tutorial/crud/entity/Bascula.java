package com.tutorial.crud.entity;

import javax.persistence.*;

@Entity
@Table(name = "basculas")
public class Bascula {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "club")
    private String clubName;
    @Column(name = "nombre")
    private String weighingMachineName;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getClubName() { return clubName; }

    public void setClubName(String clubName) { this.clubName = clubName; }

    public String getWeighingMachineName() { return weighingMachineName; }

    public void setWeighingMachineName(String weighingMachineName) { this.weighingMachineName = weighingMachineName; }

    @Override
    public String toString() {
        return "Bascula{" +
                "id=" + id +
                ", clubName='" + clubName + '\'' +
                ", weighingMachineName='" + weighingMachineName + '\'' +
                '}';
    }
}

package com.xa1y5k.tutorial.user;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity //fogunk tudni táblát létrehozni az adatbázisba
@Table(name = "users") //usernek nevezzük el a tablet
public class User {
    private @Id @GeneratedValue long id; //első oszlop az id; magától új ID generálódik az adatbázisba az új soroknál
    private @NotBlank String felhasznalo;
    private @NotBlank String jelszo;
    private@NotBlank String valodinev;

    public User() {
    }
    public User(@NotBlank String felhasznalo,
                @NotBlank String jelszo,
                @NotBlank String valodinev) {
        this.felhasznalo = felhasznalo;
        this.jelszo = jelszo;
        this.valodinev = valodinev;
    }
    public long getId() {
        return id;
    }
    public String getFelhasznalo() {
        return felhasznalo;
    }
    public void setUsername(String felhasznalo) {
        this.felhasznalo = felhasznalo;
    }
    public String getJelszo() {
        return jelszo;
    }
    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getValodinev(){return valodinev;}
    public void setValodinev(String valodinev) {this.valodinev = valodinev;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(felhasznalo, user.felhasznalo) &&
                Objects.equals(jelszo, user.jelszo) &&
                Objects.equals(valodinev, user.valodinev);
    }

}

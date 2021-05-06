package com.xa1y5k.tutorial.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    //regisztráció
    @PostMapping("/users/register")
    public Status registerUser(@Valid @RequestBody User newUser) {
        List<User> users = userRepository.findAll();

        for (User user : users) {

            //a felhasználónév legalább hat karakterből álljon
            if (newUser.getFelhasznalo().length() < 6){
                return Status.TUL_ROVID_FELHASZNALONEV;
            }

            //a regisztrációnál adjunk meg valódi nevet, nélküle sikertelen post kérés
            if (newUser.getValodinev().length() < 1){
                return Status.ADJA_MEG_A_NEVET;
            }

            //a jelszó legalább 8 karakter
            if (newUser.getJelszo().length() < 8){
                return Status.TUL_ROVID_JELSZO; //ez a jelszó hosszát nézi
            }

            //a jelszó ne csak betűkből álljon
            if (!newUser.getJelszo().matches(".*[^a-zA-Z.].*")) {
                return Status.CSAK_BETU_VAN_A_JELSZOBAN;
            }

            //a jelszó ne csak számokból álljon
            if (!newUser.getJelszo().matches(".*[^0-9].*")) {
                return Status.CSAK_SZAM_VAN_A_JELSZOBAN;
            }

            //ne legyen két ugyanolyan nevű felhasználó
            if (user.getFelhasznalo().equals(newUser.getFelhasznalo())) {

                return Status.EZZEL_A_FELHASZNALONEVVEL_MAR_REGISZTRALTAK; //ez nézi meg, hogy van e már ilyen nevű felhasználó az adatbázisban
            }

        }

        //az adattárolóba titkosítva mentjük a jelszót (minden betűt kivéve a z-t eggyel előre léptet)

        StringBuilder kodolas = new StringBuilder();
        char[] betu = newUser.getJelszo().toCharArray();

        for (int i = 0; i < betu.length; i++) {
            if (Character.toLowerCase(betu[i]) != 'z' && Character.isLetter(betu[i])) {

                char kovetkezo = (char) ((int) betu[i] + 1);
                kodolas.append(kovetkezo);
            } else {

                kodolas.append(betu[i]);
            }
        }
        newUser.setJelszo(kodolas.toString());

        //elmentjük az új felhasználónk adatait
        userRepository.save(newUser);
        return Status.SIKER;
    }

    //bejelentkezés
    @PostMapping("/users/login")
    public Status loginUser(@Valid @RequestBody User belepo) {

        //a belépő által megadott kódot titkosítjuk, hiszen titkosítva van az adatbázisban, ezért csak
        //akkor látjuk, hogy jó-e a kód, ha titkosítva hasonlítjuk össze

        StringBuilder kodolas = new StringBuilder();
        char[] betu = belepo.getJelszo().toCharArray();

        for (int i = 0; i < betu.length; i++) {
            if (Character.toLowerCase(betu[i]) != 'z' && Character.isLetter(betu[i])) {

                char kovetkezo = (char) ((int) betu[i] + 1);
                kodolas.append(kovetkezo);
            } else {

                kodolas.append(betu[i]);
            }
        }
        belepo.setJelszo(kodolas.toString());

        List<User> users = userRepository.findAll();

        //megnézzük hogy a felhasználónév és a jelszó egyezik e
        for (User other : users) {
            if (other.getFelhasznalo().equals(belepo.getFelhasznalo())){

                if(other.getJelszo().equals(belepo.getJelszo())){

                    return Status.SIKER;
                }

            }

        }
        return Status.SIKERTELEN;

    }

}





package nl.jordyu.sudoku;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cell {

    private int antwoord = 0; //0 = antwoord onbekend.
    private boolean[] kandidaatAntwoorden = new boolean[10]; //Getalmogelijkheden 1 tot 9
    private int aantalKandidaatGetallen = 9;

    private Map<CellGroep.Soort, CellGroep> cellGroepen = new HashMap<>();

    // Een Cell initieren, waarvan de waarde nog niet bekend is.
    public Cell() {
        Arrays.fill(kandidaatAntwoorden, true);
    }

    // Wanneer het getal al bekend is, kan deze constructor aangeroepen worden.
    public Cell(int antwoord) {
        setAntwoord(antwoord);
    }

    // Haal een kandidaatgetal weg.
    // Dit gebeurt als dit getal voorkomt in een rij, kolom of 3x3blok.
    // Returnvale: TRUE als deze cell maar 1 mogelijkheid over heeft.
    public boolean haalKandidaatAntwoordWeg(int kandidaatAntwoord) {
        if (kandidaatAntwoorden[kandidaatAntwoord]) {
            kandidaatAntwoorden[kandidaatAntwoord] = false;
            aantalKandidaatGetallen--;
            //todo: Zorg ervoor dat gerapporteerd wordt dat de andere cellen kun kandidaten moeten bijstellen.

            return aantalKandidaatGetallen == 1;
        }
        return false;
    }

    // Getters en Setters.
    public int getAntwoord() { return antwoord; }

    public boolean getKandidaatGetal(int kandidaatGetal) { return kandidaatAntwoorden[kandidaatGetal]; }

    //Set rij, kolom of 3x3blok
    public void setCelGroep(CellGroep groep, CellGroep.Soort soort) {
        cellGroepen.put(soort, groep);
    }

    //Get rij, kolom of 3x3blok
    public CellGroep getCelGroep(CellGroep.Soort soort) {
        return cellGroepen.get(soort);
    }

    public boolean isBekend() {
        return antwoord != 0;
    }

    public void setAntwoord(int antwoord) {
        this.antwoord = antwoord;
        Arrays.fill(kandidaatAntwoorden, false);
        aantalKandidaatGetallen = 0;

        //Verwijder kandidaatgetallen bij omliggende cellen.
        cellGroepen.forEach((soort, groep) -> {
            groep.verwijderKandidaatGetal(antwoord);
        });
    }
}

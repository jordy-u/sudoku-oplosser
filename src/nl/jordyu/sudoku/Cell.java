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
    public void haalKandidaatAntwoordWeg(int kandidaatAntwoord) {
        if (kandidaatAntwoorden[kandidaatAntwoord]) {
            kandidaatAntwoorden[kandidaatAntwoord] = false;
            aantalKandidaatGetallen--;

            if (aantalKandidaatGetallen == 1) {
                setAntwoord(getEnigeKandidaatAntwoord());
            }
        }
    }

    private int getEnigeKandidaatAntwoord() {
        for (int i=1; i<10; i++) {
            if (kandidaatAntwoorden[i]) return i;
        }
        throw new RuntimeException("Geen enkel kandidaat-antwoord mogelijk of cell heeft al een antwoord.");
    }

    //Verwijder kandidaat-antwoorden bij omliggende cellen.
    public void updateGroepen() {
        cellGroepen.forEach((soort, groep) -> groep.verwijderKandidaatGetal(antwoord));
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

        SudokuWachtrij.voegNieuwAntwoordToe(this);
    }
}

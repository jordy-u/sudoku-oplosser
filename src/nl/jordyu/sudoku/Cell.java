package nl.jordyu.sudoku;

import java.util.HashMap;
import java.util.Map;

public class Cell {

    private int getal = 0;
    private boolean[] kandidaatGetallen = new boolean[10]; //Getalmogelijkheden 1 tot 9
    private int aantalKandidaatGetallen = 9;

    private Map<CellGroep.Soort, CellGroep> celGroepen = new HashMap<>();


    // Haal een kandidaatgetal weg.
    // Dit gebeurt als dit getal voorkomt in een rij, kolom of 3x3blok.
    // Returnvale: TRUE als deze cell maar 1 mogelijkheid over heeft.
    public boolean haalKandidaatgetalWeg(int kandidaatGetal) {
        if (kandidaatGetallen[kandidaatGetal]) {
            kandidaatGetallen[kandidaatGetal] = false;
            aantalKandidaatGetallen--;

            return aantalKandidaatGetallen == 1;
        }
        return false;
    }

    // Getters en Setters.
    public int getGetal() { return getal; }

    public boolean getKandidaatGetal(int kandidaatGetal) { return kandidaatGetallen[kandidaatGetal]; }

    //Set rij, kolom of 3x3blok
    public void setCelGroep(CellGroep groep, CellGroep.Soort soort) {
        celGroepen.put(soort, groep);
    }

    //Get rij, kolom of 3x3blok
    public CellGroep getCelGroep(CellGroep.Soort soort) {
        return celGroepen.get(soort);
    }

}

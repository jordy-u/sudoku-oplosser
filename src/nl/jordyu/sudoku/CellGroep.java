package nl.jordyu.sudoku;

import java.util.ArrayList;
import java.util.List;

public class CellGroep {

    enum Soort {
        RIJ,
        KOLOM,
        BLOK,
    }

    //Elke cell bevindt zich in 3 groepen: rij, kolom en 3x3blok.
    private Cell[] cellen = new Cell[9];
    private List<Cell> onbekendeCellen = new ArrayList<>();

    private int aantalCellenToegevoegd = 0;
    private boolean[] bekendeGetallen = new boolean[10]; //Getallen 1 tot 9, welke in deze groep al ingevuld zijn.

    public void voegCellToeAanGroep(Cell cell) {
        cellen[aantalCellenToegevoegd++] = cell;

        if (!cell.isBekend()) {
            onbekendeCellen.add(cell);
        }
    }

    public boolean checkVoorUniekeKandidaatAntwoordenInGroep() {

        boolean minstensEenNieuwAntwoord = false;

        for (int i=1; i<10; i++) {
            if (bekendeGetallen[i]) continue;

            int aantalMogelijkeCellen = 0;
            Cell mogelijkeCell = null;

            for (Cell cell : onbekendeCellen) {
                if (cell.getKandidaatGetal(i)) {
                    aantalMogelijkeCellen++;
                    mogelijkeCell = cell;
                }
            }

            if (aantalMogelijkeCellen == 1) {
                mogelijkeCell.setAntwoord(i);
                bekendeGetallen[i] = true;
                minstensEenNieuwAntwoord = true;
            }

        }

        return minstensEenNieuwAntwoord;
    }

    // Wanneer er een antwoord is gevonden, moeten de kandidaatAntwoorden van alle cellen in de groep bijgewerkt worden.
    public void verwijderKandidaatGetal(int antwoord) {
        for (Cell cell : onbekendeCellen) {
            cell.haalKandidaatAntwoordWeg(antwoord);
        }
    }

    public Cell[] getCellen() {
        return cellen;
    }

}

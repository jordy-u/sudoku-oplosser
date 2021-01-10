package nl.jordyu.sudoku;

public class CellGroep {

    enum Soort {
        RIJ,
        KOLOM,
        BLOK,
    }

    //Elke cell bevindt zich in 3 groepen: rij, kolom en 3x3blok.
    private Cell[] cellen = new Cell[9];
    private int aantalCellenToegevoegd = 0;

    public void voegCellToeAanGroep(Cell cell) {
        cellen[aantalCellenToegevoegd++] = cell;
    }


}

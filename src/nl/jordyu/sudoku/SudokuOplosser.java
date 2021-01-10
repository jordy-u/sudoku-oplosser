package nl.jordyu.sudoku;

import java.util.List;

public class SudokuOplosser {

    private final List<Cell> alleCellen;
    private final CellGroep[] rijen = new CellGroep[9];
    private final CellGroep[] kolommen = new CellGroep[9];
    private final CellGroep[] blokken = new CellGroep[9];

    public SudokuOplosser(List<Cell> alleCellen) {
        this.alleCellen = alleCellen;
        voegCellenToeAanGroepen();
        SudokuWachtrij.updateKandidaatAntwoorden();
    }


    /*
            Blok Id van 3x3-blok:
            0   1   2
            3   4   5
            6   7   8

            Som van kolom/3 en rij/3, maar rijen zijn 3x zoveel waard.
            Rij/3*3 zorgt voor afronden naar eerste rij van de 3:
            Rijen 0,1,2=0
            Rijen 3,4,5=3
            Rijen 6,7,8=6

            Dus rij=6, kolom=8 wordt blokId=8.
                kolom 8/3        =  2 (afronding naar beneden, want het is een int (geheel getal))
                rij 6/3 = 2. 2x3 =  6
                _______________________+
                blokId           =  8
             */
    private void voegCellenToeAanGroepen() {
        // Maak groepen aan
        for (int i=0; i<9; i++) {
            rijen[i] = new CellGroep();
            kolommen[i] = new CellGroep();
            blokken[i] = new CellGroep();
        }

        // Zorg dat de cellen in de juiste rijen, kolommen en 3x3-blokken komen.
        for (int i=0; i<81; i++) {
            Cell dezeCell = alleCellen.get(i);

            int rij = i/9;
            int kolom = i%9;

            rijen[rij].voegCellToeAanGroep(dezeCell);
            dezeCell.setCelGroep(rijen[rij], CellGroep.Soort.RIJ);
            
            kolommen[kolom].voegCellToeAanGroep(dezeCell);
            dezeCell.setCelGroep(kolommen[kolom], CellGroep.Soort.KOLOM);

            int blokId = rij/3*3 + kolom/3;
            blokken[blokId].voegCellToeAanGroep(dezeCell);
            dezeCell.setCelGroep(blokken[blokId], CellGroep.Soort.BLOK);
        }
    }
    
    public CellGroep[] getRijen() {
        return rijen;
    }
    
}

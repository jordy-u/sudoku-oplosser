package nl.jordyu.sudoku;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SudokuOplosser {

    private final List<Cell> alleCellen;
    private final CellGroep[] rijen = new CellGroep[9];
    private final CellGroep[] kolommen = new CellGroep[9];
    private final CellGroep[] blokken = new CellGroep[9];
    private EnumMap<CellGroep.Soort, CellGroep[]> groepen = new EnumMap<>(CellGroep.Soort.class);

    public SudokuOplosser(List<Cell> alleCellen) {
        // Voeg rijen, kolommen en 3x3-blokken toe aan groepen-lijst.
        groepen.put(CellGroep.Soort.RIJ, rijen);
        groepen.put(CellGroep.Soort.KOLOM, kolommen);
        groepen.put(CellGroep.Soort.BLOK, blokken);
        
        this.alleCellen = alleCellen;
        voegCellenToeAanGroepen();
        SudokuWachtrij.updateKandidaatAntwoorden();

        Main.printSudokuResultaat(rijen, null);
        
        // Ga alle groepen langs, tot er geen nieuwe antwoorden gevonden kunnen worden.
        while(checkVoorUniekeKandidaatAntwoorden());
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
            rijen[i] = new CellGroep(CellGroep.Soort.RIJ);
            kolommen[i] = new CellGroep(CellGroep.Soort.KOLOM);
            blokken[i] = new CellGroep(CellGroep.Soort.BLOK);
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

    // Check of er 1 kandidaatantwoord in rij, kolom of 3x3-blok is.
    private boolean checkVoorUniekeKandidaatAntwoorden() {
        boolean minstensEenUpdate = false;

        for (Map.Entry<CellGroep.Soort, CellGroep[]> cellGroepen : groepen.entrySet()) {
            for (CellGroep cellGroep : cellGroepen.getValue()) {
                if (cellGroep.checkVoorUniekeKandidaatAntwoordenInGroep()) {
                    minstensEenUpdate = true;
                    SudokuWachtrij.updateKandidaatAntwoorden();
                }
                System.out.println("-------------------");
                Main.printSudokuResultaat(rijen, cellGroep.toCellList());
            }
        }
        return minstensEenUpdate;
    }
    
}

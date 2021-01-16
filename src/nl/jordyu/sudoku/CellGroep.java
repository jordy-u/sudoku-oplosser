package nl.jordyu.sudoku;

import java.util.*;

public class CellGroep {

    enum Soort {
        RIJ,
        KOLOM,
        BLOK,
    }

    //Elke cell bevindt zich in 3 groepen: rij, kolom en 3x3blok.
    private Cell[] cellen = new Cell[9];
    private Map<Integer, Cell> onbekendeCellen = new HashMap<>();
    private final Soort soort;

    private int aantalCellenToegevoegd = 0;
    private boolean[] bekendeGetallen = new boolean[10]; //Getallen 1 tot 9, welke in deze groep al ingevuld zijn.

    public CellGroep(Soort soort) {
        this.soort = soort;
    }

    public void voegCellToeAanGroep(Cell cell) {
        cellen[aantalCellenToegevoegd] = cell;

        if (!cell.isBekend()) {
            onbekendeCellen.put(aantalCellenToegevoegd, cell);
        }
        aantalCellenToegevoegd++;
    }

    public boolean checkVoorUniekeKandidaatAntwoordenInGroep() {

        boolean minstensEenNieuwAntwoord = false;

        for (int kandidaatAntwoord=1; kandidaatAntwoord<10; kandidaatAntwoord++) {
            if (bekendeGetallen[kandidaatAntwoord]) continue;

            Map<Integer, Cell> kandidaatCellenInSubgroep = new HashMap<>();

            int aantalMogelijkeCellen = 0;
            Cell mogelijkeCell = null;

            for (Map.Entry<Integer, Cell> cellEntry : onbekendeCellen.entrySet()) {
                if (cellEntry.getValue().getKandidaatGetal(kandidaatAntwoord)) {
                    aantalMogelijkeCellen++;
                    mogelijkeCell = cellEntry.getValue();
                    kandidaatCellenInSubgroep.put(cellEntry.getKey(), cellEntry.getValue());
                }
            }

            if (aantalMogelijkeCellen == 1) {
                mogelijkeCell.setAntwoord(kandidaatAntwoord);
                bekendeGetallen[kandidaatAntwoord] = true;
                minstensEenNieuwAntwoord = true;
            } else {
                behandelKandidaatAntwoordenInZelfdeSubgroep(kandidaatCellenInSubgroep, kandidaatAntwoord);
            }

        }

        return minstensEenNieuwAntwoord;
    }

    // Wanneer er een antwoord is gevonden, moeten de kandidaatAntwoorden van alle cellen in de groep bijgewerkt worden.
    public void verwijderKandidaatGetal(int antwoord) {
        onbekendeCellen.forEach((id, cell) -> cell.haalKandidaatAntwoordWeg(antwoord));
    }

    // Wanneer een antwoord in een bepaalde subgroep MOET zitten, dan zit hij niet in de andere subgroepen.
    // Haal de kandidaat-antwoorden uit die andere subgroepen.
    public void verwijderKandidaatGetal(int antwoord, Map<Integer, Cell> uitzonderingCellen) {
        //todo checken
        onbekendeCellen.forEach((id, cell) -> {
            if (!uitzonderingCellen.containsValue(cell))
                cell.haalKandidaatAntwoordWeg(antwoord);
        });
    }

    public Cell[] getCellen() {
        return cellen;
    }

    /*
    Subgroep is een van de volgende groepen:
    * In rij:   Een groep van 3 cellen naast elkaar in hetzelfde blok.
    * In kolom: Een groep van 3 cellen onder elkaar in hetzelfde blok.
    * In Blok:  Een groep van 3 cellen naast elkaar.
    * In Blok:  Een groep van 3 cellen onder elkaar.

    Als alle kandidaatantwoorden in hetzelfde subgroep zitten,
        dan kunnen deze antwoorden voorkomen in andere plekken binnen dezelfde rij/kolom/groep.

    if blok:
		rij:
			check [0,1,2] vs [3,4,5] vs [6,7,8]
		kolom:
			check [0,3,6] vs [1,4,7] vs [2,5,8]

	if rij of blok
		check [0,1,2] vs [3,4,5] vs [6,7,8]
     */
    private void behandelKandidaatAntwoordenInZelfdeSubgroep(Map<Integer, Cell> cellen, int antwoordKandidaat) {
        // Horizontaal
        if (soort == Soort.RIJ || soort == Soort.BLOK) {
            int subgroepId = -1;
            for (Map.Entry<Integer, Cell> cell : cellen.entrySet()) {
                if (subgroepId == -1) {
                    subgroepId = getSubgroepByCellIdHorizontaal(cell.getKey());
                } else {
                    if (subgroepId != getSubgroepByCellIdHorizontaal(cell.getKey())) {
                        // Kandidaatgetal behoort tot meerdere subgroepen.
                        subgroepId = -1;
                        break;
                    }
                }
            }

            if (subgroepId != -1) {
                Cell willekeurigeCellInSubgroep = cellen.entrySet().iterator().next().getValue();
                if (soort == Soort.RIJ)
                    willekeurigeCellInSubgroep.getCelGroep(Soort.BLOK).verwijderKandidaatGetal(antwoordKandidaat, cellen);
                else // BLOK
                    willekeurigeCellInSubgroep.getCelGroep(Soort.RIJ).verwijderKandidaatGetal(antwoordKandidaat, cellen);
            }
        }

        // Verticaal
        if (soort == Soort.KOLOM || soort == Soort.BLOK) {
            int subgroepId = -1;
            for (Map.Entry<Integer, Cell> cell : cellen.entrySet()) {
                if (subgroepId == -1) {
                    subgroepId = getSubgroepByCellIdVerticaal(cell.getKey());
                } else {
                    if (subgroepId != getSubgroepByCellIdVerticaal(cell.getKey())) {
                        // Kandidaatgetal behoort tot meerdere subgroepen.
                        subgroepId = -1;
                        break;
                    }
                }
            }

            if (subgroepId != -1) {
                Cell willekeurigeCellInSubgroep = cellen.entrySet().iterator().next().getValue();
                if (soort == Soort.KOLOM)
                    willekeurigeCellInSubgroep.getCelGroep(Soort.BLOK).verwijderKandidaatGetal(antwoordKandidaat, cellen);
                else // BLOK
                    willekeurigeCellInSubgroep.getCelGroep(Soort.KOLOM).verwijderKandidaatGetal(antwoordKandidaat, cellen);
            }
        }
    }

    public int getSubgroepByCellIdHorizontaal(int cellId) {
        if (soort == Soort.KOLOM) throw new RuntimeException("getSubgroepByCellIdHorizontaal aangeroepen voor kolom");
        return cellId / 3;
    }

    public int getSubgroepByCellIdVerticaal(int cellId) {
        if (soort == Soort.RIJ) throw new RuntimeException("getSubgroepByCellIdVerticaal aangeroepen voor rij");
        if (soort == Soort.KOLOM)
            return cellId / 3;
        else //BLOK
            return cellId % 3;
    }

    public void antwoordOpOnbekendeCell(Cell cell) {
        if (onbekendeCellen.containsValue(cell)) {
            bekendeGetallen[cell.getAntwoord()] = true;
        }
    }

    public List<Cell> toCellList() {
        return Arrays.asList(cellen);
    }
}

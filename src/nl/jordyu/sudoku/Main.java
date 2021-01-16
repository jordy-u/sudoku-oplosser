package nl.jordyu.sudoku;

import java.util.List;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
	// write your code here
        SudokuWachtrij.getInstance(); //instantiëer SudokuWachtrij

        List<Cell> alleCellen = ReadSudokuFile.leesSudoku("sudoku-voorbeelden/sudoku_5ster.txt");

        SudokuOplosser oplosser = new SudokuOplosser(alleCellen);

        System.out.println("Programma afgelopen.");
        printSudokuResultaat(oplosser.getRijen(), null);

    }

    // Print de sudoku zonder opmaak.
    public static void printSudokuResultaat(CellGroep[] rijen) {
        printSudokuResultaat(rijen, new ArrayList<>());
    }
    
    public static void printSudokuResultaat(CellGroep[] rijen, List<Cell> highlightCellen) {
        for (CellGroep rij : rijen) {
            System.out.println(convertRijToString(rij, highlightCellen));
        }
    }
    
    public static String convertRijToString(CellGroep rij, List<Cell> highlightCellen) {
        String rijString = "";
        for (Cell cell : rij.getCellen()) {
            String antwoord = (cell.getAntwoord() != 0) ? String.valueOf(cell.getAntwoord()) : "";

            if (highlightCellen.contains(cell))
                antwoord = ANSI_YELLOW + antwoord + ANSI_RESET;

            rijString += antwoord + "\t";
        }
        return rijString;
    }
}

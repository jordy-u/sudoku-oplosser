package nl.jordyu.sudoku;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SudokuWachtrij.getInstance(); //instantiëer SudokuWachtrij

        List<Cell> alleCellen = ReadSudokuFile.leesSudoku("sudoku-voorbeelden/sudoku_5ster.txt");

        SudokuOplosser oplosser = new SudokuOplosser(alleCellen);

        System.out.println("Programma afgelopen.");
        printSudokuResultaat(oplosser.getRijen());

    }
    
    public static void printSudokuResultaat(CellGroep[] rijen) {
        for (CellGroep rij : rijen) {
            System.out.println(convertRijToString(rij));
        }
    }
    
    public static String convertRijToString(CellGroep rij) {
        String rijString = "";
        for (Cell cell : rij.getCellen()) {
            if (cell.getAntwoord() != 0) rijString += cell.getAntwoord();
            rijString += "\t";
        }
        return rijString;
    }
}

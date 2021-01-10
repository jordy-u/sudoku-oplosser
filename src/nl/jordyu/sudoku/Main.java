package nl.jordyu.sudoku;

import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        SudokuWachtrij.getInstance(); //instantiëer SudokuWachtrij

        List<Cell> alleCellen = ReadSudokuFile.leesSudoku("sudoku.txt");

        System.out.println("Programma afgelopen.");

    }
}

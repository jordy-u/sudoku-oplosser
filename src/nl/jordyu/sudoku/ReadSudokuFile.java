package nl.jordyu.sudoku;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files

// Bron: https://www.w3schools.com/java/java_files_read.asp

public class ReadSudokuFile {

    private ReadSudokuFile() {} // prevents instances, alleen static functies.

    public static List<Cell> leesSudoku(String filename) {

        List<String> tekstRegels = new ArrayList<>();

        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            int aantalGelezenRegels = 0;
            while (myReader.hasNextLine() && aantalGelezenRegels <= 9) {
                String data = myReader.nextLine();
                tekstRegels.add(data);
                System.out.println(data);
                aantalGelezenRegels++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        List<Cell> alleCellen = new ArrayList<>();

        // Tekstregel splitten met tabs.
        // bron: https://javarevisited.blogspot.com/2017/01/how-to-split-string-based-on-delimiter-in-java.html#axzz6jA1dze6F
        for (String tekstRegel : tekstRegels) {
            String[] celWaardesInRij = tekstRegel.split("\t");

            for (String celWaarde : celWaardesInRij) {
                if (!celWaarde.isEmpty())
                    alleCellen.add(new Cell(Integer.parseInt(celWaarde)));
                else
                    alleCellen.add(new Cell());
            }
        }

        return alleCellen;

    }
}
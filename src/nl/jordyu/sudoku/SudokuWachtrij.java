package nl.jordyu.sudoku;

import java.util.ArrayList;

public final class SudokuWachtrij
{

    private static SudokuWachtrij INSTANCE;
    private ArrayList<Cell> nieuweCellenMetAntwoorden = new ArrayList<>();

    private SudokuWachtrij()
    {
    }

    public static SudokuWachtrij getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new SudokuWachtrij();
        }

        return INSTANCE;
    }

    public static void voegNieuwAntwoordToe(Cell cell) {
        INSTANCE.nieuweCellenMetAntwoorden.add(cell);
    }

    public static void updateKandidaatAntwoorden() {
        // Update 1-voor-1 alle cellen met antwoorden.
        // In deze while loop kunnen tussendoor nieuwe cellen-met-antwoorden worden toegevoegd.
        while (! INSTANCE.nieuweCellenMetAntwoorden.isEmpty()) {
            Cell cellMetNieuwAntwoord = INSTANCE.nieuweCellenMetAntwoorden.remove(0);
            cellMetNieuwAntwoord.updateGroepen();
        }
    }
}
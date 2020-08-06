package com.example.solverProgram;

public class sudokuTest {
    public static void main(String[] args) {
        int[][] sudokuTest1 = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println("Test 1");
        sudoku test1 = new sudoku(sudokuTest1);

        int[][] sudokuTest2 = {
                {1, 0, 4, 0, 0, 0, 0, 0, 2},
                {0, 4, 0, 0, 0, 0, 0, 0, 0}, // there are two 4's in the square so it will come out invalid.
                {0, 0, 3, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println("Test 2");
        sudoku test2 = new sudoku(sudokuTest2);
        test2.displaySudoku();
        test2.displaySudokuDebug("isEmpty");

        int[][] sudokuTest3 = {
                {1, 0, 7, 0, 0, 0, 0, 0, 2},
                {0, 4, 0, 0, 0, 0, 0, 0, 0},
                {5, 0, 3, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println("Test 3");
        sudoku test3 = new sudoku(sudokuTest3);
        test3.displaySudoku();
        test3.insertNumber(9, 4, 5);
        test3.displaySudoku();
        test3.insertNumber(11, 4, 5);
        test3.removeNumber(4, 5);
        test3.displaySudoku();
        test3.checkIfCompleted();

        int[][] sudokuTest4 = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {5, 0, 6, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {4, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println("Test 4");
        sudoku test4 = new sudoku(sudokuTest4);
        test4.displaySudoku();
        test4.insertNumber(1, 4, 0);
        test4.displaySudoku();
        test4.displaySudokuDebug("isColumnValid");
        test4.removeNumber(4, 0);
        test4.displaySudokuDebug("isColumnValid");
        test4.insertNumber(5, 2, 3);
        test4.displaySudoku();
        test4.displaySudokuDebug("isRowValid");
        test4.displaySudokuDebug("isSquareValid");
        test4.insertNumber(0,2,3);
        test4.displaySudokuDebug("isRowValid");
        test4.displaySudokuDebug("isSquareValid");



    }

}

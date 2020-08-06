package com.example.solverProgram;

public class solverTest {
    public static void main(String[] args) {
        int[][] solverTest1 = {
                {0, 0, 4, 0, 0, 0, 1, 2, 0},
                {0, 9, 8, 0, 2, 6, 3, 0, 0},
                {0, 3, 0, 0, 9, 7, 0, 0, 5},
                {8, 7, 1, 3, 5, 4, 2, 9, 0},
                {0, 0, 0, 0, 6, 0, 0, 8, 0},
                {0, 5, 6, 0, 0, 9, 0, 0, 0},
                {9, 0, 5, 0, 7, 0, 0, 0, 2},
                {0, 2, 0, 0, 3, 0, 0, 0, 0},
                {6, 8, 3, 2, 0, 5, 9, 0, 7}
        };

        System.out.println("Solver Test 1");
        sudoku test1 = new sudoku(solverTest1);
        test1.displaySudoku();

        solver sTest1 = new solver(test1);
//        System.out.println(sTest1.solvePuzzle());
        System.out.println(sTest1.solvePuzzle());
    }
}

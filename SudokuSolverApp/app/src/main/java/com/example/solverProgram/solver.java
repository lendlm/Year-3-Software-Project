package com.example.solverProgram;

public class solver {

    private sudoku OGpuzzle; // to hold the initial state of the sudoku.
//    private sudoku tempPuzzle;
    private sudoku solvedPuzzle; // to hold the final state of the sudoku if it is solvable.

    public solver(sudoku puzzle_) {
        OGpuzzle = puzzle_;
//        tempPuzzle = puzzle_;
    }

    public boolean solvePuzzle() {
        if(OGpuzzle.checkIfGridValid()) { // if the grid begins invalid then it will be impossible to solve so this is just to save a lot of time.
            return this.solvePuzzle(OGpuzzle);
        } else {
            return false;
        }
    }

    // first attempt
//    public boolean solvePuzzle(sudoku puzzle, int xPos, int yPos) { // recursive function to fill sudoku.
//        System.out.println("xPos: " + xPos + ", yPos: " + yPos);
//
//        if(puzzle.checkIfCompleted()) { // this will end recursion once completed.
//            puzzle.displaySudoku();
//            solvedPuzzle = puzzle;
//            return true;
//        }
//
//        for(int cellInsert = 1; cellInsert <= 9; cellInsert++) {
//            if(puzzle.insertNumber(cellInsert, yPos, xPos) || puzzle.getCell(xPos, yPos).isImmutable()) {
//                xPos += 1;
//                if(xPos > 8) {
//                    xPos = 0;
//                    yPos += 1;
//                }
//                if(yPos > 8) {
//                    yPos = 0;
//                }
//                if(solvePuzzle(puzzle, xPos, yPos)) {
//                    return true;
//                } else {
//                    puzzle.removeNumber(yPos, xPos);
//                    System.out.println("removed at: " + xPos + ", " + yPos);
//                    puzzle.displaySudoku();
//                }
//            }
//        }
//        puzzle.displaySudoku();
////        puzzle.displaySudokuDebug("isSquareValid");
//        return false;
//    }

    // second attempt
    public boolean solvePuzzle(sudoku puzzle) {
        int xPos = -1;
        int yPos = -1;
        boolean isEmpty = true;

        if(puzzle.checkIfCompleted()) { // base case to exit recursive function.
//            puzzle.displaySudoku();
            solvedPuzzle = puzzle;
            return true;
        }

        for(int y = 0; y < 9; y++) {
            for( int x = 0; x < 9; x++) {
                if(puzzle.getCell(x, y).isEmpty()) { // finds an empty cell and records its coordinates.
                    xPos = x;
                    yPos = y;

                    isEmpty = false;
                    break;
                }
            }
            if(!isEmpty) {
                break;
            }
        }



        for(int cellInsert = 1; cellInsert <= 9; cellInsert++) {
            if(puzzle.insertNumber(cellInsert, yPos, xPos)) { // if the number is valid then it will continue through the sudoku.
//                puzzle.displaySudoku();
                if(solvePuzzle(puzzle)) {
                    return true;
                }
//                else {
////                    puzzle.removeNumber(yPos, xPos);
//                }
            } else {
                puzzle.removeNumber(yPos, xPos);
//                System.out.println("removed at: " + xPos + ", " + yPos);
//                puzzle.displaySudoku();
            }
        }

//        puzzle.displaySudoku();
        return false;
    }

    public sudoku getSolvedPuzzle() {
        return solvedPuzzle;
    }
}
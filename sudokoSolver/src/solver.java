// COMPONENTS:
// recursive??
// backtracking (a way to go back without touching immutable cells)
//


public class solver {

    private sudoku OGpuzzle;
    private sudoku tempPuzzle;
    private sudoku solvedPuzzle;

    public solver(sudoku puzzle_) {
        OGpuzzle = puzzle_;
        tempPuzzle = puzzle_;
    }

    public boolean solvePuzzle() {
        return this.solvePuzzle(OGpuzzle, 9);
    }
    public boolean solvePuzzle(sudoku puzzle, int xPos, int yPos) { // recursive function to fill sudoku.
        System.out.println("xPos: " + xPos + ", yPos: " + yPos);

        if(puzzle.checkIfCompleted()) { // this will end recursion once completed.
            puzzle.displaySudoku();
            return true;
        }

        for(int cellInsert = 1; cellInsert <= 9; cellInsert++) {
            if(puzzle.insertNumber(cellInsert, yPos, xPos) || puzzle.getCell(xPos, yPos).isImmutable()) {
                xPos += 1;
                if(xPos > 8) {
                    xPos = 0;
                    yPos += 1;
                }
                if(yPos > 8) {
                    yPos = 0;
                }
                if(solvePuzzle(puzzle, xPos, yPos)) {
                    return true;
                } else {
                    puzzle.removeNumber(yPos, xPos);
                    System.out.println("removed at: " + xPos + ", " + yPos);
                    puzzle.displaySudoku();
                }
            }
        }
        puzzle.displaySudoku();
//        puzzle.displaySudokuDebug("isSquareValid");
        return false;
    }
    public boolean solvePuzzle(sudoku puzzle, int n) {
        int xPos = -1;
        int yPos = -1;
        boolean isEmpty = true;

        for(int y = 0; y < n; y++) {
            for( int x = 0; x < n; x++) {
                if(puzzle.getCell(x, y).isEmpty()) {
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

        if(puzzle.checkIfCompleted()) {
            puzzle.displaySudoku();
            return true;
        }

        for(int cellInsert = 1; cellInsert <= n; cellInsert++) {
            if(puzzle.insertNumber(cellInsert, yPos, xPos)) {
                puzzle.displaySudoku();
                if(solvePuzzle(puzzle, n)) {
                    return true;
                } else {
//                    puzzle.removeNumber(yPos, xPos);
                }
            } else {
                puzzle.removeNumber(yPos, xPos);
//                System.out.println("removed at: " + xPos + ", " + yPos);
//                puzzle.displaySudoku();
            }
        }

//        puzzle.displaySudoku();
        return false;
    }
}
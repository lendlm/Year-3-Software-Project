package com.example.solverProgram;

public class sudoku {

    private cell[][] grid = new cell[9][9];
    private double percentOfZeros = 0;

    public sudoku(int[][] initial) {
        for(int y = 0; y < grid.length; y++ ) {
            for (int x = 0; x < grid[0].length; x++) {
                if (initial[y][x] == 0) {
                    grid[y][x] = new cell();
                } else {
                    grid[y][x] = new cell(initial[y][x], true);
                }
            }
        }

        if(!checkIfGridValid()) {
            System.out.println("sudoku is invalid. pls check if puzzle is set up correctly.");
        } else {
            System.out.println("initialising complete. good luck.");
        }
    }

    public boolean checkIfGridValid() {
        boolean valid = true;

        for(int y = 0; y < grid.length; y++ ) {
            for (int x = 0; x < grid[0].length; x++) {
                cell temp = grid[y][x]; // to reduce the time taken to find the number. resets the number's maker with the modular operation.

                if(!(temp.isEmpty())) {
//                    System.out.println("y: " + y + " x: " + x + " cell: " + temp.getNumber());
                    if(!checkIfCellValid(y, x)) {
                        System.out.println("not valid here: column: " + y + " row: " + x);
                        valid = false;
                    }
                }
            }
        }

        return valid;
    }
    private boolean checkIfCellValid(int column, int row) {
        cell temp = grid[column][row];
        boolean valid = true;
        if(!validColumn(temp, row, column)) {
            valid = false;
        }
        if(!validRow(temp, column, row)) {
            valid = false;
        }
        if(!validSquare(temp, row, column)) {
            valid = false;
        }
        return valid;
    }
    private boolean validColumn(cell currentCell, int row, int column) {
        boolean valid = true;

        for(int y = 0; y < grid.length; y++) {
            if(y == column) { // the cell should not check with itself.
                continue;
            }
            if(currentCell.getNumber() == grid[y][row].getNumber()) {
//                System.out.println("invalid cell. number already exists in column.");
                grid[column][row].setColumnValid(false);
                grid[y][row].setColumnValid(false);
                valid = false; // the loop does not break after finding the first invalid number in case there are more invalid cells
            }
        }
        return valid;
    }
    private boolean validRow(cell currentCell, int column, int row) {
        boolean valid = true;

        for(int x = 0; x < grid.length; x++) {
            if(x == row) {
                continue;
            }
            if(currentCell.getNumber() == grid[column][x].getNumber()) {
//                System.out.println("invalid cell. number already exists in row.");
                grid[column][row].setRowValid(false);
                grid[column][x].setRowValid(false);
                valid = false;
            }
        }
        return valid;
    }
    private boolean validSquare(cell currentCell, int row, int column) {
        int xSquare = row - row%3; // to look for which 3 x 3 square the cell resides in.
        int ySquare = column - column%3;

        boolean valid = true;

        for(int y = 0; y < 3; y++) {
            int miniy = ySquare + y;
            for(int x = 0; x < 3; x++) {
                int minix = xSquare + x;
//                System.out.println(miniy + " " + minix + " " + column + " " + row);
                if(minix == row && miniy == column) {
//                    System.out.println("miss me");
                    continue;
                }
                if(currentCell.getNumber() == grid[miniy][minix].getNumber()) {
//                    System.out.println("invalid cell. number already exists in square.");
                    grid[column][row].setSquareValid(false);
                    grid[miniy][minix].setSquareValid(false);
//                    System.out.println(grid[miniy][minix] + " " + cell);
                    valid = false;
                }
            }
        }

        return valid;
    }

    public cell getCell(int xPos, int yPos) {
        return grid[yPos][xPos];
    }

    public boolean insertNumber(int newNumber, int column, int row) {
        if(grid[column][row].isImmutable()) {
            System.out.println("cell cannot be altered.");
            return false;
        }
        if(newNumber == 0) {
            this.removeNumber(column, row);
            return true;
        } else if(newNumber > 9 || newNumber < 1) {
            System.out.println("invalid number being inserted. please insert a number from 1 - 9.");
            return false;
        }

        int oldNumber = grid[column][row].getNumber();
        if(oldNumber == newNumber) {
            System.out.println("that number is already in the cell.");
            return false;
        }

        unmarkCells(grid[column][row], column, row); // unmarks other cells if they were invalid because of this cell.

        // moved into their own functions
//        if(!grid[column][row].isColumnValid()) {
//            for(int y = 0; y < 9; y++) {
//                if(y == column) {
//                    continue;
//                }
//                if(grid[y][row].getNumber() == oldNumber) {
//                    grid[y][row].setColumnValid(true);
//                }
//            }
//        }
//        if(!grid[column][row].isRowValid()) {
//            for(int x = 0; x < 9; x++) {
//                if(x == row) {
//                    continue;
//                }
//                if(grid[column][x].getNumber() == oldNumber) {
//                    grid[column][x].setRowValid(true);
//                }
//            }
//        }
//        if(!grid[column][row].isSquareValid()) {
//            int xSquare = row - row%3;
//            int ySquare = column - column%3;
//
//            for(int y = 0; y < 3; y++) {
//                int miniy = ySquare + y;
//                for(int x = 0; x < 3; x++) {
//                    int minix = xSquare + x;
//                    if(minix == row && miniy == column) {
//                        continue;
//                    } else if(grid[miniy][minix].getNumber() == oldNumber) {
//                        grid[miniy][minix].setSquareValid(true);
//                    }
//                }
//            }
//        }

        grid[column][row].setNumber(newNumber);
//        System.out.println(newNumber + " has been inserted.");
        return checkIfCellValid(column, row);
    }

    public void removeNumber(int column, int row) {
        if(grid[column][row].isImmutable()) {
            System.out.println("cell cannot be altered.");
            return;
        }
        if(grid[column][row].getNumber() == 0) {
            System.out.println("cell already empty.");
            return;
        }

        unmarkCells(grid[column][row], column, row); // unmarks other cells if they were invalid because of this cell.

//        System.out.println(grid[column][row].getNumber() + " has been removed.");
        grid[column][row] = new cell();
//        System.out.println(grid[column][row].getNumber());
    }

    private void unmarkCells(cell currentCell, int column, int row) {

        if(!currentCell.isColumnValid()) {
            int multiInvalid = 0; // if there are more than one other cell that is invalid then they would still be invalid if the currentCell number is changed.
            boolean onlyTwoInvalid = true;
            for(int y = 0; y < 9; y++) {
                if(y == column) {
                    continue;
                }
                if(!grid[y][row].isColumnValid()) {
                    multiInvalid++;
                }
                if(multiInvalid > 1) {
                    onlyTwoInvalid = false;
                    break;
                }
            }
            if(!onlyTwoInvalid) {
                return;
            }

            for(int y = 0; y < 9; y++) {
                if(y == column) {
                    continue;
                }
                if(grid[y][row].getNumber() == currentCell.getNumber()) {
                    grid[y][row].setColumnValid(true);
                }
            }
        }
        if(!currentCell.isRowValid()) {
            int multiInvalid = 0;
            boolean onlyTwoInvalid = true;
            for(int x = 0; x < 9; x++) {
                if(x == row) {
                    continue;
                }
                if(!grid[column][x].isRowValid()) {
                    multiInvalid++;
                }
                if(multiInvalid > 1) {
                    onlyTwoInvalid = false;
                    break;
                }
            }
            if(!onlyTwoInvalid) {
                return;
            }

            for(int x = 0; x < 9; x++) {
                if(x == row) {
                    continue;
                }
                if(grid[column][x].getNumber() == currentCell.getNumber()) {
                    grid[column][x].setRowValid(true);
                }
            }
        }
        if(!currentCell.isSquareValid()) {
            int xSquare = row - row%3;
            int ySquare = column - column%3;

            int multiInvalid = 0;
            boolean onlyTwoInvalid = true;
            for(int y = 0; y < 3; y++) {
                int miniy = ySquare + y;
                for(int x = 0; x < 3; x++) {
                    int minix = xSquare + x;
                    if(minix == row && miniy == column) {
                        continue;
                    } else if(!grid[miniy][minix].isSquareValid()) {
                        multiInvalid++;
                    }
                    if(multiInvalid > 1) {
                        onlyTwoInvalid = false;
                        break;
                    }
//                    if(!onlyTwoInvalid) {
//                        break;
//                    }
                }
                if(!onlyTwoInvalid) {
                    break;
                }
            }
            if(!onlyTwoInvalid) {
                return;
            }

            for(int y = 0; y < 3; y++) {
                int miniy = ySquare + y;
                for(int x = 0; x < 3; x++) {
                    int minix = xSquare + x;
                    if(minix == row && miniy == column) {
                        continue;
                    }
                    if(grid[miniy][minix].getNumber() == currentCell.getNumber()) {
                        grid[miniy][minix].setSquareValid(true);
                    }
                }
            }
        }
    }
//    to display the sudoku onto the console.
    public void displaySudoku() {
        for(int y = 0; y < grid.length; y++) {
            if(y%3 == 0 && !(y == 0)) {
                System.out.println("------|-------|------");
            }
            for(int x = 0; x < grid[0].length; x++) {
                if(x%3 == 2 && !(x == 8)) {
                    System.out.print(grid[y][x].getNumber() + " | ");
                } else {
                    System.out.print(grid[y][x].getNumber() + " ");
                }
            }
            System.out.println();
        }
    }
//    to display the sudoku but with relevant information.
    public void displaySudokuDebug(String para) {
        for (cell[] cells : grid) {
            for (int x = 0; x < grid[0].length; x++) {
                int bool;
                switch (para) {
                    case "isEmpty":
                        bool = cells[x].isEmpty() ? 1 : 0;
                        System.out.print(bool + " ");
                        break;
                    case "isColumnValid":
                        bool = cells[x].isColumnValid() ? 1 : 0;
                        System.out.print(bool + " ");
                        break;
                    case "isRowValid":
                        bool = cells[x].isRowValid() ? 1 : 0;
                        System.out.print(bool + " ");
                        break;
                    case "isSquareValid":
                        bool = cells[x].isSquareValid() ? 1 : 0;
                        System.out.print(bool + " ");
                        break;
                    case "isMarked":
                        bool = cells[x].isMarked() ? 1 : 0;
                        System.out.print(bool + " ");
                        break;
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void updatePercentOfZeros() {
        int zeroCounter = 0;
        for(cell[] column: grid) {
            for(cell currentCell: column) {
                if(currentCell.isEmpty()) {
                    zeroCounter++;
                }
            }
        }

        this.percentOfZeros = ((double) zeroCounter / 81) * 10000; // * 100 to get the whole number percent. another * 100 to prepare it for rounding to 2 decimal places.
        this.percentOfZeros = Math.round(this.percentOfZeros);
        this.percentOfZeros /= 100;
    }

    public boolean checkIfCompleted() {
        updatePercentOfZeros();

        // these were needed when this boolean had an if statement.
        //            System.out.println("sudoku completed! congratulations!");
        //            System.out.println("sudoku is only " + (100 - this.percentOfZeros) + "% complete. keep it up!");

        return this.percentOfZeros == 0 && checkIfGridValid();
    }

    public int[][] getCellValues() { // to return all the integer values as a 2D array.
        int[][] cellValues = new int[9][9];
        for(int y = 0; y < cellValues.length; y++) {
            for(int x = 0; x < cellValues[0].length; x++) {
                cellValues[y][x] = grid[y][x].getNumber();
            }
        }
        return cellValues;
    }
}

// TO-DO LIST:

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
        };
    }



    private boolean checkIfGridValid() {
        boolean valid = true;

        for(int y = 0; y < grid.length; y++ ) {
            for (int x = 0; x < grid[0].length; x++) {
                cell temp = grid[y][x]; // to reduce the time taken to find the number. resets the number's maker with the modular operation.

                if(temp.isEmpty() || temp.isMarked()) {
                    continue;
                } else {
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
        for(int y = 0; y < grid.length; y++) {
            if(y == column) {
                continue;
            }
            if(currentCell.getNumber() == grid[y][row].getNumber()) {
                System.out.println("invalid cell. number already exists in column.");
                grid[column][row].setColumnValid(false);
                grid[y][row].setColumnValid(false);
                return false;
            }
        }
        return true;
    }
    private boolean validRow(cell currentCell, int column, int row) {
        for(int x = 0; x < grid.length; x++) {
            if(x == row) {
                continue;
            }
            if(currentCell.getNumber() == grid[column][x].getNumber()) {
                System.out.println("invalid cell. number already exists in row.");
                grid[column][row].setRowValid(false);
                grid[column][x].setRowValid(false);
                return false;
            }
        }
        return true;
    }
    private boolean validSquare(cell currentCell, int row, int column) {
        int xSquare = row - row%3;
        int ySquare = column - column%3;

        for(int y = 0; y < 3; y++) {
            int miniy = ySquare + y;
            for(int x = 0; x < 3; x++) {
                int minix = xSquare + x;
//                System.out.println(miniy + " " + minix + " " + column + " " + row);
                if(minix == row && miniy == column) {
//                    System.out.println("miss me");
                    continue;
                } else if(currentCell.getNumber() == grid[miniy][minix].getNumber()) {
                    System.out.println("invalid cell. number already exists in square.");
                    grid[column][row].setSquareValid(false);
                    grid[miniy][minix].setSquareValid(false);
//                    System.out.println(grid[miniy][minix] + " " + cell);
                    return false;
                }
            }
        }

        return true;
    }

    public void insertNumber(int newNumber, int column, int row) {
        if(grid[column][row].isImmutable()) {
            System.out.println("cell cannot be altered.");
            return;
        }
        if(newNumber == 0) {
            this.removeNumber(column, row);
            return;
        } else if(newNumber > 9 || newNumber < 1) {
            System.out.println("invalid number being inserted. please insert a number from 1 - 9.");
            return;
        }

        int oldNumber = grid[column][row].getNumber();
        if(oldNumber == newNumber) {
            System.out.println("that number is already in the cell.");
            return;
        }

        unmarkCells(grid[column][row], column, row);

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
        System.out.println(newNumber + " has been inserted.");
        checkIfCellValid(column, row);
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

        unmarkCells(grid[column][row], column, row);

        grid[column][row] = new cell().getEmptyCell();
        System.out.println("number has been removed.");
    }

    private void unmarkCells(cell currentCell, int column, int row) {

        if(!currentCell.isColumnValid()) {
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

            for(int y = 0; y < 3; y++) {
                int miniy = ySquare + y;
                for(int x = 0; x < 3; x++) {
                    int minix = xSquare + x;
                    if(minix == row && miniy == column) {
                        continue;
                    } else if(grid[miniy][minix].getNumber() == currentCell.getNumber()) {
                        grid[miniy][minix].setSquareValid(true);
                    }
                }
            }
        }
    }

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
            System.out.println("");
        }
    }
    public void displaySudokuDebug(String para) {
        for(int y = 0; y < grid.length; y++) {
            for(int x = 0; x < grid[0].length; x++) {
                int bool;
                switch(para) {
                    case "isEmpty":
                        bool = grid[y][x].isEmpty()? 1 : 0;
                        System.out.print(bool + " ");
                        break;
                    case "isColumnValid":
                        bool = grid[y][x].isColumnValid()? 1 : 0;
                        System.out.print(bool + " ");
                        break;
                    case "isRowValid":
                        bool = grid[y][x].isRowValid()? 1 : 0;
                        System.out.print(bool + " ");
                        break;
                    case "isSquareValid":
                        bool = grid[y][x].isSquareValid()? 1 : 0;
                        System.out.print(bool + " ");
                        break;
                }
            }
            System.out.println("");
        }
        System.out.println("");
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

    public void checkIfCompleted() {
        updatePercentOfZeros();
        if(this.percentOfZeros == 0 && checkIfGridValid()) {
            System.out.println("sudoku completed! congratulations!");
        } else {
            System.out.println("sudoku is only " + (100 - this.percentOfZeros) + "% complete. keep it up!");
        }
    }
}

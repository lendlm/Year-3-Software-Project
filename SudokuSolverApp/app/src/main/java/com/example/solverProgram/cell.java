package com.example.solverProgram;

public class cell {

    private int number;
    private boolean isEmpty;
    private boolean columnValid;
    private boolean rowValid;
    private boolean squareValid;
    private boolean marked;
    private boolean immutable;

//    public static final cell emptyCell = new cell();

    public cell() { // creates empty cell.
        this.number = 0;
        this.isEmpty = true;
        this.columnValid = true;
        this.rowValid = true;
        this.squareValid = true;
        this.marked = false;
        this.immutable = false;
    }

    public cell(int newNumber) {
        this.number = newNumber;
        this.isEmpty = false;
        this.columnValid = true;
        this.rowValid = true;
        this.squareValid = true;
        this.marked = false;
        this.immutable = false;
    }

    public cell(int newNumber, boolean _immutable) { // creates a cell that should not be changed
        this.number = newNumber;
        this.isEmpty = false;
        this.columnValid = true;
        this.rowValid = true;
        this.squareValid = true;
        this.marked = false;
        this.immutable = _immutable;
    }

//    public cell getEmptyCell() { // resets cell into an empty one.
//        return emptyCell;
//    }

    public boolean isImmutable() {
        return immutable;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int newNumber) {
        if(newNumber < 1 || newNumber > 9) {
            System.out.println("number invalid. pls choose a number between 1-9.");
        } else {
            this.number = newNumber;
            this.setEmpty(false);
            this.setAllValid(); // resets all -Valid variables.
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }
    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isColumnValid() {
        return columnValid;
    }
    public void setColumnValid(boolean valid) {
        this.columnValid = valid;
        if(!valid) { // marks the cell if it is invalid but unmarks it if all -Valid variables are valid
            this.setMarked(true);
        } else if(this.isColumnValid() && this.isRowValid() && this.isSquareValid()) {
            this.setMarked(false);
        }
    }

    public boolean isRowValid() {
        return rowValid;
    }
    public void setRowValid(boolean valid) {
        this.rowValid = valid;
        if(!valid) {
            this.setMarked(true);
        } else if(this.isColumnValid() && this.isRowValid() && this.isSquareValid()) {
            this.setMarked(false);
        }
    }

    public boolean isSquareValid() {
        return squareValid;
    }
    public void setSquareValid(boolean valid) {
        this.squareValid = valid;
        if(!valid) {
            this.setMarked(true);
        } else if(this.isColumnValid() && this.isRowValid() && this.isSquareValid()) {
            this.setMarked(false);
        }
    }

    public void setAllValid() {
        this.setColumnValid(true);
        this.setRowValid(true);
        this.setSquareValid(true);
        this.setMarked(false);
    }
//    public void setMultiValids(boolean col, boolean row, boolean sq) {
//        this.setColumnValid(col);
//        this.setRowValid(row);
//        this.setSquareValid(sq);
//        if(col || row || sq) {
//            this.setMarked(true);
//        } else {
//            this.setMarked(false);
//        }
//    }

    public boolean isMarked() {
        return marked;
    }
    private void setMarked(boolean marked) {
        this.marked = marked;
    }
}

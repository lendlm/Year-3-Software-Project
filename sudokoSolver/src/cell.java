public class cell {

    private int number;
    private boolean isEmpty;
    private boolean columnValid;
    private boolean rowValid;
    private boolean squareValid;
    private boolean marked;

    public static final cell emptyCell = new cell();

    public cell() { // creates empty cell.
        this.number = 0;
        this.isEmpty = true;
        this.columnValid = true;
        this.rowValid = true;
        this.squareValid = true;
        this.marked = false;
    }

    public cell(int newNumber) {
        this.number = newNumber;
        this.isEmpty = false;
        this.columnValid = true;
        this.rowValid = true;
        this.squareValid = true;
        this.marked = false;
    }

    public cell getEmptyCell() { // resets cell into an empty one.
        return emptyCell;
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
            this.setAllValid();
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
        if(valid == false) {
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
        if(valid == false) {
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
        if(valid == false) {
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
    public void setMultiValids(boolean col, boolean row, boolean sq) {
        this.setColumnValid(col);
        this.setRowValid(row);
        this.setSquareValid(sq);
        if(col == true || row == true || sq == true) {
            this.setMarked(true);
        } else {
            this.setMarked(false);
        }
    }

    public boolean isMarked() {
        return marked;
    }
    private void setMarked(boolean marked) {
        this.marked = marked;
    }
}

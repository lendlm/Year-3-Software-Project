package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solverProgram.solver;
import com.example.solverProgram.sudoku;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    initialising all the Views.
    private TextView[] cells = new TextView[81];
    private TextView[] numberButtons = new TextView[9];
    private TextView clearButton;
    private TextView clearAllButton;
    private Button solveButton;

//    when a view button has been pressed, the booleans will keep track of it and the TextViews will store which view was pressed.
    private boolean cellChosen = false;
    private boolean numberChosen = false;
    private boolean clearChosen = false;
    private int cellId;
    private int numberId;
    private TextView cellView;
    private TextView numView;

//    sudoku program related variables.
    private int[][] userInput = new int[9][9];
    private sudoku OGPuzzle;
    private sudoku solvedPuzzle;
    private solver sudokuSolver;
    private int[][] completedBoard = new int[9][9];

//    default state of the board.
    int[][] defaultBoard = {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setting click listener for cells.
        int x = 0;
        int y = 0;
        for(int i = 0; i < cells.length; i++) {
            String cellPos = "x";
            cellPos += (char) (x + '0');
            cellPos += 'y';
            cellPos += (char) (y + '0');
//            System.out.println(cellPos);
            int id = getResources().getIdentifier(cellPos, "id", getPackageName()); // gets int id for the corresponding cell position.
            cells[i] = (TextView) findViewById(id);
            if(defaultBoard[y][x] != 0) {
                cells[i].setText(String.valueOf(defaultBoard[y][x]));
            }
            cells[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cellView = (TextView) view;
//                    String toastText = cellView.getResources().getResourceEntryName(view.getId());
//                    toastText += " chosen";
//                    Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
                    cellChosen = true;
                    cellId = cellView.getId();
                    changeCellNumber();
                }
            });

//            increments through the 9x9 grid
            x++;
            if(x > 8) {
                x = 0;
                y++;
            }
        }

//        setting click listeners for number buttons.
        for(int i = 0; i < numberButtons.length; i++) {
            String numButton = "choose_";
            numButton += (char) (i + '1');
            int id = getResources().getIdentifier(numButton, "id", getPackageName()); // gets int id for the corresponding number button.
            numberButtons[i] = (TextView) findViewById(id);
            numberButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(numView != null) {
                        numView.setBackground(getResources().getDrawable(R.drawable.button));
                    }
                    if(clearChosen) {
                        clearButton.setBackground(getResources().getDrawable(R.drawable.button2));
                        clearButton.setTextColor(getResources().getColor(R.color.text_color2));
                    }
                    numView= (TextView) view;
//                    String toastText = numView.getText().toString();
//                    toastText += " chosen.";
//                    Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
                    numberChosen = true;
                    numView.setBackground(getResources().getDrawable(R.drawable.button_highlight));
                    clearChosen = false;
                    numberId = numView.getId();
                    changeCellNumber();
                }
            });
        }

//        setting click listener for clear button.
        clearButton = (TextView) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "clearing cell", Toast.LENGTH_SHORT).show();
                if(numberChosen) {
                    numView.setBackground(getResources().getDrawable(R.drawable.button));
                }
                numberChosen = false;
                clearChosen = true;
                clearButton.setBackground(getResources().getDrawable(R.drawable.button_highlight));
                clearButton.setTextColor(getResources().getColor(R.color.text_color1));
                changeCellNumber();
            }
        });

        clearAllButton = (TextView) findViewById(R.id.clear_all_button);
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearChosen = false;
                clearButton.setBackground(getResources().getDrawable(R.drawable.button2));
                clearButton.setTextColor(getResources().getColor(R.color.text_color2));
                for(int i = 0; i < cells.length; i++) {
                    if(!(cells[i].getText() == getString(R.string.cell))) {
                        cells[i].setText(getString(R.string.cell));
                    }
                }
            }
        });

//        setting click listener for solve button.
        solveButton = (Button) findViewById(R.id.solve_button);
        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = 0;
                int y = 0;
                for(int i = 0; i < cells.length; i++) {

                    if(cells[i].getText() != getString(R.string.cell)) {
                        userInput[y][x] = Integer.parseInt(cells[i].getText().toString());
                    } else {
                        userInput[y][x] = 0;
                    }

//                    increments through the 9x9 grid.
                    x++;
                    if(x > 8) {
                        x = 0;
                        y++;
                    }
                }
                OGPuzzle = new sudoku(userInput);
                sudokuSolver = new solver(OGPuzzle);
                if(sudokuSolver.solvePuzzle()) {
                    solvedPuzzle = sudokuSolver.getSolvedPuzzle();
//                    Toast.makeText(getApplicationContext(), "puzzle is solvable!", Toast.LENGTH_LONG).show();

                    completedBoard = solvedPuzzle.getCellValues(); // gets cell values of the completed board onto an int[][] array.

                    Intent puzzleInfo = new Intent("com.example.SolvingActivity");
//                    serialises 2d arrays so I can bundle them into intent puzzleInfo.
                    Bundle userInputBundle = new Bundle();
                    userInputBundle.putSerializable("user_input", userInput);
                    Bundle completedBundle = new Bundle();
                    completedBundle.putSerializable("solved_puzzle", completedBoard);

                    puzzleInfo.putExtras(userInputBundle); // stores info of the user's input for initial set-up.
                    puzzleInfo.putExtras(completedBundle); // stores info of the solved puzzle for hint access.
                    startActivity(puzzleInfo); // moves into the solving activity screen.
                } else {
                    Toast.makeText(getApplicationContext(), "puzzle is unsolvable. please remove some numbers you inputted and try again.", Toast.LENGTH_LONG).show();
                }
            }
        });

//        Toast.makeText(this, "this is a example sudoku. clear all to begin inputting your own puzzle.", Toast.LENGTH_LONG).show();
    }

    private void changeCellNumber() {
//        once a cell block and a number has been chosen, it will insert the number into the cell and any other cells chosen afterwards.
        if(numberChosen && cellChosen) {
            cellView.setText(numView.getText());
            cellChosen = false; // this is to make sure when changing numbers, the cell just altered does not change as well.
//            System.out.println("number and cell chosen");
        } else if(clearChosen && cellChosen) {
            cellView.setText(getString(R.string.cell));
            cellChosen = false;
        }
    }

    public void onClick(View view) {

    }

    public void goToHowTo(View view) {
        Intent howTo = new Intent("com.example.HowToActivity");
        startActivity(howTo);
    }
}
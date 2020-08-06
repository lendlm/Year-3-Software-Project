package com.example.sudokusolver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Random;

public class SolvingActivity extends AppCompatActivity implements View.OnClickListener {

//    variables to store array data from previous activity.
    private int[][] userInput;
    private int[][] completedBoard;

//    initialising cell views.
    private TextView[] cells = new TextView[81];
    private TextView cellView;
    private boolean cellChosen = false;
    private boolean solveOneChosen = false;
    private int cellId;
    private int emptyCells = 81;

    private TextView solveOneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solver);
        Intent puzzleInfo = getIntent();
        Object[] userInputArray = (Object[]) puzzleInfo.getExtras().getSerializable("user_input");
        Object[] completedBoardArray = (Object[]) puzzleInfo.getExtras().getSerializable("solved_puzzle");
        if(userInputArray != null && completedBoardArray != null) {
            userInput = new int[userInputArray.length][];
            completedBoard = new int[completedBoardArray.length][];
            for(int i = 0; i < userInputArray.length; i++) {
                userInput[i] = (int[]) userInputArray[i];
                completedBoard[i] = (int[]) completedBoardArray[i];
            }
        }

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
            if(userInput[y][x] != 0) {
                cells[i].setText(String.valueOf(userInput[y][x]));
                emptyCells--;
            }
            cells[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cellView = (TextView) view;
                    cellChosen = true;
                    cellId = cellView.getId();
                    if(solveOneChosen) {
                        if(cellView.getText() != getString(R.string.cell)) {
                            Toast.makeText(SolvingActivity.this, "cell already solved", Toast.LENGTH_SHORT).show();
                        } else {
                            solveOneCell(cellView, Arrays.asList(cells).indexOf(cellView)); // finds position of the cell on the board.
                        }
                    }
                }
            });

//            increments through the 9x9 grid
            x++;
            if(x > 8) {
                x = 0;
                y++;
            }
        }

        solveOneButton = (TextView) findViewById(R.id.solve_one);
    }

    public void onSolveOneClick(View view) {
        if(emptyCells == 0) { // if the board is complete then it will not need to solve anymore.
            Toast.makeText(this, "no more cells to solve.", Toast.LENGTH_SHORT).show();
            return;
        }
        solveOneChosen = true;
        solveOneButton.setBackground(getResources().getDrawable(R.drawable.button_highlight));
        solveOneButton.setTextColor(getResources().getColor(R.color.text_color1));
    }
    private void solveOneCell(TextView view, int n) {
        int y = n / 9; // gets the y position of the cell.
        int x = n % 9; // gets the x position of the cell.
        view.setText(String.valueOf(completedBoard[y][x]));
        view.setBackground(getResources().getDrawable(R.drawable.cell_highlight));
        emptyCells--;
    }

    public void onSolveSomeClick(View view) {
        solveOneChosen = false;
        solveOneButton.setBackground(getResources().getDrawable(R.drawable.button2));
        solveOneButton.setTextColor(getResources().getColor(R.color.text_color2));
        if(emptyCells == 0) { // if the board is complete then it will not need to solve anymore.
            Toast.makeText(this, "no more cells to solve.", Toast.LENGTH_SHORT).show();
            return;
        }
        Random rand = new Random();
        int randomNumber;
        if(emptyCells < 5) {
            randomNumber = rand.nextInt(emptyCells) + 1;
        } else {
            randomNumber = rand.nextInt(3) + 2; // gets a random number between 3 and 5.
        }

        for(int i = 0; i < randomNumber; i++) {
            int randomCell = rand.nextInt(81);
            while(cells[randomCell].getText() != getString(R.string.cell)) { // checks if the cell chosen is empty. if it isn't then it will choose a different one.
                randomCell = rand.nextInt(81);
            }
            int y = randomCell / 9; // gets the y position of the random cell.
            int x = randomCell % 9; // gets the x position of the random cell.
            cells[randomCell].setText(String.valueOf(completedBoard[y][x]));
            cells[randomCell].setBackground(getResources().getDrawable(R.drawable.cell_highlight));
            emptyCells--;
        }
    }

    public void onSolveAllClick(View view) {
        solveOneChosen = false;
        solveOneButton.setBackground(getResources().getDrawable(R.drawable.button2));
        solveOneButton.setTextColor(getResources().getColor(R.color.text_color2));
        if(emptyCells == 0) { // if the board is complete then it will not need to solve anymore.
            Toast.makeText(this, "no more cells to solve.", Toast.LENGTH_SHORT).show();
            return;
        }
        int x = 0;
        int y = 0;
        for(int i = 0; i < cells.length; i++) {
            if(cells[i].getText() == getString(R.string.cell)) { // if the cell is empty then it will reveal it.
                cells[i].setText(String.valueOf(completedBoard[y][x]));
                cells[i].setBackground(getResources().getDrawable(R.drawable.cell_highlight));
                emptyCells--;
            }

//            increments through the 9x9 grid
            x++;
            if(x > 8) {
                x = 0;
                y++;
            }
        }
    }

    public void onClick(View view) {

    }

    public void goBack(View view) { // goes back to previous activity (MainActivity).
        finish();
    }
}

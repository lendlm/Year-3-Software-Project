package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView[] cells = new TextView[81];
    TextView[] numberButtons = new TextView[9];
    TextView clearButton;
    TextView clearAllButton;

    boolean cellChosen = false;
    boolean numberChosen = false;
    boolean clearChosen = false;
    int cellId;
    int numberId;
    TextView cellView;
    TextView numView;

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
            cells[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cellView = (TextView) view;
                    String toastText = cellView.getResources().getResourceEntryName(view.getId());
                    toastText += " chosen";
                    Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
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
                    numView= (TextView) view;
                    String toastText = numView.getText().toString();
                    toastText += " chosen.";
                    Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
                    numberChosen = true;
                    clearChosen = false;
                    numberId = numView.getId();
                    changeCellNumber();
                }
            });
        }

//        setting click listener for clear button
        clearButton = (TextView) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "clearing cell", Toast.LENGTH_SHORT).show();
                numberChosen = false;
                clearChosen = true;
                changeCellNumber();
            }
        });

        clearAllButton = (TextView) findViewById(R.id.clear_all_button);
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < cells.length; i++) {
                    if(!(cells[i].getText() == getString(R.string.cell))) {
                        cells[i].setText(getString(R.string.cell));
                    }
                }
            }
        });

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
            clearChosen = false; // this is to stop from accidentally clearing multiple cells.
        }
    }

    public void onClick(View view) {

    }

    public void onClickNumber(View view) {

    }
}
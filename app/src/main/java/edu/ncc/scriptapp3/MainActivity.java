package edu.ncc.scriptapp3;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {

    private String script[];
    private String optionSet[][];
    private int story;
    private int adventure; // Decides which script to use
    private Scanner s;
    private int numStories;
    private Button btns[];
    private Button menuBtns[];
    private TextView tView;
    private TableLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btns = new Button [3];
        btns[0] = (Button) findViewById (R.id.button1);
        btns[1] = (Button) findViewById (R.id.button2);
        btns[2] = (Button) findViewById (R.id.button3);
        for (int i = 0 ; i < btns.length ; i++)
            btns[i].setVisibility(View.GONE);

        menuBtns = new Button [4];
        menuBtns[0] = (Button) findViewById (R.id.Adv1);
        menuBtns[1] = (Button) findViewById (R.id.Adv2);
        menuBtns[2] = (Button) findViewById (R.id.Adv3);
        menuBtns[3] = (Button) findViewById (R.id.Adv4);
        // REMOVE THIS FOR LOOP WHEN WE HAVE 4 STORIES
        for (int i = 2 ; i < menuBtns.length ; i++)
            menuBtns[i].setClickable(false);

        tView = (TextView) findViewById (R.id.textView1);
        layout = (TableLayout) findViewById (R.id.tableLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startAdventure (View v) {

        InputStream i1, i2;
        i1 = this.getResources().openRawResource(R.raw.script_g);
        i2 = this.getResources().openRawResource(R.raw.script_g);
        adventure = 0;
        layout.setBackgroundColor(Color.RED);

        if (v.getId() == R.id.Adv2) {
            i1 = this.getResources().openRawResource(R.raw.script_t);
            i2 = this.getResources().openRawResource(R.raw.script_t);
            adventure = 1;
            layout.setBackgroundColor(Color.BLUE);
        }
        else if (v.getId() == R.id.Adv3) {
            ;// Insert inputStream statement here when 3rd adventure is written
        }
        else if (v.getId() == R.id.Adv4) {
            ;// Insert inputStream statement here when 4th adventure is written
        }

        s = new Scanner ( i1 );

        numStories = 0;
        while (s.hasNextLine()) {
            if (s.next().charAt(0) == '*')
                numStories++;
            s.nextLine();
        }
        s.close();

        s = new Scanner ( i2 );

        script = new String [ numStories ];
        optionSet = new String [ numStories ] [3];
        // NOTE: The "3" is meant to be the maximum number of possible options for the user at any
        //       point in the adventure

        while ( s.hasNextLine() ) {
            String temp = s.nextLine();
            if (temp.substring(0 , 1).charAt(0) == '*')
                script[ Integer.parseInt( temp.substring(2 , 4)) ] = temp.substring(5);
            else if (temp.substring(0 , 1).charAt(0) == '#')
                optionSet[ Integer.parseInt( temp.substring(2 , 4)) ]
                         [ Integer.parseInt( temp.substring(5 , 7)) ] = temp.substring(11);
        }
        s.close();

        for (int i = 0 ; i < menuBtns.length ; i++) {
            menuBtns[i].setVisibility(View.GONE);
        }

        for (int i = 0 ; i < btns.length ; i++) {
            if (optionSet[0][i] != null) {
                btns[i].setVisibility(View.VISIBLE);
                btns[i].setText(optionSet[0][i]);
            }
            else
                btns[i].setVisibility(View.GONE);
        }

        tView.setText(script[0]);

        story = 0;
    }

    public void firstOption (View v) {
        advanceStory(adventure , 0);
    }

    public void secondOption (View v) {
        advanceStory(adventure , 1);
    }

    public void thirdOption (View v) {
        advanceStory(adventure , 2);
    }

    public void advanceStory( int adv , int choice ) {
        if (adv == 0) { // Adventure #1's progression
            switch (story) {
                case 0:
                    if (choice == 0)
                        story = 1;
                    else if (choice == 1)
                        story = 2;
                    else if (choice == 2)
                        story = 3;
                    break;
                case 1: // End of story
                    story = -1;
                    break;
                case 2: // End of story
                    story = -1;
                    break;
                case 3: // End of story
                    story = -1;
                    break;
            }
        }
        else if (adv == 1) { // Adventure #2's progression
            switch (story) {
                case 0:
                    if (choice == 0)
                        story = 1;
                    else if (choice == 1)
                        story = 2;
                    break;
                case 1:
                    if (choice == 0)
                        story = 3;
                    else if (choice == 1)
                        story = 4;
                    break;
                case 2:
                    if (choice == 0)
                        story = 5;
                    else if (choice == 1)
                        story = 6;
                    break;
                case 3:
                    story = -1;
                    break;
                case 4:
                    story = -1;
                    break;
                case 5:
                    story = -1;
                    break;
                case 6:
                    story = -1;
                    break;
            }
        }

        // Now we need to adjust TextView and Buttons

        if (story == -1)
            backToMainMenu();
        else {
            tView.setText(script[story]);
            for (int i = 0; i < btns.length; i++) {
                if (optionSet[story][0] == null) {
                    btns[0].setVisibility(View.VISIBLE);
                    btns[0].setText("Back to Main Menu");
                    btns[1].setVisibility(View.GONE);
                    btns[2].setVisibility(View.GONE);
                } else if (optionSet[story][i] != null) {
                    btns[i].setVisibility(View.VISIBLE);
                    btns[i].setText(optionSet[story][i]);
                } else
                    btns[i].setVisibility(View.GONE);
            }
        }
    }

    public void backToMainMenu() {
        for (int i = 0 ; i < btns.length ; i++)
            btns[i].setVisibility(View.GONE);

        for (int i = 0 ; i < menuBtns.length ; i++)
            menuBtns[i].setVisibility(View.VISIBLE);

        tView.setText("Choose An Adventure!");
        layout.setBackgroundColor(Color.TRANSPARENT);
    }
    
    // My documentation skills are terrible :c
}

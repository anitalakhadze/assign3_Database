package com.ani.examples;

import javax.swing.*;
import java.awt.*;

public class MyStupidComponent extends Component {
    private int score;
    private Label myScoreCounter;

    //
    void updateMyScore(int score) {
        this.score = score;
        int myStupidVar = 10;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(myStupidVar);
                myScoreCounter.setText(Integer.toString(score));
            }
        });
    }
    //
}

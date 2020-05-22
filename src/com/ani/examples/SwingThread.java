package com.ani.examples;

import javax.swing.*;
import java.awt.*;

public class SwingThread extends JFrame {
    private JLabel label;
    private JButton right;
    private JButton wrong;
    private JButton fork;
    private JTextField field;
    private LabelWorker worker;
    private JButton inter;

    public SwingThread() {
        super("Swing Thread");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        label = new JLabel("Hello");
        add(label);

        //Button that adds an "x" when clicked -- fine,
        //runs on the swing thread
        right = new JButton("Add Right x");
        add(right);
        right.addActionListener(e -> label.setText(label.getText() + " x"));

        // Bad -> hogs the Swing thread.
        // the whole GUI will appear to lock up for 5 seconds -- use
        // worker thread instead.
        // note also that the "All homework is cancelled" NEVER appears on screen.
        wrong = new JButton("Add Wrong y");
        add(wrong);
        wrong.addActionListener(actionEvent -> {
            String text = label.getText();
            label.setText("All homework is cancelled!");
            //sleep for 5 seconds -- simulate time consuming operation
            try { Thread.sleep(5000); }
            catch (InterruptedException ignored) {}
            label.setText(text + " y");
        });

        // Field appends text to label -- uses worker thread correctly
        field = new JTextField("hi", 20);
        field.setMaximumSize(new Dimension(200, 20));
        add(field);

        worker = null;
        fork = new JButton("Fork off Adder");
        add(fork);

        // fork button -> set text, then fork off worker
        fork.addActionListener(actionEvent -> {
            // set text right away -- ok, we're on the swing thread
            label.setText(label.getText() + " ... WAIT FOR IT!");

            //interrupt previous worker if it exists
            if (worker != null) worker.interrupt();

            //for off new worker using text from field
            worker = new LabelWorker(field.getText());
            worker.start();
        });

        // Interrupt existing worker
        inter = new JButton("Interrupt");
        add(inter);
        inter.addActionListener(actionEvent -> {
            // Q: is there a race condition between this code
            // and the above worker interrupt code?
            if (worker != null) {
                worker.interrupt();
                worker = null;
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    // Takes a work, appends it 10 times to the label,
    // working very slowly. Exits when interrupted
    private class LabelWorker extends Thread {
        String word;

        public LabelWorker(String initWord) {
            word = initWord;
        }

        public void run(){
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                try { Thread.sleep(100); }
                // way 1 to notice interrupted
                catch (InterruptedException ex) { break; }

                text.append(word);
                final String finalText = text.toString();

                //way 2 to notice interruption
                if (isInterrupted()) break;

                // NONONO, cannot do this
                // label.setText(finalText);

                // message back to the GUI using invokeLater/Runnable
                SwingUtilities.invokeLater(() -> label.setText(finalText));

            }
        }
    }

    public static void main (String[] args) {
        new SwingThread();
    }
}

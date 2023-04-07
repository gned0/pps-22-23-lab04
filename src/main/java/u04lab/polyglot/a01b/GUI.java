package u04lab.polyglot.a01b;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import u04lab.polyglot.Pair;
public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Logics logics;

    public GUI(int size, int mines) {
        this.logics = new LogicsImpl(size,mines);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);

        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);

        ActionListener al = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> p = buttons.get(bt);
            //System.out.println("hit "+p);
            Optional<Integer> result = logics.hit(p.getX(), p.getY());
            if (result.isPresent() && !logics.won()) {
                this.drawboard();
            } else {
                System.out.println(logics.won() ? "WON" : "LOST");
                System.exit(0);
            }
        };

        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(al);
                this.buttons.put(jb,new Pair<>(j,i));
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }
    private void drawBoard() {
        for (var entry: this.buttons.entrySet()) {
            // call the logic here
            // if this button is a cell with counter, put the number
            // if this button has a flag, put the flag
            if(logics.getFlags().contains(entry.getValue())) {
                String string = "F";
                entry.getKey().setText(string);
            }
            if(logics.getCounters().contains(entry.getValue())) {
                String string = Integer.valueOf(this.logics.computeAdjacentMines(entry.getValue()).size()).toString();
                entry.getKey().setText(string);
                entry.getKey().setEnabled(false);
            }
        }
    }

    private void drawBoard() {
        for (var entry: this.buttons.entrySet()) {
            if(logics.getCounters().contains(entry.getValue())) {
                String string = Integer.valueOf(this.logics.computeAdjacentMines(entry.getValue()).size()).toString();
                entry.getKey().setText(string);
                entry.getKey().setEnabled(false);
            }
        }
    }

}


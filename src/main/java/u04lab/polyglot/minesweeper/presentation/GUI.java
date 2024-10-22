package u04lab.polyglot.minesweeper.presentation;

import e2.logic.Logics;
import e2.logic.LogicsImpl;
import e2.structures.Pair;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import java.io.Serial;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    
    @Serial
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size, int numberOfMines) {
        List<Pair<Integer, Integer>> minesPoisitions = new ArrayList<>();
        minesPoisitions.add(new Pair<>(0, 0));
        minesPoisitions.add(new Pair<>(1, 1));
        minesPoisitions.add(new Pair<>(2, 2));
        this.logics = new LogicsImpl(size, 5);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener onClick = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> pos = buttons.get(bt);
            boolean aMineWasFound = logics.mineHit(pos); // call the logic here to tell it that cell at 'pos' has been selected
            if (aMineWasFound) {
                quitGame();
                JOptionPane.showMessageDialog(this, "Game over");
                System.exit(0);
            } else {
                this.logics.addCounters(pos);
                drawBoard();
            }
            boolean isThereVictory = this.logics.isGameWon(); // call the logic here to ask if there is victory
            if (isThereVictory){
                quitGame();
                JOptionPane.showMessageDialog(this, "You won!!");
                System.exit(0);
            }
        };

        MouseInputListener onRightClick = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JButton bt = (JButton)e.getSource();
                if (bt.isEnabled()){
                    final Pair<Integer,Integer> pos = buttons.get(bt);
                    logics.addFlag(pos);
                }
                drawBoard();
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(onClick);
                jb.addMouseListener(onRightClick);
                this.buttons.put(jb,new Pair<>(i,j));
                panel.add(jb);
            }
        }
        this.drawBoard();
        this.setVisible(true);
    }
    
    private void quitGame() {
        this.drawBoard();
    	for (var entry: this.buttons.entrySet()) {
            // call the logic here
            // if this button is a mine, draw it "*"
            // disable the button
            if(logics.getMines().contains(entry.getValue())) {
                String string = "*";
                entry.getKey().setText(string);
                entry.getKey().setEnabled(false);
            }
    	}
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
    
}

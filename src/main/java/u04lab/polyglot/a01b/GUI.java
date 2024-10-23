package u04lab.polyglot.a01b;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import u04lab.polyglot.Pair;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> buttons = new HashMap<>();
    private final Set<Pair<Integer, Integer>> flaggedCells = new HashSet<>();
    private final Logics logics;

    public GUI(int size, int mines) {
        this.logics = new LogicsImpl(size, mines);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(BorderLayout.CENTER, panel);

        ActionListener al = (e) -> {
            final JButton bt = (JButton) e.getSource();
            final Pair<Integer, Integer> p = buttons.get(bt);
            if (!flaggedCells.contains(p)) {
                Optional<Integer> result = logics.discover(p.getX(), p.getY());
                if (result.isPresent() && !logics.won()) {
                    this.updateBoard();
                } else {
                    System.out.println(logics.won() ? "WON" : "LOST");
                    System.exit(0);
                }
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int row = i; // Declare a final variable for the row index
                final int col = j; // Declare a final variable for the column index
                final JButton jb = new JButton(" ");

                jb.addActionListener(al);
                jb.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            toggleFlag(jb, new Pair<>(col, row)); // Use the final variables
                        }
                    }
                });

                this.buttons.put(jb, new Pair<>(col, row)); // Use the final variables
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void toggleFlag(JButton button, Pair<Integer, Integer> position) {
        if (flaggedCells.contains(position)) {
            flaggedCells.remove(position);
            button.setText(" ");
        } else {
            flaggedCells.add(position);
            button.setText("F");
        }
    }

    private void updateBoard() {
        buttons.forEach((b, p) -> {
            Integer state = logics.getCellState(p.getX(), p.getY());
            if (state != -2) {
                b.setText(state.toString());
                b.setEnabled(false);
            }
        });
    }
}

package reversi_ce160353_phamthanhnghiem;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author CE160353 PhamThanhNghiem
 */
public class Card extends JLabel {

    int row, col, value;
    private MouseListener mouseClicked;
    private Reversi parent;

    public Card(Reversi parent, int row, int col, int value) {
        this.parent = parent;
        this.row = row;
        this.col = col;
        this.value = value;
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        this.mouseClicked = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardClicked();
            }
        };
        this.addMouseListener(mouseClicked);

        updateFace();
    }

    private void cardClicked() {
        int count = parent.countAllDirection(row, col);
        if (count > 0) {
            turnOn(parent.getLuotDi());
            parent.luotDiTiepTheo();
            this.removeMouseListener(mouseClicked);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            parent.updatePoint();
        }

    }

    public void updateFace() {
        this.setIcon(getFace());
    }

    private ImageIcon getFace() {
        return new ImageIcon(getClass().getResource("/img/" + value + ".png"));
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        updateFace();
    }

    public void turnOn(int luotDi) {
        this.value = value * Reversi.turnOnValue + luotDi;
        updateFace();
    }

    public void reTurn() {
        int bg = this.value / Reversi.turnOnValue;
        int v1 = this.value % Reversi.turnOnValue;
        v1 = 1 - v1;
        this.value = bg * Reversi.turnOnValue + v1;
        updateFace();
    }

}

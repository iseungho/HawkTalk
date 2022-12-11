package DrawBoard;

import java.awt.*;
import javax.swing.*;

public class Brush extends JLabel {
    public int x, y, size = 10;
    private Color color = Color.BLACK;
    private boolean clear = false;

    @Override
    public synchronized void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x - 1 - size, y - 1 - size, 1 + size, 1 + size);
        if (!clear) {
            g.setColor(color);
            g.fillOval(x - 1 - size, y - 1 - size, 1 + size, 1 + size);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 1000, 1000);
            clear = false;
        }

    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }

    public synchronized void setSize(int size) {
        this.size = size;
    }

    public synchronized void setColor(Color color) {
        this.color = color;
    }

    public synchronized void setClear(boolean clear) {
        this.clear = clear;
    }

    public synchronized Color getColor() {
        return this.color;
    }
}
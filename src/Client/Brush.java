package Client;

import java.awt.*;
import javax.swing.*;

public class Brush extends JLabel {
    public int x, y;
    public Color color = Color.RED;
    public boolean clear = true;

    @Override
    public synchronized void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(x - 10, y - 10, 10, 10);
        if (clear) {
            g.setColor(color);
            g.fillOval(x - 10, y - 10, 10, 10);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 1000, 1000);
            clear = true;
        }

    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }

    public synchronized void setColor(Color color) {
        this.color = color;
    }

    public synchronized void setClear(boolean clear) {
        this.clear = clear;
    }
}
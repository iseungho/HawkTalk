package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherLayout extends JFrame {
    private JButton CloseButton;
    private JTextArea WeatherArea;
    private JLabel WeatherIcon;
    private JPanel WeatherLayoutPanel;

    public WeatherLayout() {
        setContentPane(WeatherLayoutPanel);
        setSize(300,400);
        setTitle("오늘의 날씨");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        Crawling crawling = new Crawling();
        WeatherArea.append(crawling.getWeatherInfo());
        CloseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}

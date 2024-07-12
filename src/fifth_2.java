import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fifth_2 extends JFrame {
    private JTextField[] textFields;
    private JButton drawButton;
    private JLabel statusLabel;
    private JPanel drawingPanel;

    public fifth_2() {
        // Настройка основного окна
        setTitle("Draw Triangle");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Инициализация компонентов
        textFields = new JTextField[6];
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new JTextField(5);
        }

        drawButton = new JButton("Draw Triangle");
        statusLabel = new JLabel("Enter coordinates and click 'Draw Triangle'");
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int[] points = getPoints();
                if (points != null) {
                    // Рисование треугольника с линиями разных цветов
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(points[0], points[1], points[2], points[3]);
                    g2d.setColor(Color.RED);
                    g2d.drawLine(points[2], points[3], points[4], points[5]);
                    g2d.setColor(Color.WHITE);
                    g2d.drawLine(points[4], points[5], points[0], points[1]);
                }
            }
        };

        // Размещение компонентов на форме
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        for (int i = 0; i < textFields.length; i++) {
            JPanel rowPanel = new JPanel();
            rowPanel.add(new JLabel("Point " + (i / 2 + 1) + " (" + (i % 2 == 0 ? "X" : "Y") + "):"));
            rowPanel.add(textFields[i]);
            inputPanel.add(rowPanel);
        }
        inputPanel.add(drawButton);
        inputPanel.add(statusLabel);

        // Добавление панелей на форму
        add(inputPanel, BorderLayout.EAST);
        add(drawingPanel, BorderLayout.CENTER);

        // Добавление слушателя кнопки
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.repaint();
            }
        });
    }

    // Метод для получения координат точек из текстовых полей
    private int[] getPoints() {
        int[] points = new int[6];
        for (int i = 0; i < textFields.length; i++) {
            try {
                points[i] = Integer.parseInt(textFields[i].getText());
            } catch (NumberFormatException e) {
                statusLabel.setText("Please enter valid coordinates.");
                return null;
            }
        }
        statusLabel.setText("Triangle drawn.");
        return points;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new fifth_2().setVisible(true);
            }
        });
    }
}
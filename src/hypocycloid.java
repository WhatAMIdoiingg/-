import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class hypocycloid extends JFrame implements ActionListener {
    private int cX = 150, cY = 150; // центр большого круга
    private int R2 = 90; // радиус большого круга
    private int k = 20; // количество областей на траектории
    private int R1 = R2 / k; // радиус меньшего (движущегося) круга
    private double initT = 0, lastT = 6.3; // полный оборот в 360 градусов (6.28 радиан)
    private double step = 0.1, angle = initT;
    private Point[] p = new Point[64]; // точки для рисования (lastT / step)
    private int i = 0; // количество точек для рисования
    private Timer timer;

    private int speed = 40;
    private boolean drawingComplete = false;
    private Color shapeColor = Color.RED;
    private JPanel drawingPanel;
    private JTextField radiusField;
    private JTextField kField;
    private JTextField speedField;
    private JComboBox<String> colorComboBox;

    public hypocycloid() {
        setTitle("Circle Drawer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                if (!drawingComplete) {
                    if (angle <= lastT) {
                        double x = R1 * (k - 1) * (Math.cos(angle) + Math.cos((k - 1) * angle) / (k - 1));
                        double y = R1 * (k - 1) * (Math.sin(angle) - Math.sin((k - 1) * angle) / (k - 1));
                        p[i] = new Point(cX + (int) x, cY + (int) y); // вычисляем следующую точку на траектории
                        double x1 = (R2 - R1) * Math.sin(angle + 1.57);
                        double y1 = (R2 - R1) * Math.cos(angle + 1.57);
                        paintCircle(g2d, cX, cY, (int) x1, (int) y1, R1, x, y);
                        paintGraphic(g2d, p);

                        angle += step;
                        i++;
                    } else {
                        drawingComplete = true;
                        timer.stop();
                    }
                }
                paintGraphic(g2d, p); // перерисовываем полную траекторию
                drawOuterCircle(g2d); // рисуем внешнюю окружность

                // Рисуем линии
                if(!drawingComplete){
                    g2d.setColor(Color.BLUE);
                    g2d.drawLine(cX, cY, p[i - 1].x, p[i - 1].y); // Линия от центра большой окружности к центру движущейся
                    g2d.drawLine(0, 0, p[i - 1].x, p[i - 1].y); // Линия из левого угла окна к центру движущейся окружности
                }}
        };
        drawingPanel.setBackground(Color.WHITE);
        add(drawingPanel, BorderLayout.CENTER);

        // Размещение компонентов на форме
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JPanel radiusPanel = new JPanel();
        radiusField = new JTextField(String.valueOf(R2), 10);
        radiusPanel.add(new JLabel("Radius:"));
        radiusPanel.add(radiusField);
        inputPanel.add(radiusPanel);

        JPanel kPanel = new JPanel();
        kField = new JTextField(String.valueOf(k), 10);
        kPanel.add(new JLabel("K:"));
        kPanel.add(kField);
        inputPanel.add(kPanel);
        JPanel speedP = new JPanel();

        speedField = new JTextField(String.valueOf(speed), 10);
        speedP.add(new JLabel("Speed:"));
        speedP.add(speedField);
        inputPanel.add(speedP);

        JPanel colorPanel = new JPanel();
        String[] colors = {"Red", "Blue", "Green", "Yellow", "Orange", "Pink"};
        colorComboBox = new JComboBox<>(colors);
        colorComboBox.setSelectedIndex(0);
        colorPanel.add(new JLabel("Color:"));
        colorPanel.add(colorComboBox);
        inputPanel.add(colorPanel);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(this);
        inputPanel.add(refreshButton);



        // Добавление панелей на форму
        add(inputPanel, BorderLayout.EAST);

        timer = new Timer(speed, this); // таймер для перерисовки каждые 40 мс
        timer.start();
    }

    private void paintGraphic(Graphics2D g, Point[] p) {
        g.setColor(shapeColor);
        for (int j = 1; j < i; j++) {
            g.drawLine(p[j - 1].x, p[j - 1].y, p[j].x, p[j].y);
        }
    }

    private void drawOuterCircle(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawOval(cX - R2, cY - R2, 2 * R2, 2 * R2);
    }

    private void drawMovingCircle(Graphics2D g, double angle) {
        double x = R1 * (k - 1) * (Math.cos(angle) + Math.cos((k - 1) * angle) / (k - 1));
        double y = R1 * (k - 1) * (Math.sin(angle) - Math.sin((k - 1) * angle) / (k - 1));
        int centerX = cX + (int) x;
        int centerY = cY + (int) y;
        g.setColor(Color.BLUE);
        g.drawOval(centerX - R1, centerY - R1, 2 * R1, 2 * R1);
    }
    private void paintCircle(Graphics2D g2d, int cX, int cY, int centX, int centY, int radius, double x, double y) {
        g2d.drawOval(centX + cX - radius, cY - radius - centY, radius * 2, radius * 2);
        g2d.drawLine(centX + cX, cY - centY, cX + (int) x, cY + (int) y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            drawingPanel.repaint();
        } else if (e.getActionCommand().equals("Refresh")) {
            try {
                R2 = Integer.parseInt(radiusField.getText());
                k = Integer.parseInt(kField.getText());
                R1 = R2 / k;
                String selectedColor = (String) colorComboBox.getSelectedItem();
                switch (selectedColor) {
                    case "Red":
                        shapeColor = Color.RED;
                        break;
                    case "Blue":
                        shapeColor = Color.BLUE;
                        break;
                    case "Green":
                        shapeColor = Color.GREEN;
                        break;
                    case "Yellow":
                        shapeColor = Color.YELLOW;
                        break;
                    case "Orange":
                        shapeColor = Color.ORANGE;
                        break;
                    case "Pink":
                        shapeColor = Color.PINK;
                        break;
                }
                speed = Integer.parseInt(speedField.getText());
                timer.setDelay(speed); // Изменение скорости
                resetDrawing();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid radius, k, or speed value.");
            }
        }
    }

    private void resetDrawing() {
        angle = initT;
        i = 0;
        drawingComplete = false;
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new hypocycloid().setVisible(true));
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fifth_1 extends JPanel {
    private int x1, y1, x2, y2;
    private Color lineColor;

    public fifth_1(int x1, int y1, int x2, int y2, Color lineColor) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.lineColor = lineColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(lineColor);
        g2d.drawLine(x1, y1, x2, y2);
    }

    public void setLineCoordinates(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Drawing Line in PictureBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Устанавливаем начальные координаты и цвет линии
        int x1 = 50, y1 = 50, x2 = 200, y2 = 200;
        Color lineColor = new Color(195, 15, 234);

        fifth_1 drawingPanel = new fifth_1(x1, y1, x2, y2, lineColor);
        frame.add(drawingPanel, BorderLayout.CENTER);

        // Создаем панель для ввода координат
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JTextField x1Field = new JTextField(Integer.toString(x1), 5);
        JTextField y1Field = new JTextField(Integer.toString(y1), 5);
        JTextField x2Field = new JTextField(Integer.toString(x2), 5);
        JTextField y2Field = new JTextField(Integer.toString(y2), 5);

        inputPanel.add(new JLabel("X1:"));
        inputPanel.add(x1Field);
        inputPanel.add(new JLabel("Y1:"));
        inputPanel.add(y1Field);
        inputPanel.add(new JLabel("X2:"));
        inputPanel.add(x2Field);
        inputPanel.add(new JLabel("Y2:"));
        inputPanel.add(y2Field);

        JButton drawButton = new JButton("Draw Line");
        inputPanel.add(drawButton);

        frame.add(inputPanel, BorderLayout.EAST);

        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newX1 = Integer.parseInt(x1Field.getText());
                    int newY1 = Integer.parseInt(y1Field.getText());
                    int newX2 = Integer.parseInt(x2Field.getText());
                    int newY2 = Integer.parseInt(y2Field.getText());
                    drawingPanel.setLineCoordinates(newX1, newY1, newX2, newY2);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid coordinates.");
                }
            }
        });

        frame.setVisible(true);
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fifth_4 extends JFrame {
    private JComboBox<String> shapeComboBox;
    private JComboBox<String> colorComboBox;
    private JLabel label;
    private JPanel drawingPanel;

    public fifth_4() {
        setTitle("Закрашивание фигур");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        label = new JLabel("Выберите фигуру и цвет:");
        String[] shapes = {"Прямоугольник", "Эллипс", "Окружность"};
        shapeComboBox = new JComboBox<>(shapes);
        String[] colors = {"Красный", "Зеленый", "Синий", "Желтый", "Оранжевый"};
        colorComboBox = new JComboBox<>(colors);
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawShape(g);
            }
        };

        // Layout components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(label);
        inputPanel.add(shapeComboBox);
        inputPanel.add(colorComboBox);

        add(inputPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);

        // Add action listener to the combo boxes
        shapeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.repaint();
            }
        });

        colorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.repaint();
            }
        });
    }

    private void drawShape(Graphics g) {
        String selectedShape = (String) shapeComboBox.getSelectedItem();
        String selectedColor = (String) colorComboBox.getSelectedItem();
        if (selectedShape != null && selectedColor != null) {
            Color color = getColor(selectedColor);
            g.setColor(color);
            switch (selectedShape) {
                case "Прямоугольник":
                    g.fillRect(60, 60, 120, 180);
                    break;
                case "Эллипс":
                    g.fillOval(60, 60, 120, 180);
                    break;
                case "Окружность":
                    g.fillOval(60, 60, 120, 120);
                    break;
            }
        }
    }

    private Color getColor(String colorName) {
        switch (colorName) {
            case "Красный":
                return Color.RED;
            case "Зеленый":
                return Color.GREEN;
            case "Синий":
                return Color.BLUE;
            case "Желтый":
                return Color.YELLOW;
            case "Оранжевый":
                return Color.ORANGE;
            default:
                return Color.BLACK;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new fifth_4().setVisible(true);
            }
        });
    }
}
import javax.swing.*;
import java.awt.*;

public class third_1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);

        JButton button = new JButton("Press");
        button.setBounds(100, 170, 100, 30);
        button.setBackground(new Color(127, 0, 255)); // Изменение цвета кнопки через RGB
        button.setForeground(new Color(255, 255, 255)); // Изменение цвета текста на кнопке через RGB
        frame.getContentPane().add(button);

        JTextField textField = new JTextField("Text");
        textField.setBounds(80, 100, 200, 30);
        textField.setFont(new Font("Arial", Font.BOLD, 16)); // Изменение шрифта текста
        frame.getContentPane().add(textField);

        JLabel label = new JLabel("Label 1");
        label.setBounds(10, 20, 100, 30);
        label.setFont(new Font("Arial", Font.ITALIC, 16)); // Изменение шрифта текста
        frame.getContentPane().add(label);

        JLabel label2 = new JLabel("Label 2");
        label2.setBounds(10, 100, 100, 30);
        label2.setFont(new Font("Arial", Font.ITALIC, 12)); // Изменение шрифта текста
        frame.getContentPane().add(label2);

        // Загрузка изображения для фона
        ImageIcon backgroundImage = new ImageIcon("C:/Users/kbelk/OneDrive/Рабочий стол/практика 2 курс/backgroung.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, 300, 300);
        frame.getContentPane().add(backgroundLabel);

        frame.setLayout(null);
        frame.setVisible(true);
    }
}
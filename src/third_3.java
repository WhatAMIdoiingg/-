import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class third_3 extends JFrame {
    private JTextField textField;
    private JLabel pictureBox;

    public third_3() {
        setTitle("Text to PictureBox");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Создаем текстовое поле для ввода текста
        textField = new JTextField(20);
        textField.addActionListener(new TextFieldListener());

        // Создаем PictureBox для отображения текста
        pictureBox = new JLabel();
        pictureBox.setHorizontalAlignment(JLabel.CENTER);
        pictureBox.setOpaque(true); // Делаем фон JLabel видимым
        pictureBox.setBackground(Color.WHITE); // Устанавливаем белый фон
        pictureBox.setForeground(new Color(11, 72, 203)); // Устанавливаем цвет текста по RGB

        // Добавляем компоненты на панель
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textField, BorderLayout.NORTH);
        panel.add(pictureBox, BorderLayout.CENTER);

        add(panel);
    }

    private class TextFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = textField.getText();
            if (!text.isEmpty()) {
                pictureBox.setText(text);
                pictureBox.setFont(new Font("Arial", Font.BOLD, 14)); // Устанавливаем шрифт и размер
                pictureBox.setPreferredSize(new Dimension(300, 100)); // Устанавливаем размер PictureBox
                pictureBox.setHorizontalAlignment(JLabel.LEFT); // Выравниваем текст
                pictureBox.setVerticalAlignment(JLabel.TOP); // Выравниваем текст
            } else {
                pictureBox.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            third_3 frame = new third_3();
            frame.setVisible(true);
        });
    }
}
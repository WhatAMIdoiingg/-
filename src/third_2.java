import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class third_2 extends JFrame {
    private JComboBox<String> comboBox;
    private JLabel pictureBox;

    public third_2() {
        setTitle("Image Loader");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Создаем ComboBox с вариантами выбора
        String[] imageOptions = {"Image 1", "Image 2", "Image 3", "Image 4"};
        comboBox = new JComboBox<>(imageOptions);
        comboBox.addActionListener(new ComboBoxListener());

        // Создаем PictureBox для отображения изображений
        pictureBox = new JLabel();
        pictureBox.setHorizontalAlignment(JLabel.CENTER);

        // Добавляем компоненты на панель
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(comboBox, BorderLayout.NORTH);
        panel.add(pictureBox, BorderLayout.CENTER);

        add(panel);
    }

    private class ComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String selected = (String) combo.getSelectedItem();

            if (selected != null) {
                ImageIcon icon = null;
                switch (selected) {
                    case "Image 1":
                        icon = new ImageIcon("C:/Users/kbelk/OneDrive/Рабочий стол/практика 2 курс/image1.jpg");
                        break;
                    case "Image 2":
                        icon = new ImageIcon("C:/Users/kbelk/OneDrive/Рабочий стол/практика 2 курс/image2.jpg");
                        break;
                    case "Image 3":
                        icon = new ImageIcon("C:/Users/kbelk/OneDrive/Рабочий стол/практика 2 курс/image3.jpg");
                        break;
                    case "Image 4":
                        icon = new ImageIcon("C:/Users/kbelk/OneDrive/Рабочий стол/практика 2 курс/image4.jpg");
                        break;
                }

                if (icon != null) {
                    pictureBox.setIcon(icon);
                } else {
                    pictureBox.setIcon(null);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            third_2 frame = new third_2();
            frame.setVisible(true);
        });
    }
}
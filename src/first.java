import javax.swing.*; // Импортируем все классы из пакета javax.swing для использования графических компонентов
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class first { //
    public static void main(String[] args) { // Объявляем главный метод, который является точкой входа в программу
        JFrame frame = new JFrame("My First GUI"); // Создаем новое окно с заголовком "My First GUI"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Устанавливаем поведение окна при закрытии: завершение программы
        frame.setSize(300, 300); // Устанавливаем размеры окна
        frame.setLocationRelativeTo(null); // Устанавливаем окно по центру экрана

        JButton button = new JButton("Copy"); // Создаем кнопку с надписью "Press"
        button.setBounds(100, 170, 100, 30); // Устанавливаем позицию и размеры кнопки
        frame.getContentPane().add(button); // Добавляем кнопку на панель содержимого окна

        JTextField textField = new JTextField("Text"); // Создаем текстовое поле с начальным текстом "Text"
        textField.setBounds(50, 100, 200, 30); // Устанавливаем позицию и размеры текстового поля
        frame.getContentPane().add(textField); // Добавляем текстовое поле на панель содержимого окна

        JLabel label = new JLabel("Label Text"); // Создаем метку с текстом "Label Text"
        label.setBounds(20, 20, 100, 30); // Устанавливаем позицию и размеры метки
        frame.getContentPane().add(label); // Добавляем метку на панель содержимого окна
        // Добавляем слушатель событий к кнопке
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText(); // Получаем текст из текстового поля
                label.setText(text); // Устанавливаем текст в метку
            }
        });
        frame.setLayout(null); // Устанавливаем null layout, что позволяет вручную позиционировать компоненты
        frame.setVisible(true); // Делаем окно видимым
    }
}
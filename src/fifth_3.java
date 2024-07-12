import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fifth_3 extends JFrame {
    private JTextField[] textFields; // Массив текстовых полей для ввода данных
    private JButton drawButton; // Кнопка для запуска рисования
    private JLabel statusLabel; // Метка для отображения сообщений пользователю
    private JPanel drawingPanel; // Панель для рисования эллипса

    public fifth_3() {
        // Настройка основного окна
        setTitle("Draw Ellipse or Circle"); // Установка заголовка окна
        setSize(600, 400); // Установка размеров окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка действия при закрытии окна
        setLayout(new BorderLayout()); // Установка менеджера компоновки для окна

        // Инициализация компонентов
        textFields = new JTextField[4]; // Создание массива для текстовых полей
        for (int i = 0; i < textFields.length; i++) {
            textFields[i] = new JTextField(5); // Создание каждого текстового поля
        }

        drawButton = new JButton("Draw Ellipse"); // Создание кнопки для рисования
        statusLabel = new JLabel("Enter coordinates and radii"); // Создание метки для сообщений
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // Вызов метода родительского класса для очистки панели
                Graphics2D g2d = (Graphics2D) g; // Приведение Graphics к Graphics2D для дополнительных возможностей
                int[] params = getParams(); // Получение параметров для рисования
                if (params != null) {
                    int x = params[0]; // Координата X верхнего левого угла
                    int y = params[1]; // Координата Y верхнего левого угла
                    int width = params[2]; // Ширина эллипса (горизонтальный радиус * 2)
                    int height = params[3]; // Высота эллипса (вертикальный радиус * 2)
                    g2d.setColor(Color.BLUE); // Установка цвета контура эллипса
                    g2d.drawOval(x, y, width, height); // Рисование эллипса или окружности
                }
            }
        };

        // Размещение компонентов на форме
        JPanel inputPanel = new JPanel(); // Создание панели для ввода данных
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Установка вертикального BoxLayout
        inputPanel.add(new JLabel("Top-left X:")); // Добавление метки для координаты X
        inputPanel.add(textFields[0]); // Добавление текстового поля для координаты X
        inputPanel.add(new JLabel("Top-left Y:")); // Добавление метки для координаты Y
        inputPanel.add(textFields[1]); // Добавление текстового поля для координаты Y
        inputPanel.add(new JLabel("Width (horizontal radius * 2):")); // Добавление метки для ширины
        inputPanel.add(textFields[2]); // Добавление текстового поля для ширины
        inputPanel.add(new JLabel("Height (vertical radius * 2):")); // Добавление метки для высоты
        inputPanel.add(textFields[3]); // Добавление текстового поля для высоты
        inputPanel.add(drawButton); // Добавление кнопки для рисования
        inputPanel.add(statusLabel); // Добавление метки для сообщений

        add(inputPanel, BorderLayout.EAST); // Добавление панели ввода данных в правую часть окна
        add(drawingPanel, BorderLayout.CENTER); // Добавление панели для рисования в центр окна

        // Добавление слушателя кнопки
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.repaint(); // Перерисовка панели для обновления рисунка
            }
        });
    }

    // Метод для получения параметров из текстовых полей
    private int[] getParams() {
        int[] params = new int[4]; // Массив для хранения параметров
        for (int i = 0; i < textFields.length; i++) {
            try {
                params[i] = Integer.parseInt(textFields[i].getText()); // Преобразование текста в число
            } catch (NumberFormatException e) {
                statusLabel.setText("Please enter valid numbers."); // Установка сообщения об ошибке
                return null; // Возвращение null, если введены некорректные данные
            }
        }
        statusLabel.setText("Ellipse drawn."); // Установка сообщения об успешном рисовании
        return params; // Возвращение массива параметров
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new fifth_3().setVisible(true); // Создание и отображение окна
            }
        });
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.Map;

public class Trajectory extends JFrame {
    private JPanel drawingPanel; // Панель для рисования траектории
    private JPanel settingsPanel; // Панель для настроек
    private JComboBox<String> pathColorComboBox; // Выпадающий список для выбора цвета траектории
    private JComboBox<String> diamondColorComboBox; // Выпадающий список для выбора цвета ромба
    private JComboBox<String> lineThicknessComboBox; // Выпадающий список для выбора толщины линии
    private JComboBox<String> lineStyleComboBox; // Выпадающий список для выбора стиля линии
    private JComboBox<String> speedComboBox; // Выпадающий список для выбора скорости
    private JComboBox<String> sizeChangeSpeedComboBox; // Выпадающий список для выбора скорости изменения размера
    private JComboBox<String> objectSizeComboBox; // Выпадающий список для выбора размера объекта
    private JComboBox<String> directionComboBox; // Выпадающий список для выбора направления движения
    private JComboBox<String> backgroundComboBox; // Выпадающий список для выбора цвета фона
    private JComboBox<String> rotationComboBox; // Выпадающий список для выбора угла поворота
    private JComboBox<String> stepComboBox; // Выпадающий список для выбора шага перемещения
    private JComboBox<String> repeatCountComboBox; // Выпадающий список для выбора количества повторений
    private JComboBox<String> trajectorySizeComboBox; // Выпадающий список для выбора размера траектории
    private JComboBox<String> diamondRotationSpeedComboBox; // Выпадающий список для выбора скорости вращения ромба
    private JComboBox<String> diamondRotationStepComboBox; // Выпадающий список для выбора шага вращения ромба
    private JButton startButton; // Кнопка для запуска анимации
    private JButton stopButton; // Кнопка для остановки анимации

    private boolean running = false; // Флаг для отслеживания состояния анимации
    private double t = 0; // Переменная для отслеживания текущего времени в анимации
    private double DT = 0.01; // Константа для шага времени в анимации
    private double speed = 1.0; // Скорость анимации
    private double diamondSize = 1.0; // Размер ромба
    private double sizeChangeSpeed = 0.01; // Скорость изменения размера
    private boolean sizeChange = false; // Флаг для включения/выключения изменения размера
    private int turns = 1; // Количество оборотов
    private String direction = "По часовой"; // Направление движения
    private Color pathColor = Color.BLUE; // Цвет траектории
    private Color diamondColor = Color.BLUE; // Цвет ромба
    private BasicStroke stroke = new BasicStroke(1); // Стиль линии
    private Color backgroundColor = Color.WHITE; // Цвет фона
    private double rotationAngle = 0; // Угол поворота
    private double pulseSpeed = 0.0; // Скорость пульсации
    private double pulseAmplitude = 1.0; // Амплитуда пульсации
    private double step = 0.01; // Шаг перемещения
    private int repeatCount = 1000; // Количество повторений
    private double A = 100; // Размер траектории
    private double diamondRotationSpeed = 0.0; // Скорость вращения ромба
    private double diamondRotationStep = 0.0; // Угол поворота ромба

    private static final int WIDTH = 800; // Ширина окна
    private static final int HEIGHT = 600; // Высота окна

    private Map<String, Color> colorMap = new HashMap<>(); // Карта для сопоставления строк и цветов
    private Map<String, BasicStroke> strokeMap = new HashMap<>(); // Карта для сопоставления строк и стилей линий

    public Trajectory() { // Конструктор
        setTitle("Движение ромба по лемнискате"); // Устанавливаем заголовок окна
        setSize(WIDTH, HEIGHT); // Устанавливаем размер окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Устанавливаем действие при закрытии окна

        drawingPanel = new JPanel() { // Создаем панель для рисования
            @Override
            protected void paintComponent(Graphics g) { // Переопределяем метод paintComponent для рисования
                super.paintComponent(g); // Вызываем метод paintComponent родительского класса
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Включаем сглаживание

                g2d.setColor(backgroundColor); // Устанавливаем цвет фона
                g2d.fillRect(0, 0, getWidth(), getHeight()); // Заполняем фон

                Path2D path = new Path2D.Double(); //  для рисования траектории
                double x = getX(0, rotationAngle); // Вычисляем начальную координату x
                double y = getY(0, rotationAngle); // Вычисляем начальную координату y
                path.moveTo(x, y); //  перо к начальной точке
                double firstX = x; // Сохраняем первую координату x
                double firstY = y; // Сохраняем первую координату y
                for (double t = 0; t <= 2 * Math.PI; t += step) { // Цикл для рисования траектории
                    x = getX(t, rotationAngle);
                    y = getY(t, rotationAngle);
                    path.lineTo(x, y);///перо
                }

                g2d.setColor(pathColor); // Устанавливаем цвет траектории
                g2d.setStroke(stroke); // Устанавливаем стиль линии
                g2d.draw(path); // Рисуем траекторию
                // Рисуем линию от последней точки до первой
                g2d.drawLine((int) x, (int) y, (int) firstX, (int) firstY);
                g2d.setColor(diamondColor); // Устанавливаем цвет ромба
                double centerX = getX(t, rotationAngle); // Вычисляем координату x центра ромба
                double centerY = getY(t, rotationAngle); // Вычисляем координату y центра ромба
                double size = 10 * (diamondSize + pulseAmplitude * Math.sin(pulseSpeed * t)); // Вычисляем размер ромба
                double rotation = diamondRotationSpeed * t + diamondRotationStep; // Вычисляем угол поворота ромба
                // Координаты вершин ромба без учета поворота
                double[] xPoints = {centerX, centerX - size, centerX, centerX + size};
                double[] yPoints = {centerY - 2*size, centerY, centerY + 2*size, centerY};

                // Пересчет координат вершин с учетом поворота
                for (int i = 0; i < xPoints.length; i++) {
                    double tempX = xPoints[i] - centerX;
                    double tempY = yPoints[i] - centerY;
                    xPoints[i] = tempX * Math.cos(Math.toRadians(rotation)) - tempY * Math.sin(Math.toRadians(rotation)) + centerX;
                    yPoints[i] = tempX * Math.sin(Math.toRadians(rotation)) + tempY * Math.cos(Math.toRadians(rotation)) + centerY;
                }


                // Рисование ромба с учетом поворота
                g2d.drawLine((int) xPoints[0], (int) yPoints[0], (int) xPoints[1], (int) yPoints[1]);
                g2d.drawLine((int) xPoints[1], (int) yPoints[1], (int) xPoints[2], (int) yPoints[2]);
                g2d.drawLine((int) xPoints[2], (int) yPoints[2], (int) xPoints[3], (int) yPoints[3]);
                g2d.drawLine((int) xPoints[3], (int) yPoints[3], (int) xPoints[0], (int) yPoints[0]);
            }

            private double getX(double t, double rotationAngle) { // Метод для вычисления координаты x
                double originalX = ((double) 800 / 2) + A * Math.sqrt(2) * Math.cos(t) / (Math.sin(t) * Math.sin(t) + 1);
                double originalY = (double) 600 / 4 + A * Math.sqrt(2) * Math.cos(t) * Math.sin(t) / (Math.sin(t) * Math.sin(t) + 1);
                double centerX = (double) 800 / 2;
                double centerY = (double) 600 / 4;
                double cosTheta = Math.cos(Math.toRadians(rotationAngle));
                double sinTheta = Math.sin(Math.toRadians(rotationAngle));
                double rotatedX = centerX + (originalX - centerX) * cosTheta - (originalY - centerY) * sinTheta;
                return rotatedX;
            }

            private double getY(double t, double rotationAngle) { // Метод для вычисления координаты y
                double originalX = ((double) 800 / 2) + A * Math.sqrt(2) * Math.cos(t) / (Math.sin(t) * Math.sin(t) + 1);
                double originalY = (double) 600 / 4 + A * Math.sqrt(2) * Math.cos(t) * Math.sin(t) / (Math.sin(t) * Math.sin(t) + 1);
                double centerX = (double) 800 / 2;
                double centerY = (double) 600 / 4;
                double cosTheta = Math.cos(Math.toRadians(rotationAngle));
                double sinTheta = Math.sin(Math.toRadians(rotationAngle));
                double rotatedY = centerY + (originalX - centerX) * sinTheta + (originalY - centerY) * cosTheta;
                return rotatedY;
            }
        };
        drawingPanel.setBackground(Color.WHITE); // Устанавливаем цвет фона панели для рисования
        add(drawingPanel, BorderLayout.CENTER); // Добавляем панель для рисования в центр окна

        settingsPanel = new JPanel(); // Создаем панель для настроек
        settingsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Устанавливаем отступы

        startButton = new JButton("Старт"); // Создаем кнопку для запуска анимации
        stopButton = new JButton("Стоп"); // Создаем кнопку для остановки анимации

        gbc.gridx = 0; // Устанавливаем координаты для кнопки "Старт"
        gbc.gridy = 0;
        settingsPanel.add(startButton, gbc); // Добавляем кнопку "Старт" на панель настроек
        gbc.gridx = 1;
        settingsPanel.add(stopButton, gbc); // Добавляем кнопку "Стоп" на панель настроек

        String[] colorNames = {"Синий", "Красный", "Зеленый", "Желтый", "Оранжевый", "Розовый", "Пурпурный", "Голубой", "Белый", "Черный"}; // Массив названий цветов
        String[] colorNames_back = {"Белый","Синий", "Красный", "Зеленый", "Желтый", "Оранжевый", "Розовый", "Пурпурный", "Голубой",  "Черный"};
        String[] strokeNames = {"Сплошной", "Пунктир", "Точки"}; // Массив названий стилей линий
        String[] rotationValues = {"0", "45", "90", "135", "180", "225", "270", "315"}; // Массив значений углов поворота
        String[] lineThicknessValues = {"1", "2", "3", "4", "5"}; // Массив значений толщины линий
        String[] speedValues = { "1.0","0.5", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0"}; // Массив значений скорости
        String[] stepValues = {"0.01", "0.02", "0.05", "0.1", "0.2", "0.5"}; // Массив значений шага перемещения
        String[] repeatCountValues = {"Непрерывно","1", "2", "3", "4", "5"}; // Массив значений количества повторений
        String[] trajectorySizeValues = {"50", "100", "150", "200", "250"}; // Массив значений размера траектории
        String[] diamondRotationSpeedValues = {"0.0", "50.0", "100.0", "300.0", "400.0", "500.0"}; // Массив значений скорости вращения ромба
        String[] diamondRotationStepValues = {"0.0", "15.0", "30.0", "45.0", "60.0", "75.0", "90.0"}; // Массив значений угла поворота ромба
        String[] SpeedValues_ofsize = {"0","1", "2", "3", "4", "5"}; // Массив значений толщины линий

        colorMap.put("Синий", Color.BLUE); // Добавляем соответствие названия цвета и объекта Color
        colorMap.put("Красный", Color.RED);
        colorMap.put("Зеленый", Color.GREEN);
        colorMap.put("Желтый", Color.YELLOW);
        colorMap.put("Оранжевый", Color.ORANGE);
        colorMap.put("Розовый", Color.PINK);
        colorMap.put("Пурпурный", Color.MAGENTA);
        colorMap.put("Голубой", Color.CYAN);
        colorMap.put("Белый", Color.WHITE);
        colorMap.put("Черный", Color.BLACK);

        strokeMap.put("Сплошной", new BasicStroke(1)); // соответствие названия стиля линии и объекта BasicStroke
        strokeMap.put("Пунктир", new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        strokeMap.put("Точки", new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{1, 9}, 0));

        gbc.gridx = 0; // Устанавливаем координаты
        gbc.gridy = 1;
        settingsPanel.add(new JLabel("Цвет траектории"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++; // Увеличиваем координату y для следующего элемента
        pathColorComboBox = new JComboBox<>(colorNames); // Создаем выпадающий список для выбора цвета траектории
        settingsPanel.add(pathColorComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 1;
        gbc.gridy = 1;
        settingsPanel.add(new JLabel("Цвет Ромба"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        diamondColorComboBox = new JComboBox<>(colorNames);
        settingsPanel.add(diamondColorComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 2;
        gbc.gridy = 1;
        settingsPanel.add(new JLabel("Толщина линии"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        lineThicknessComboBox = new JComboBox<>(lineThicknessValues);
        settingsPanel.add(lineThicknessComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 3;
        gbc.gridy = 1;
        settingsPanel.add(new JLabel("Стиль линии"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        lineStyleComboBox = new JComboBox<>(strokeNames);
        settingsPanel.add(lineStyleComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 0;
        gbc.gridy = 3;
        settingsPanel.add(new JLabel("Скорость"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        speedComboBox = new JComboBox<>(speedValues);
        settingsPanel.add(speedComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 1;
        gbc.gridy = 3;
        settingsPanel.add(new JLabel("Скорость пульсации"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        sizeChangeSpeedComboBox = new JComboBox<>(speedValues);
        settingsPanel.add(sizeChangeSpeedComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 2;
        gbc.gridy = 3;
        settingsPanel.add(new JLabel("Размер объекта"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        objectSizeComboBox = new JComboBox<>(speedValues);
        settingsPanel.add(objectSizeComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 3;
        gbc.gridy = 3;
        settingsPanel.add(new JLabel("Количество повторений"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        repeatCountComboBox = new JComboBox<>(repeatCountValues);
        settingsPanel.add(repeatCountComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 0;
        gbc.gridy = 5;
        settingsPanel.add(new JLabel("Направление движения"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        directionComboBox = new JComboBox<>(new String[]{"По часовой", "Против часовой"});
        settingsPanel.add(directionComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 1;
        gbc.gridy = 5;
        settingsPanel.add(new JLabel("Фон"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        backgroundComboBox = new JComboBox<>(colorNames_back);
        settingsPanel.add(backgroundComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 2;
        gbc.gridy = 5;
        settingsPanel.add(new JLabel("Поворот траектории"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        rotationComboBox = new JComboBox<>(rotationValues);
        settingsPanel.add(rotationComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 3;
        gbc.gridy = 5;
        settingsPanel.add(new JLabel("Шаг перемещения"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        stepComboBox = new JComboBox<>(stepValues);
        settingsPanel.add(stepComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 0;
        gbc.gridy = 7;
        settingsPanel.add(new JLabel("Размер траектории"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        trajectorySizeComboBox = new JComboBox<>(trajectorySizeValues);
        settingsPanel.add(trajectorySizeComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 1;
        gbc.gridy = 7;
        settingsPanel.add(new JLabel("Скорость вращения ромба"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        diamondRotationSpeedComboBox = new JComboBox<>(diamondRotationSpeedValues);
        settingsPanel.add(diamondRotationSpeedComboBox, gbc); // Добавляем выпадающий список на панель настроек

        gbc.gridx = 2;
        gbc.gridy = 7;
        settingsPanel.add(new JLabel("Угол поворота ромба"), gbc); // Добавляем метку на панель настроек
        gbc.gridy++;
        diamondRotationStepComboBox = new JComboBox<>(diamondRotationStepValues);
        settingsPanel.add(diamondRotationStepComboBox, gbc); // Добавляем выпадающий список на панель настроек

        add(settingsPanel, BorderLayout.SOUTH); // Добавляем панель настроек внизу окна

        startButton.addActionListener(e -> { // Добавляем слушатель событий для кнопки "Старт"
            running = true; // Устанавливаем флаг запуска анимации в true
            new Thread(this::run).start(); // Запускаем новый поток для анимации
        });

        stopButton.addActionListener(e -> running = false); // Добавляем слушатель событий для кнопки "Стоп"

        pathColorComboBox.addActionListener(e -> pathColor = colorMap.get((String) pathColorComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка цвета траектории
        diamondColorComboBox.addActionListener(e -> diamondColor = colorMap.get((String) diamondColorComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка цвета ромба
        directionComboBox.addActionListener(e -> direction = (String) directionComboBox.getSelectedItem()); // Добавляем слушатель событий для выпадающего списка направления движения
        speedComboBox.addActionListener(e -> speed = Double.parseDouble((String) speedComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка скорости
        lineThicknessComboBox.addActionListener(e -> stroke = new BasicStroke(Float.parseFloat((String) lineThicknessComboBox.getSelectedItem()))); // Добавляем слушатель событий для выпадающего списка толщины линии
        lineStyleComboBox.addActionListener(e -> stroke = strokeMap.get((String) lineStyleComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка стиля линии
        backgroundComboBox.addActionListener(e -> backgroundColor = colorMap.get((String) backgroundComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка цвета фона
        objectSizeComboBox.addActionListener(e -> diamondSize = Double.parseDouble((String) objectSizeComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка размера объекта
        rotationComboBox.addActionListener(e -> rotationAngle = Double.parseDouble((String) rotationComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка угла поворота
        sizeChangeSpeedComboBox.addActionListener(e -> pulseSpeed = Double.parseDouble((String) sizeChangeSpeedComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка скорости пульсации
        stepComboBox.addActionListener(e -> step = Double.parseDouble((String) stepComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка шага перемещения
        repeatCountComboBox.addActionListener(e -> { // Добавляем слушатель событий для выпадающего списка количества повторений
            String selected = (String) repeatCountComboBox.getSelectedItem();
            if (selected.equals("Непрерывно")) {
                repeatCount = 1000;
            } else {
                repeatCount = Integer.parseInt(selected);
            }
        });
        trajectorySizeComboBox.addActionListener(e -> A = Double.parseDouble((String) trajectorySizeComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка размера траектории
        diamondRotationSpeedComboBox.addActionListener(e -> diamondRotationSpeed = Double.parseDouble((String) diamondRotationSpeedComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка скорости вращения ромба
        diamondRotationStepComboBox.addActionListener(e -> diamondRotationStep = Double.parseDouble((String) diamondRotationStepComboBox.getSelectedItem())); // Добавляем слушатель событий для выпадающего списка угла поворота ромба

        setVisible(true); // Делаем окно видимым
    }


    private void run() {// метод запуска анимации
        while (running) {
            t += step * speed * (direction.equals("По часовой") ? 1 : -1);// Обновляем время в анимации
            if (t > 2 * Math.PI * repeatCount) {
                t = 0;
                running = false;
            }
            repaint();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Trajectory();
    }
}
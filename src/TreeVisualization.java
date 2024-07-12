import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Класс для хранения глобальных переменных
class GlobalVariables {
    private static int levels; // Переменная для хранения уровней

    // Метод для получения текущего значения уровней
    public static int getLevels() {
        return levels;
    }

    // Метод для установки нового значения уровней
    public static void setLevels(int newLevels) {
        levels = newLevels;
    }
}

// Класс для представления узла дерева
class TreeNode {
    List<TreeNode> children; // Список дочерних узлов
    Color color; // Цвет узла

    // Конструктор для инициализации списка дочерних узлов
    TreeNode() {
        this.children = new ArrayList<>();
    }

    // Метод для добавления дочернего узла
    void addChild(TreeNode child) {
        children.add(child);
    }
}

// Класс для представления дерева
class Tree {
    TreeNode root; // Корневой узел дерева
    private int levels = GlobalVariables.getLevels() + 1; // Количество уровней дерева
    private Map<Color, Color> colorMap; // Карта для хранения цветов
    private static final Color[] colorCycle = {Color.RED, Color.BLUE, Color.YELLOW, Color.PINK, Color.GREEN, Color.CYAN}; // Цикл цветов

    // Конструктор для инициализации дерева
    Tree(int initialValue) {
        root = new TreeNode();
        colorMap = new HashMap<>();
        generateTree(root, levels, true, 1.0f);
    }

    // Рекурсивный метод для генерации дерева
    private void generateTree(TreeNode node, int level, boolean isRoot, float colorScale) {
        if (level == 0) return; // Базовый случай: если уровень равен 0, завершить рекурсию
        int childCount = isRoot ? 6 : 3; // Количество дочерних узлов: 6 для корня, 3 для остальных
        for (int i = 0; i < childCount; i++) {
            TreeNode child = new TreeNode();
            if (isRoot) {
                child.color = getColorForBranch(i); // Установить цвет для корневого узла
            } else {
                child.color = getChildColor(node.color); // Установить цвет для дочернего узла
            }
            node.addChild(child);
            generateTree(child, level - 1, false, colorScale - 0.15f); // Рекурсивный вызов для дочернего узла
        }
    }

    // Метод для получения цвета для ветки
    private Color getColorForBranch(int index) {
        return colorCycle[index % colorCycle.length];
    }

    // Метод для получения цвета для дочернего узла
    private Color getChildColor(Color parentColor) {
        int index = -1;
        for (int i = 0; i < colorCycle.length; i++) {
            if (colorCycle[i].equals(parentColor)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return Color.BLACK; // Если цвет не найден, вернуть черный цвет
        }
        return colorCycle[(index + 1) % colorCycle.length];
    }

    // Метод для обновления уровней дерева
    public void updateLevels(int newLevels) {
        levels = newLevels;
        root = new TreeNode();
        colorMap.clear();
        generateTree(root, levels+1, true, 1.0f);
    }
}

// Класс для представления панели дерева
class TreePanel extends JPanel {
    private Tree tree; // Дерево
    private double scale = 1.0; // Масштаб
    private int offsetX = 0; // Смещение по X
    private int offsetY = 0; // Смещение по Y
    private int[] levelGaps; // Промежутки между уровнями
    private int levels = GlobalVariables.getLevels() + 1; // Количество уровней
    private SierpinskiHexagon hexagon; // Шестиугольник
    private Point mouseStartPoint; // Начальная точка для перетаскивания

    // Конструктор для инициализации панели дерева
    TreePanel(Tree tree, SierpinskiHexagon hexagon) {
        this.tree = tree;
        this.hexagon = hexagon;
        setPreferredSize(new Dimension(2000, 2000));
        levelGaps = calculateLevelGaps(levels);

        // Обработчик для масштабирования колесиком мыши
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (e.isControlDown()) {
                    scale += notches * -0.1;
                    scale = Math.max(0.1, scale);
                } else {
                    offsetY += notches * 20;
                }
                repaint();
            }
        });

        // Обработчик для клика мыши
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int level = getLevelFromClick(e.getPoint());
                if (level != -1) {
                    hexagon.setN(level);
                    hexagon.setColors(getColorsForLevel(level));
                    hexagon.repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouseStartPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseStartPoint = null;
            }
        });

        // Обработчик для перетаскивания мыши
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseStartPoint != null) {
                    offsetX += e.getX() - mouseStartPoint.x;
                    // offsetY += e.getY() - mouseStartPoint.y;
                    mouseStartPoint = e.getPoint();
                    repaint();
                }
            }
        });
    }

    // Метод для определения уровня по клику мыши
    private int getLevelFromClick(Point point) {
        double y = point.getY() * scale + offsetY; // Преобразование координаты Y с учетом масштаба и смещения
        double currentHeight = 30 * scale; // Начальная высота для корня дерева с учетом масштаба
        for (int i = 0; i < levels; i++) {
            double levelHeight = 100 * scale; // Высота текущего уровня с учетом масштаба
            if (y >= currentHeight && y < currentHeight + levelHeight) {
                return i; // Возвращаем уровень, если точка попадает в текущий уровень
            }
            currentHeight += levelHeight; // Обновляем текущую высоту для следующего уровня
        }
        return -1; // Возвращаем -1, если уровень не найден
    }

    // Метод для получения цветов для уровня
    public Color[] getColorsForLevel(int level) {
        Color[] colors = new Color[6];
        TreeNode node = tree.root;
        for (int i = 0; i < 6; i++) {
            if (i < node.children.size()) {
                colors[i] = node.children.get(i).color;
            } else {
                colors[i] = Color.BLACK;
            }
        }
        Color[] shiftedColors = new Color[6];
        int shift = level % 6;
        for (int i = 0; i < 6; i++) {
            shiftedColors[i] = colors[(i + shift) % 6];
        }
        return shiftedColors;
    }

    // Метод для расчета промежутков между уровнями
    private int[] calculateLevelGaps(int levels) {
        int[] gaps = new int[levels];
        for (int i = 0; i < levels; i++) {
            gaps[i] = 12 * (int) Math.pow(3, levels - 1 - i);
        }
        return gaps;
    }

    // Метод для отрисовки компонента
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(offsetX, offsetY);
        g2d.scale(scale, scale);
        drawTree(g2d, tree.root, getWidth() / 2, 30, levelGaps[0], levels, 0);
    }

    // Рекурсивный метод для отрисовки дерева
    private void drawTree(Graphics2D g, TreeNode node, double x, double y, double horizontalGap, int level, int currentLevel) {
        int nodeSize = 9;
        g.setColor(node.color != null ? node.color : Color.BLACK);
        g.fillOval((int) x - nodeSize / 2, (int) y - nodeSize / 2, nodeSize, nodeSize);
        int childrenSize = node.children.size();
        double totalWidth = (childrenSize - 1) * horizontalGap;
        double startX = x - totalWidth / 2;
        double childY = y + 100;
        for (int i = 0; i < childrenSize; i++) {
            TreeNode child = node.children.get(i);
            double childX = startX + i * horizontalGap;
            g.setColor(child.color);
            g.setStroke(new BasicStroke(2));
            g.drawLine((int) x, (int) y, (int) childX, (int) childY);
            double nextHorizontalGap = (currentLevel + 1 < levelGaps.length) ? levelGaps[currentLevel + 1] : levelGaps[levelGaps.length - 1];
            drawTree(g, child, childX, childY, nextHorizontalGap, level - 1, currentLevel + 1);
        }
    }

    // Метод для обновления уровней дерева
    public void updateLevels(int newLevels) {
        levels = newLevels + 1;
        levelGaps = calculateLevelGaps(levels);
        tree.updateLevels(newLevels);
        repaint();
    }
}

// Класс для представления шестиугольника Серпинского
class SierpinskiHexagon extends JPanel implements KeyListener {
    private int n; // Уровень глубины
    private final int WIDTH = 640; // Ширина панели
    private final int HEIGHT = 480; // Высота панели
    private final int RADIUS = 200; // Радиус шестиугольника
    private Color[] colors; // Цвета шестиугольника

    // Конструктор для инициализации шестиугольника
    public SierpinskiHexagon(int n, Color[] initialColors) {
        this.n = n;
        this.colors = initialColors;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        setFocusable(true);
        setBackground(Color.BLACK);
    }

    // Метод для установки уровня глубины
    public void setN(int n) {
        this.n = n;
    }

    // Метод для установки цветов
    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    // Метод для отрисовки компонента
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawHexagon(g, n, WIDTH / 2, HEIGHT / 2, RADIUS);
    }

    // Метод для отрисовки шестиугольника
    private void drawHexagon(Graphics g, int n, int cx, int cy, int radius) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];
        for (int i = 0; i < 6; i++) {
            xPoints[i] = (int) (cx + radius * Math.cos(i * Math.PI / 3));
            yPoints[i] = (int) (cy + radius * Math.sin(i * Math.PI / 3));
        }
        for (int i = 0; i < 6; i++) {
            drawTriangle(g, n, xPoints[i], yPoints[i], xPoints[(i + 1) % 6], yPoints[(i + 1) % 6], cx, cy, colors[i]);
        }
    }

    // Метод для отрисовки треугольника
    private void drawTriangle(Graphics g, int n, int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
        if (n == 0) {
            g.setColor(color);
            int[] xPoints = {x1, x2, x3};
            int[] yPoints = {y1, y2, y3};
            g.fillPolygon(xPoints, yPoints, 3);
            return;
        }
        int x12 = (x1 + x2) / 2;
        int y12 = (y1 + y2) / 2;
        int x23 = (x2 + x3) / 2;
        int y23 = (y2 + y3) / 2;
        int x31 = (x3 + x1) / 2;
        int y31 = (y3 + y1) / 2;
        g.setColor(Color.BLACK);
        int[] xPoints = {x31, x12, x23};
        int[] yPoints = {y31, y12, y23};
        g.fillPolygon(xPoints, yPoints, 3);
        drawTriangle(g, n - 1, x1, y1, x12, y12, x31, y31, color);
        drawTriangle(g, n - 1, x2, y2, x12, y12, x23, y23, color);
        drawTriangle(g, n - 1, x3, y3, x31, y31, x23, y23, color);
    }

    // Метод для обработки нажатия клавиши
    @Override
    public void keyPressed(KeyEvent e) {
        System.exit(0);
    }

    // Метод для обработки ввода символа
    @Override
    public void keyTyped(KeyEvent e) {}

    // Метод для обработки отпускания клавиши
    @Override
    public void keyReleased(KeyEvent e) {}
}

// Класс для представления панели настроек шестиугольника
class HexagonSettingsPanel extends JPanel {
    private JTextField levelField; // Поле для ввода уровня
    private SierpinskiHexagon hexagon; // Шестиугольник
    private TreePanel treePanel; // Панель дерева
    private JButton showTreeButton; // Кнопка для отображения дерева

    // Конструктор для инициализации панели настроек
    public HexagonSettingsPanel(SierpinskiHexagon hexagon, TreePanel treePanel) {
        this.hexagon = hexagon;
        this.treePanel = treePanel;
        setLayout(new BorderLayout());

        levelField = new JTextField(10);
        levelField.setText(String.valueOf(GlobalVariables.getLevels()));
        levelField.addActionListener(e -> {
            try {
                int newLevel = Integer.parseInt(levelField.getText());
                GlobalVariables.setLevels(newLevel);
                hexagon.setN(newLevel);
                hexagon.setColors(treePanel.getColorsForLevel(newLevel));
                hexagon.repaint();
                treePanel.updateLevels(newLevel);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        showTreeButton = new JButton("Show Tree");
        showTreeButton.addActionListener(e -> {
            JFrame treeFrame = new JFrame("Tree Visualization");
            treeFrame.setSize(800, 800);
            treeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JScrollPane scrollPane = new JScrollPane(treePanel);
            treeFrame.add(scrollPane);
            treeFrame.setVisible(true);
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Level: "));
        inputPanel.add(levelField);
        inputPanel.add(showTreeButton);

        add(inputPanel, BorderLayout.NORTH);
    }
}

// Основной класс для запуска приложения
public class TreeVisualization extends JFrame {
    // Конструктор для инициализации главного окна
    public TreeVisualization() {
        setTitle("Sierpinski Hexagon Settings");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        int initialLevel = Integer.parseInt(JOptionPane.showInputDialog("Enter the initial number of levels:"));
        GlobalVariables.setLevels(initialLevel);

        Tree tree = new Tree(1);
        Color[] initialColors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK, Color.CYAN};
        SierpinskiHexagon hexagon = new SierpinskiHexagon(GlobalVariables.getLevels(), initialColors);

        TreePanel treePanel = new TreePanel(tree, hexagon);
        JScrollPane scrollPane = new JScrollPane(treePanel);

        HexagonSettingsPanel hexagonSettingsPanel = new HexagonSettingsPanel(hexagon, treePanel);

        // Добавляем изображение
        JLabel imageLabel = new JLabel(new ImageIcon("C:/Users/kbelk/OneDrive/Рабочий стол/практика 2 курс/шестиугольник.png"));
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Создаем панель для настроек и изображения
        JPanel settingsAndImagePanel = new JPanel(new BorderLayout());
        settingsAndImagePanel.add(hexagonSettingsPanel, BorderLayout.NORTH);
        settingsAndImagePanel.add(imagePanel, BorderLayout.CENTER);

        // Добавляем все в главное окно
        add(settingsAndImagePanel, BorderLayout.NORTH);
        add(hexagon, BorderLayout.CENTER);

        JFrame treeFrame = new JFrame("Tree Visualization");
        treeFrame.setSize(800, 800);
        treeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        treeFrame.add(scrollPane);
        treeFrame.setVisible(true);
    }

    // Метод для запуска приложения
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TreeVisualization frame = new TreeVisualization();
            frame.setVisible(true);
        });
    }
}
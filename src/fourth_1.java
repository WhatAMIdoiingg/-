import javax.swing.*;
import java.awt.*;

public class fourth_1 {
    public static void main(String[] args) {
        // Создаем фрейм
        JFrame frame = new JFrame("Menu Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Создаем меню
        JMenuBar menuBar = new JMenuBar();

        // Создаем меню
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        // Добавляем пункты меню
        fileMenu.add(new JMenuItem("Open"));
        fileMenu.add(new JMenuItem("Save"));
        fileMenu.add(new JMenuItem("Exit"));

        editMenu.add(new JMenuItem("Cut"));
        editMenu.add(new JMenuItem("Copy"));
        editMenu.add(new JMenuItem("Paste"));

        // Добавляем меню в меню бар
        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        // Создаем панель для размещения меню
        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new BorderLayout());
        bottomRightPanel.add(menuBar, BorderLayout.NORTH);

        // Создаем панель и добавляем на нее компоненты
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Добавляем bottomRightPanel
        panel.add(bottomRightPanel, BorderLayout.SOUTH);

        // Добавляем панель в фрейм
        frame.add(panel);

        // Отображаем фрейм
        frame.setVisible(true);
    }
}
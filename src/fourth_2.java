import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class fourth_2 extends JFrame {
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openItem, saveAsItem, exitItem;
    private String currentText = ""; // Текущий текст в текстовом поле
    private boolean isModified = false;

    public fourth_2() {
        // Инициализация компонентов
        textArea = new JTextArea();
        fileChooser = new JFileChooser();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Файл");
        openItem = new JMenuItem("Открыть");
        saveAsItem = new JMenuItem("Сохранить как");
        exitItem = new JMenuItem("Выход");

        // Настройка фильтров для файлов
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
            }
            public String getDescription() {
                return "Текстовые файлы (*.txt)";
            }
        });

        // Настройка меню
        fileMenu.add(openItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Обработчики событий
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });

        saveAsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAsFile();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        // Добавление слушателя изменений к текстовому полю
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String newText = textArea.getText();
                if (!newText.equals(currentText)) {
                    currentText = newText;
                    //System.out.println("Текст изменён");
                    isModified = true;
                }
            }
        });

        // Настройка окна
        setTitle("Текстовый редактор");
        setSize(600, 400);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        add(new JScrollPane(textArea));
        setVisible(true);
    }

    private void openFile() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "Cp1251"))) {
                textArea.read(reader, null);
                currentText = textArea.getText(); // Обновление текущего текста
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Файл не найден", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveAsFile() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getParentFile(), file.getName() + ".txt");
            }
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "Cp1251"))) {
                textArea.write(writer);
                currentText = textArea.getText(); // Обновление текущего текста
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exit() {
        if (isModified) {
            int result = JOptionPane.showConfirmDialog(this, "Текст был изменён. \nСохранить изменения?", "Простой редактор", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                saveAsFile();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new fourth_2());
    }
}
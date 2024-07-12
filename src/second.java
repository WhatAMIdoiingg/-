import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class second {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);

        JButton button = new JButton("Press");
        button.setBounds(100, 170, 100, 30);
        frame.getContentPane().add(button);

        JTextField textField = new JTextField("Text");
        textField.setBounds(80, 100, 200, 30);
        frame.getContentPane().add(textField);

        JLabel label = new JLabel("Label 1");
        label.setBounds(10, 20, 100, 30);
        frame.getContentPane().add(label);

        JLabel label2 = new JLabel("Label 2");
        label2.setBounds(10, 100, 100, 30);
        frame.getContentPane().add(label2);
        frame.setTitle("Enter form");
        label.setText("Print your name");
        label2.setText("Name:");
        button.setText("Show");
        button.setToolTipText("Нажмите, чтобы увидеть приветствие");
        textField.setToolTipText("Введите ваше имя здесь");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Выводим MessageBox
                JOptionPane.showMessageDialog(frame, "Здравствуй " + textField.getText() + "!", "Приветствие", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setLayout(null);
        frame.setVisible(true);
    }
}
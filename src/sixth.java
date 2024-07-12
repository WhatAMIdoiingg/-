import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class sixth extends JFrame {
    private JLabel label;

    public sixth() {
        setTitle("Hover Effect Example");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        label = new JLabel("What have we done");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        label.setForeground(Color.BLACK);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setText("probability theory and mathematical statistics");
                label.setForeground(Color.RED);
                JOptionPane.showMessageDialog(sixth.this, "Don't get in, it'll kill you", "Message", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setText("What have we done");
                label.setForeground(Color.BLACK);
            }
        });

        getContentPane().add(label, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            sixth frame = new sixth();
            frame.setVisible(true);
        });
    }
}
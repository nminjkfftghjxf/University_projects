package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public StartFrame() {
        super("Bine ați venit!");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton clientButton = new JButton("Client");
        JButton personalButton = new JButton("Personal");
        JButton managerButton = new JButton("Manager");

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientMenuFrame clientMenu = new ClientMenuFrame();
                clientMenu.setVisible(true);
                dispose();
            }
        });

        personalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschide fereastra de autentificare pentru personal
                PersonalLoginFrame loginFrame = new PersonalLoginFrame();
                loginFrame.setVisible(true);
                dispose(); // Închide fereastra de start
            }
        });

        managerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschide fereastra de autentificare pentru manager
                ManagerLoginFrame loginFrame = new ManagerLoginFrame();
                loginFrame.setVisible(true);
                dispose(); // Închide fereastra de start
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(clientButton, constraints);

        constraints.gridy = 1;
        panel.add(personalButton, constraints);

        constraints.gridy = 2;
        panel.add(managerButton, constraints);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StartFrame startFrame = new StartFrame();
                startFrame.setVisible(true);
            }
        });
    }
}

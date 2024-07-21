package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerLoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public ManagerLoginFrame() {
        super("Autentificare Manager");
        setSize(800, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Parolă:");
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Autentificare");
        JButton backButton = new JButton("Back");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (email.equals("admin@restaurant.null") && password.equals("adminRestaurantMagic12")) {
                    openManagerFunctionality();
                } else {
                    JOptionPane.showMessageDialog(ManagerLoginFrame.this, "Autentificare eșuată. Verificați emailul și parola.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                StartFrame startFrame = new StartFrame();
                startFrame.setVisible(true);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(emailLabel, constraints);

        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(emailField, constraints);

        constraints.gridy = 1;
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        constraints.gridy = 3;
        panel.add(backButton, constraints);

        getContentPane().add(panel, BorderLayout.CENTER);
    
    }
    
    private void openManagerFunctionality() {
        
        ManagerFrame managerFrame = new ManagerFrame();
        managerFrame.setVisible(true);
        dispose(); 
    }
}

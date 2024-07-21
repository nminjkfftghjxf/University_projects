package view;

import javax.swing.*;

import dao.UtilizatorDAO;
import model.Utilizator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonalLoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public PersonalLoginFrame() {
        super("Autentificare Personal");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Nume utilizator:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Parolă:");
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Autentificare");
        JButton backButton = new JButton("Back");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                
                UtilizatorDAO utilizatorDAO = new UtilizatorDAO();
                Utilizator utilizator = utilizatorDAO.obtineUtilizatorDupaUsername(username);

                if (utilizator != null && utilizator.getParola().equals(password)) {
                    // Dacă autentificarea este corectă, deschideți fereastra principală pentru personal
                    //dispose();
                    PersonalFrame personalFrame = new PersonalFrame();
                    personalFrame.setVisible(true);
                } else {
                    // Dacă autentificarea eșuează, afișați un mesaj de eroare
                    JOptionPane.showMessageDialog(null, "Nume de utilizator sau parolă incorectă!", "Eroare de autentificare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Închide fereastra de autentificare pentru personal și deschide fereastra de start
                dispose();
                StartFrame startFrame = new StartFrame();
                startFrame.setVisible(true);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(usernameLabel, constraints);

        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(usernameField, constraints);

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
}

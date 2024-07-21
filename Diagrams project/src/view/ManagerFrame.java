package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public ManagerFrame() {
        super("Funcționalități Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500); 
        setLocationRelativeTo(null);
        initializeUI();
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JButton addEmployeeButton = new JButton("Adăugare Personal");
        JButton modifyMenuButton = new JButton("Modificare Meniu");
        JButton backButton = new JButton("Back");

        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployeeFrame addEmplyee = new AddEmployeeFrame();
                addEmplyee.setVisible(true);
                dispose();
            }
        });

        modifyMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuManagementFrame menuManage = new MenuManagementFrame();
                menuManage.setVisible(true);
                dispose();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ManagerLoginFrame managerLoginFrame = new ManagerLoginFrame();
                managerLoginFrame.setVisible(true);
            }
        });
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(addEmployeeButton, constraints);

        constraints.gridy = 1;
        panel.add(modifyMenuButton, constraints);
        
        constraints.gridy = 3;
        panel.add(backButton, constraints);
        

        getContentPane().add(panel, BorderLayout.CENTER);
    }
}

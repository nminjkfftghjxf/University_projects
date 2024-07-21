package view;

import dao.UtilizatorDAO;
import model.Utilizator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddEmployeeFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JList<String> employeeList;
    private DefaultListModel<String> employeeListModel;
    private UtilizatorDAO utilizatorDAO;

    public AddEmployeeFrame() {
        utilizatorDAO = new UtilizatorDAO();

        setTitle("Adăugare Personal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerFrame managerFrame = new ManagerFrame();
                managerFrame.setVisible(true);
                dispose(); 
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.NORTH);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        inputPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Parolă:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        inputPanel.add(passwordField, gbc);

        JButton addButton = new JButton("Adăugare Personal");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (!username.isEmpty() && !password.isEmpty()) {
                    Utilizator utilizator = new Utilizator();
                    utilizator.setUsername(username);
                    utilizator.setParola(password);
                    utilizatorDAO.adauga(utilizator);
                    employeeListModel.addElement(username);
                    usernameField.setText("");
                    passwordField.setText("");
                    actualizeazaListaPersonal();
                } else {
                    JOptionPane.showMessageDialog(AddEmployeeFrame.this,
                            "Vă rugăm să completați toate câmpurile", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel listPanel = new JPanel(new BorderLayout());
        JLabel listLabel = new JLabel("Personalul existent", JLabel.CENTER);
        listPanel.add(listLabel, BorderLayout.NORTH);

        employeeListModel = new DefaultListModel<>();
        employeeList = new JList<>(employeeListModel);
        JScrollPane scrollPane = new JScrollPane(employeeList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        JButton deleteButton = new JButton("Ștergere Personal");
        listPanel.add(deleteButton, BorderLayout.SOUTH);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedEmployee = employeeList.getSelectedValue();
                if (selectedEmployee != null) {
                    List<Utilizator> employees = utilizatorDAO.obtineTot();
                    for (Utilizator emp : employees) {
                        if (emp.getUsername().equals(selectedEmployee)) {
                            utilizatorDAO.sterge(emp.getIdUtilizator());
                            break;
                        }
                    }
                    employeeListModel.removeElement(selectedEmployee);
                } else {
                    JOptionPane.showMessageDialog(AddEmployeeFrame.this,
                            "Selectați un angajat pentru a-l șterge", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        

        add(inputPanel, BorderLayout.WEST);
        add(listPanel, BorderLayout.CENTER);
        
        add(inputPanel, BorderLayout.WEST);
        add(listPanel, BorderLayout.CENTER);
        
        actualizeazaListaPersonal();
    }

    private void actualizeazaListaPersonal() {
        employeeListModel.clear();
        List<Utilizator> employees = utilizatorDAO.obtineTot();
        for (Utilizator emp : employees) {
        	String userInfo = "ID: " + emp.getIdUtilizator() + " | Username: " + emp.getUsername() + " | Parolă: " + emp.getParola();
        	employeeListModel.addElement(userInfo);
        }
    }
}

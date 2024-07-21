package view;

import model.Produs;
import dao.ProdusDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuManagementFrame extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private JTextField numeField;
    private JTextField descriereField;
    private JTextField pretField;
    private JTextField tipField;
    private JTextField disponibilField;
    private DefaultListModel<String> listModel;
    private ProdusDAO produsDAO;
    private JList<String> listaProduse;
    
    public MenuManagementFrame() {
        setTitle("Management Meniu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500); 
        setLocationRelativeTo(null);
        initializeUI();
    }
    
    private void initializeUI() {
        produsDAO = new ProdusDAO();
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton backButton = createStyledButton("Back");
        JButton aperitiveButton = createStyledButton("Aperitive");
        JButton feluriPrincipaleButton = createStyledButton("Feluri principale");
        JButton bauturiSpirtoaseButton = createStyledButton("Bauturi spirtoase");
        JButton bauturiNespirtoaseButton = createStyledButton("Bauturi nespirtoase");

        buttonPanel.add(aperitiveButton);
        buttonPanel.add(feluriPrincipaleButton);
        buttonPanel.add(bauturiSpirtoaseButton);
        buttonPanel.add(bauturiNespirtoaseButton);
        buttonPanel.add(backButton);

        JPanel topButtonPanel = new JPanel(new BorderLayout());
        topButtonPanel.setBackground(Color.WHITE);
        JButton adaugareButton = createStyledButton("Adăugare");
        JButton modificareButton = createStyledButton("Modificare");
        JButton stergereButton = createStyledButton("Ștergere");

        JPanel buttonRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonRightPanel.add(adaugareButton);
        buttonRightPanel.add(modificareButton);
        buttonRightPanel.add(stergereButton);
        topButtonPanel.add(buttonRightPanel, BorderLayout.EAST);

        topButtonPanel.add(backButton, BorderLayout.WEST);

        listModel = new DefaultListModel<>();
        listaProduse = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaProduse);
        listaProduse.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane.setPreferredSize(new Dimension(200, 400));

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(topButtonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        adaugareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Adăugare Produs");
                JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

                panel.add(new JLabel("Nume:"));
                numeField = new JTextField();
                panel.add(numeField);

                panel.add(new JLabel("Descriere:"));
                descriereField = new JTextField();
                panel.add(descriereField);

                panel.add(new JLabel("Preț:"));
                pretField = new JTextField();
                panel.add(pretField);
                
                panel.add(new JLabel("Disponibil:"));
                disponibilField = new JTextField();
                panel.add(disponibilField);

                panel.add(new JLabel("Categorie:"));
                tipField = new JTextField();
                panel.add(tipField);

                JButton saveButton = new JButton("Salvează");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        salvareProdusInBazaDeDate();
                        frame.dispose();
                    }
                });
                panel.add(saveButton);

                frame.add(panel);
                frame.setSize(400, 300);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });

        modificareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaProduse.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedValue = listModel.getElementAt(selectedIndex);
                    // Extragem informațiile produsului din string-ul selectat (nume - descriere - preț RON)
                    String[] parts = selectedValue.split(" - ");
                    String nume = parts[0];
                    // Căutăm produsul după nume
                    Produs produs = produsDAO.obtineProdusDupaNume(nume);
                    if (produs != null) {
                        JFrame frame = new JFrame("Modificare Produs");
                        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

                        panel.add(new JLabel("Nume:"));
                        numeField = new JTextField(produs.getNume());
                        panel.add(numeField);

                        panel.add(new JLabel("Descriere:"));
                        descriereField = new JTextField(produs.getDescriere());
                        panel.add(descriereField);

                        panel.add(new JLabel("Preț:"));
                        pretField = new JTextField(String.valueOf(produs.getPret()));
                        panel.add(pretField);
                        
                        panel.add(new JLabel("Disponibil:"));
                        disponibilField = new JTextField(String.valueOf(produs.getDisponibil()));
                        panel.add(disponibilField);

                        panel.add(new JLabel("Categorie:"));
                        tipField = new JTextField(produs.getCategorie());
                        panel.add(tipField);

                        JButton saveButton = new JButton("Salvează");
                        saveButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                modificareProdusInBazaDeDate(produs.getIdProdus());
                                frame.dispose();
                            }
                        });
                        panel.add(saveButton);

                        frame.add(panel);
                        frame.setSize(400, 300);
                        frame.setVisible(true);
                        frame.setLocationRelativeTo(null);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați un produs pentru a-l modifica.");
                }
            }
        });

        stergereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaProduse.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedValue = listModel.getElementAt(selectedIndex);
                    // Extragem informațiile produsului din string-ul selectat (nume - descriere - preț RON)
                    String[] parts = selectedValue.split(" - ");
                    String nume = parts[0];
                    // Căutăm produsul după nume
                    Produs produs = produsDAO.obtineProdusDupaNume(nume);
                    if (produs != null) {
                        produsDAO.sterge(produs.getIdProdus());
                        listModel.remove(selectedIndex);
                        JOptionPane.showMessageDialog(null, "Produsul a fost șters cu succes.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați un produs pentru a-l șterge.");
                }
            }
        });
        
        aperitiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afiseazaProduseCategorie("aperitive");
            }
        });

        feluriPrincipaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afiseazaProduseCategorie("feluri principale");
            }
        });

        bauturiSpirtoaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afiseazaProduseCategorie("bauturi spirtoase");
            }
        });

        bauturiNespirtoaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afiseazaProduseCategorie("bauturi nespirtoase");
            }
        });
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ManagerFrame managerFrame = new ManagerFrame();
                managerFrame.setVisible(true);
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void salvareProdusInBazaDeDate() {
        try {
            String nume = numeField.getText();
            String descriere = descriereField.getText();
            double pret = Double.parseDouble(pretField.getText());
            String categorie = tipField.getText();
            String disponibil = disponibilField.getText();
            
            Produs produs = new Produs();
            produs.setNume(nume);
            produs.setDescriere(descriere);
            produs.setPret(pret);
            produs.setCategorie(categorie);
            produs.setDisponibil(Integer.valueOf(disponibil));
            
            produsDAO.adauga(produs);
            JOptionPane.showMessageDialog(null, "Produsul a fost adăugat cu succes!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Eroare: Prețul trebuie să fie un număr valid.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Eroare la adăugarea produsului: " + ex.getMessage());
        }
    }
    
    private void modificareProdusInBazaDeDate(int produsId) {
        try {
            String nume = numeField.getText();
            String descriere = descriereField.getText();
            double pret = Double.parseDouble(pretField.getText());
            String categorie = tipField.getText();
            String disponibil = disponibilField.getText();
            
            Produs produs = new Produs();
            produs.setIdProdus(produsId); // Setăm ID-ul produsului pentru a face update
            produs.setNume(nume);
            produs.setDescriere(descriere);
            produs.setPret(pret);
            produs.setCategorie(categorie);
            produs.setDisponibil(Integer.valueOf(disponibil));
            
            produsDAO.actualizeaza(produs);
            JOptionPane.showMessageDialog(null, "Produsul a fost modificat cu succes!");
            afiseazaProduseCategorie(categorie); // Actualizăm lista produselor
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Eroare: Prețul trebuie să fie un număr valid.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Eroare la modificarea produsului: " + ex.getMessage());
        }
    }

    private void afiseazaProduseCategorie(String categorie) {
        listModel.clear();
        List<Produs> produse = produsDAO.obtineProduseCategorie(categorie);
        for (Produs produs : produse) {
            listModel.addElement(produs.getNume() + " - " + produs.getDescriere() + " - " + produs.getPret() + " RON");
        }
    }
}

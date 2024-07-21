package view;

import model.Produs;
import dao.ProdusDAO;
import dao.ComandaDAO;
import model.Comanda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ClientMenuFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private DefaultListModel<String> listModel;
    private ProdusDAO produsDAO;
    private JList<String> listaProduse;
    private Map<Produs, Integer> cosProduse = new HashMap<>();

    public ClientMenuFrame() {
        setTitle("Meniu Client");
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
        JButton adaugaButton = createStyledButton("Adaugă");
        JButton informatiiButton = createStyledButton("Informații");
        JButton cosButton = createStyledButton("Coș");

        buttonPanel.add(aperitiveButton);
        buttonPanel.add(feluriPrincipaleButton);
        buttonPanel.add(bauturiSpirtoaseButton);
        buttonPanel.add(bauturiNespirtoaseButton);
        buttonPanel.add(backButton);
        buttonPanel.add(adaugaButton);
        buttonPanel.add(informatiiButton);
        buttonPanel.add(cosButton);

        JPanel topButtonPanel = new JPanel(new BorderLayout());
        topButtonPanel.setBackground(Color.WHITE);

        JPanel buttonRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonRightPanel.add(adaugaButton);
        buttonRightPanel.add(informatiiButton);
        buttonRightPanel.add(cosButton);
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
                StartFrame startFrame = new StartFrame();
                startFrame.setVisible(true);
            }
        });

        informatiiButton.addActionListener(new ActionListener() {
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
                        // Afișăm fereastra modală cu descrierea produsului
                        showProductDescription(produs.getDescriere());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați un produs pentru a vedea informațiile.");
                }
            }
        });

        adaugaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaProduse.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedValue = listModel.getElementAt(selectedIndex);
                    String[] parts = selectedValue.split(" - ");
                    String nume = parts[0];
                    Produs produs = produsDAO.obtineProdusDupaNume(nume);
                    if (produs != null) {
                        cosProduse.put(produs, cosProduse.getOrDefault(produs, 0) + 1);
                        JOptionPane.showMessageDialog(null, "Produsul a fost adăugat în coș.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați un produs pentru a-l adăuga în coș.");
                }
            }
        });

        cosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showShoppingCart();
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

    private void afiseazaProduseCategorie(String categorie) {
        listModel.clear();
        List<Produs> produse = produsDAO.obtineProduseCategorie(categorie);
        for (Produs produs : produse) {
            if (produs.getDisponibil() > 0) {
                listModel.addElement(produs.getNume() + " - " + produs.getPret() + " RON");
            }
        }
    }

    private void showProductDescription(String description) {
        // Creăm fereastra modală
        JDialog dialog = new JDialog();
        dialog.setTitle("Descriere Produs");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(null);

        // Cream o eticheta pentru descrierea produsului
        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setLineWrap(true); // Textul va trece pe mai multe linii dacă este prea lung
        descriptionArea.setWrapStyleWord(true); // Textul se va încadra între cuvinte întregi

        // Adăugăm eticheta într-un JScrollPane pentru a permite derularea în cazul în care textul este prea lung
        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        dialog.add(scrollPane, BorderLayout.CENTER);

        // Facem fereastra modală vizibilă
        dialog.setVisible(true);
    }

    private void showShoppingCart() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Coș de cumpărături");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(1000, 800);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel productsPanel = new JPanel(new GridLayout(cosProduse.size(), 4));
        for (Map.Entry<Produs, Integer> entry : cosProduse.entrySet()) {
            Produs produs = entry.getKey();
            int cantitate = entry.getValue();
            
            JLabel nameLabel = new JLabel("Nume: " + produs.getNume() + " ");
            JLabel priceLabel = new JLabel("Pret: " + produs.getPret() * cantitate + " RON");
            JLabel textLabel = new JLabel("Cantitate: ");
            JLabel quantityLabel = new JLabel(Integer.toString(cantitate));
            JButton addButton = new JButton("+");
            JButton removeButton = new JButton("-");

            productsPanel.add(nameLabel);
            productsPanel.add(priceLabel);
            productsPanel.add(textLabel);
            productsPanel.add(quantityLabel);
            productsPanel.add(addButton);
            productsPanel.add(removeButton);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int currentQuantity = Integer.parseInt(quantityLabel.getText());
                    currentQuantity++;
                    quantityLabel.setText(Integer.toString(currentQuantity));
                    priceLabel.setText("Pret: " + produs.getPret() * currentQuantity + " RON");
                    cosProduse.put(produs, currentQuantity);
                }
            });

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int currentQuantity = Integer.parseInt(quantityLabel.getText());
                    if (currentQuantity > 1) {
                        currentQuantity--;
                        quantityLabel.setText(Integer.toString(currentQuantity));
                        priceLabel.setText("Pret: " + produs.getPret() * currentQuantity + " RON");
                        cosProduse.put(produs, currentQuantity);
                    } else {
                        // Dacă cantitatea devine 0, eliminăm produsul din coș
                        cosProduse.remove(produs);
                        productsPanel.remove(nameLabel);
                        productsPanel.remove(priceLabel);
                        productsPanel.remove(quantityLabel);
                        productsPanel.remove(addButton);
                        productsPanel.remove(removeButton);
                        productsPanel.revalidate();
                        productsPanel.repaint();
                    }
                }
            });
        }

        JButton placeOrderButton = new JButton("Plasează comanda");
        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder();
                dialog.dispose(); // Închidem fereastra de coș
            }
        });

        panel.add(new JScrollPane(productsPanel), BorderLayout.CENTER);
        panel.add(placeOrderButton, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void placeOrder() {
        if (cosProduse.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Coșul este gol.");
            return;
        }

        ComandaDAO comandaDAO = new ComandaDAO();
        double suma = 0;
        StringBuilder produseBuilder = new StringBuilder();
        
        for (Map.Entry<Produs, Integer> entry : cosProduse.entrySet()) {
            Produs produs = entry.getKey();
            int cantitate = entry.getValue();
            suma += produs.getPret() * cantitate;
            produseBuilder.append(produs.getNume()).append(" x ").append(cantitate).append(", ");
        }
        
        // Eliminăm ultima virgulă și spațiu
        if (produseBuilder.length() > 0) {
            produseBuilder.setLength(produseBuilder.length() - 2);
        }
        
        Comanda comanda = new Comanda();
        comanda.setProduse(produseBuilder.toString());
        comanda.setSuma(suma);
        
        comandaDAO.adauga(comanda);
        cosProduse.clear();
        JOptionPane.showMessageDialog(null, "Comanda a fost plasată cu succes.");
    }
}

package view;

import javax.swing.*;

import dao.ChitantaDAO;
import dao.ComandaDAO;
import dao.ProdusDAO;
import model.Chitanta;
import model.Comanda;
import model.Produs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class PersonalFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JList<String> listaComenzi;
    private DefaultListModel<String> listModel;
    
    public PersonalFrame() {
        super("Pagina Personal");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeUI();
        afiseazaComenzi();
    }

    private void initializeUI() {
    	JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton backButton = createStyledButton("Back");
        JButton statusButton = createStyledButton("Actualizare status");
        JButton disponibilitateButton = createStyledButton("Actualizare disponibilitate");
        JButton preluareButton = createStyledButton("Preia comanda");
        JButton confirmareButton = createStyledButton("Confirma comanda");
        JButton chitantaButton = createStyledButton("Vizualizari chitante");

        buttonPanel.add(statusButton);
        buttonPanel.add(disponibilitateButton);
        buttonPanel.add(preluareButton);
        buttonPanel.add(confirmareButton);
        buttonPanel.add(chitantaButton);
        buttonPanel.add(backButton);

        JPanel topButtonPanel = new JPanel(new BorderLayout());
        topButtonPanel.setBackground(Color.WHITE);
        
        JPanel buttonRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topButtonPanel.add(buttonRightPanel, BorderLayout.EAST);

        topButtonPanel.add(backButton, BorderLayout.WEST);

        listModel = new DefaultListModel<>();
        listaComenzi = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaComenzi);
        listaComenzi.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane.setPreferredSize(new Dimension(200, 400));

        mainPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(topButtonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                PersonalLoginFrame personalLoginFrame = new PersonalLoginFrame();
                personalLoginFrame.setVisible(true);
            }
        });
       
        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obținem comanda selectată
                int selectedIndex = listaComenzi.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedCommand = listaComenzi.getSelectedValue();
                    // Creăm pop-up-ul pentru actualizarea statusului comenzii
                    showStatusUpdatePopup(selectedCommand);
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați o comandă pentru a actualiza statusul.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        disponibilitateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obținem lista de produse și disponibilitatea lor din baza de date
                List<Produs> produse = ProdusDAO.obtineToateProdusele();
                
                // Creăm un obiect JFrame pentru pop-up
                JFrame popupFrame = new JFrame("Actualizare disponibilitate");
                popupFrame.setSize(400, 300);
                popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                popupFrame.setLocationRelativeTo(null);

                // Creăm un model de listă pentru afișarea produselor
                DefaultListModel<String> produsListModel = new DefaultListModel<>();
                // Adăugăm numele și disponibilitatea fiecărui produs în modelul de listă
                for (Produs produs : produse) {
                    produsListModel.addElement(produs.getNume() + " - Disponibil: " + produs.getDisponibil());
                }
                // Creăm o listă care să utilizeze modelul de listă creat
                JList<String> listaProduse = new JList<>(produsListModel);
                // Adăugăm un panou de derulare pentru lista de produse
                JScrollPane produsScrollPane = new JScrollPane(listaProduse);

                // Creăm o casetă de text pentru a afișa și modifica disponibilitatea
                JTextField disponibilitateTextField = new JTextField();
                disponibilitateTextField.setPreferredSize(new Dimension(150, 30));
                
                // Creăm un buton pentru a salva modificările în baza de date
                JButton salvareButton = createStyledButton("Salvare");

                // Adăugăm acțiune pentru butonul de salvare
                salvareButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Obținem produsul selectat din listă
                        int selectedIndex = listaProduse.getSelectedIndex();
                        if (selectedIndex != -1) {
                            // Obținem produsul selectat
                            Produs produsSelectat = produse.get(selectedIndex);
                            // Obținem noua disponibilitate introdusă de utilizator
                            int nouaDisponibilitate = Integer.parseInt(disponibilitateTextField.getText());
                            // Actualizăm disponibilitatea produsului în baza de date
                            ProdusDAO.actualizareDisponibil(produsSelectat.getIdProdus(), nouaDisponibilitate);
                            // Afișăm un mesaj de confirmare
                            JOptionPane.showMessageDialog(null, "Disponibilitatea produsului a fost actualizată cu succes.", "Actualizare disponibilitate", JOptionPane.INFORMATION_MESSAGE);
                            // Închidem pop-up-ul
                            popupFrame.dispose();
                        } else {
                            // Afișăm un mesaj de eroare dacă nu este selectat niciun produs
                            JOptionPane.showMessageDialog(null, "Selectați un produs pentru a actualiza disponibilitatea.", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                // Creăm un panou pentru a organiza componentele
                JPanel contentPanel = new JPanel(new BorderLayout());
                contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                // Adăugăm lista de produse și caseta de text în partea stângă a panoului
                contentPanel.add(produsScrollPane, BorderLayout.CENTER);
                contentPanel.add(disponibilitateTextField, BorderLayout.EAST);
                // Adăugăm butonul de salvare în partea de jos a panoului
                contentPanel.add(salvareButton, BorderLayout.SOUTH);

                // Adăugăm panoul de conținut în pop-up
                popupFrame.add(contentPanel);

                // Facem pop-up-ul vizibil
                popupFrame.setVisible(true);
            }
        });
        
        preluareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaComenzi.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Obținem comanda selectată
                    String selectedCommand = listaComenzi.getSelectedValue();
                    String[] parts = selectedCommand.split(", ");
                    int idComanda = Integer.parseInt(parts[0]);
                    
                    // Creăm un pop-up pentru actualizarea timpului estimativ de finalizare
                    JFrame popupFrame = new JFrame("Actualizare timp estimativ");
                    popupFrame.setSize(400, 200);
                    popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    popupFrame.setLocationRelativeTo(null);
                    
                    // Creăm un panel pentru organizarea componentelor pop-up-ului
                    JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    
                    // Adăugăm un câmp text pentru introducerea noului timp estimativ
                    JTextField timpEstimativField = new JTextField(10);
                    panel.add(new JLabel("Timp estimativ (minute):"), BorderLayout.WEST);
                    panel.add(timpEstimativField, BorderLayout.CENTER);
                    
                    // Creăm un buton pentru confirmarea modificărilor
                    JButton confirmButton = new JButton("Confirmare");
                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Obținem noul timp estimativ introdus de utilizator
                            int timpEstimativNou = Integer.parseInt(timpEstimativField.getText());
                            
                            // Actualizăm timpul estimativ în baza de date
                            ComandaDAO comandaDAO = new ComandaDAO();
                            comandaDAO.actualizareTimpEstimativ(idComanda, timpEstimativNou);
                            
                            // Actualizăm statusul comenzii în "in pregatire"
                            comandaDAO.actualizareStatus(idComanda, "in pregatire");
                            
                            // Actualizăm lista de comenzi afișate
                            afiseazaComenzi();
                            
                            // Închidem pop-up-ul
                            popupFrame.dispose();
                        }
                    });
                    panel.add(confirmButton, BorderLayout.SOUTH);
                    
                    // Adăugăm panel-ul la pop-up
                    popupFrame.add(panel);
                    
                    // Facem pop-up-ul vizibil
                    popupFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați o comandă pentru a o prelua.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        confirmareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listaComenzi.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Obținem comanda selectată
                    String selectedCommand = listaComenzi.getSelectedValue();
                    String[] parts = selectedCommand.split(", ");
                    int idComanda = Integer.parseInt(parts[0]);
                    double sumaTotala = Double.parseDouble(parts[parts.length-1]); 
                    
                    // Creăm un pop-up cu trei butoane
                    JFrame popupFrame = new JFrame("Confirmare comanda");
                    popupFrame.setSize(400, 200);
                    popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    popupFrame.setLocationRelativeTo(null);

                    JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
                    JButton cashButton = createStyledButton("Cash");
                    JButton cardButton = createStyledButton("Card");
                    JButton confirmButton = createStyledButton("Confirm");

                    final String[] metodaPlata = {null}; // Array pentru a permite modificarea metodei de plată

                    cashButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            metodaPlata[0] = "cash";
                            ChitantaDAO chitantaDAO = new ChitantaDAO();
                            chitantaDAO.actualizareMetodaPlata(idComanda, "cash");
                        }
                    });

                    cardButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            metodaPlata[0] = "card";
                            ChitantaDAO chitantaDAO = new ChitantaDAO();
                            chitantaDAO.actualizareMetodaPlata(idComanda, "card");
                        }
                    });

                    confirmButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (metodaPlata[0] == null) {
                                JOptionPane.showMessageDialog(popupFrame, "Selectați o metodă de plată.", "Eroare", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Generăm numărul chitantei
                            String numarChitanta = "R" + (int)(Math.random() * 900 + 100);

                            // Creăm noua chitanță
                            Chitanta chitanta = new Chitanta();
                            chitanta.setIdComanda(idComanda);
                            chitanta.setMetodaPlata(metodaPlata[0]);
                            chitanta.setSuma(sumaTotala);
                            chitanta.setNumarChitanta(numarChitanta);

                            // Adăugăm chitanța în baza de date
                            ChitantaDAO chitantaDAO = new ChitantaDAO();
                            chitantaDAO.adauga(chitanta);
                            
                            ComandaDAO comandaDAO = new ComandaDAO();
                            comandaDAO.actualizareStatus(idComanda, "servita");

                            // Închidem pop-up-ul
                            popupFrame.dispose();

                            JOptionPane.showMessageDialog(null, "Comanda a fost confirmată cu succes.", "Confirmare comanda", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });

                    panel.add(cashButton);
                    panel.add(cardButton);
                    panel.add(confirmButton);

                    popupFrame.add(panel);
                    popupFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați o comandă pentru a o confirma.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        chitantaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChitantaDAO chitantaDAO = new ChitantaDAO();
                List<Chitanta> chitante = chitantaDAO.obtineTot();

                JFrame popupFrame = new JFrame("Vizualizari chitante");
                popupFrame.setSize(400, 300);
                popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                popupFrame.setLocationRelativeTo(null);

                DefaultListModel<String> chitanteListModel = new DefaultListModel<>();
                for (Chitanta chitanta : chitante) {
                    chitanteListModel.addElement("ID: " + chitanta.getIdChitanta() + ", Comanda ID: " + chitanta.getIdComanda() + ", Metoda: " + chitanta.getMetodaPlata() + ", Suma: " + chitanta.getSuma() + ", Numar: " + chitanta.getNumarChitanta());
                }
                JList<String> listaChitante = new JList<>(chitanteListModel);
                JScrollPane chitanteScrollPane = new JScrollPane(listaChitante);

                JPanel contentPanel = new JPanel(new BorderLayout());
                contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                contentPanel.add(chitanteScrollPane, BorderLayout.CENTER);

                popupFrame.add(contentPanel);
                popupFrame.setVisible(true);
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
    
    private void afiseazaComenzi() {
        listModel.clear(); // Curăță lista înainte de a adăuga comenzile noi
        ComandaDAO comandaDAO = new ComandaDAO();
        List<Comanda> comenzi = comandaDAO.obtineTot();
        for (Comanda comanda : comenzi) {
            listModel.addElement(comanda.toString()); // Adaugă fiecare comandă în listă
        }
    }
    
    private void showStatusUpdatePopup(String selectedCommand) {
        // Creăm un obiect JFrame pentru pop-up
        JFrame popupFrame = new JFrame("Actualizare status comandă");
        popupFrame.setSize(400, 200);
        popupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        popupFrame.setLocationRelativeTo(null);

        // Creăm butoanele pentru pop-up
        JButton inAsteptareButton = createStyledButton("În așteptare");
        JButton inPreparareButton = createStyledButton("În preparare");
        JButton servitaButton = createStyledButton("Servită");
        JButton stergereButton = createStyledButton("Ștergere");

        // Adăugăm acțiuni pentru fiecare buton
        inAsteptareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	int selectedIndex = listaComenzi.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedCommand = listaComenzi.getSelectedValue();
                    
                    int idComanda = Integer.parseInt(selectedCommand.split(", ")[0]);
                    
                    ComandaDAO comandaDAO = new ComandaDAO();
                    comandaDAO.actualizareStatus(idComanda, "In asteptare");
                    
                    afiseazaComenzi();
                    
                    popupFrame.dispose();
                    
                    JOptionPane.showMessageDialog(null, "Statusul comenzii a fost actualizat cu succes în \"În așteptare\".", "Actualizare status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați o comandă pentru a actualiza statusul.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        inPreparareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	int selectedIndex = listaComenzi.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedCommand = listaComenzi.getSelectedValue();
                    
                    int idComanda = Integer.parseInt(selectedCommand.split(", ")[0]);
                    
                    ComandaDAO comandaDAO = new ComandaDAO();
                    comandaDAO.actualizareStatus(idComanda, "In preparare");
                    
                    afiseazaComenzi();
                    
                    popupFrame.dispose();
                    
                    JOptionPane.showMessageDialog(null, "Statusul comenzii a fost actualizat cu succes în \"În așteptare\".", "Actualizare status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați o comandă pentru a actualiza statusul.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        servitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            	int selectedIndex = listaComenzi.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedCommand = listaComenzi.getSelectedValue();
                    
                    int idComanda = Integer.parseInt(selectedCommand.split(", ")[0]);
                    
                    ComandaDAO comandaDAO = new ComandaDAO();
                    comandaDAO.actualizareStatus(idComanda, "servita");
                    comandaDAO.actualizareTimpEstimativ(idComanda, 0);
                    
                    afiseazaComenzi();
                    
                    popupFrame.dispose();
                    
                    JOptionPane.showMessageDialog(null, "Statusul comenzii a fost actualizat cu succes în \"În așteptare\".", "Actualizare status", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați o comandă pentru a actualiza statusul.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        stergereButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obținem comanda selectată
                int selectedIndex = listaComenzi.getSelectedIndex();
                if (selectedIndex != -1) {
                    // Afisam un dialog de confirmare pentru stergerea comenzii
                    int option = JOptionPane.showConfirmDialog(null, "Sigur doriți să ștergeți comanda selectată?", "Confirmare ștergere", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        // Obținem ID-ul comenzii din textul selectat
                        int idComanda = Integer.parseInt(listaComenzi.getSelectedValue().split(", ")[0]); 
                        // Ștergem comanda din baza de date
                        ComandaDAO comandaDAO = new ComandaDAO();
                        comandaDAO.sterge(idComanda);
                        
                        afiseazaComenzi();
                        
                        JOptionPane.showMessageDialog(null, "Comanda a fost ștearsă cu succes.", "Ștergere comandă", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selectați o comandă pentru a o șterge.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Creăm un panel pentru a organiza butoanele
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.add(inAsteptareButton);
        buttonPanel.add(inPreparareButton);
        buttonPanel.add(servitaButton);
        buttonPanel.add(stergereButton);

        // Adăugăm panel-ul la pop-up
        popupFrame.add(buttonPanel, BorderLayout.CENTER);

        // Facem pop-up-ul vizibil
        popupFrame.setVisible(true);
    }
    
    
}

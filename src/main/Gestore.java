package main;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class Gestore extends JPanel {

	public Gestore(QueryExecuter exe, MainFrame frame) {
		
		this.setLayout(new BorderLayout());
		
		this.exe = exe;
		this.frame = frame;
		this.southPanel = new JPanel();
		this.add(southPanel, BorderLayout.SOUTH);
		
		buttons = new ArrayList<>();
		
		JButton button = new JButton("Achievement mai sbloccati");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				southPanel.removeAll();
				
				area.setText("Achievements non sbloccati:\n");
				
				try {
					ResultSet rs = exe.achievementNeverUnlocked();
					
					while(rs.next()) {
						area.setText(area.getText() + rs.getString("nome") + " " + rs.getInt("idGioco") + " " + rs.getString("descrizione") + "\n");
					}
				} catch (SQLException e1) {
					frame.getErrorPane().showMessageDialog(frame, "Errore.......");
					e1.printStackTrace();
				}
				
				frame.loadPanel(Gestore.this);
			}
		});
		buttons.add(button);
		
		button = new JButton("Giocatori con winrate maggiore del 50%");
		buttons.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				southPanel.removeAll();
				
				try {
					ResultSet rs = exe.giocatoriWinrateMoreThan50();
					
					area.setText("Lista giocatori che hanno un winrate maggiore del 50%:\n");
					
					while(rs.next()) {
						area.setText(area.getText() + rs.getString("username") + "\n");
					}
					
				} catch (SQLException e1) {
					frame.getErrorPane().showMessageDialog(frame, "Errore nella visualizzazione della classifica");
					e1.printStackTrace();
				}
				
				frame.loadPanel(Gestore.this);
			}
		});
		
		
		button = new JButton("Utenti per gioco");
		buttons.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				area.setText("");
				
				southPanel.removeAll();
				
				southPanel.add(new JLabel("ID Gioco: "));
				JTextField nomeGioco = new JTextField(5);
				southPanel.add(nomeGioco);
				
				JButton button = new JButton("Conta");
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							area.setText(Integer.toString(exe.usersForGame(Integer.parseInt(nomeGioco.getText()))));
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							frame.getErrorPane().showMessageDialog(frame, "Errore.....");
							e1.printStackTrace();
						}
					}
				});
				southPanel.add(button);
				
				frame.loadPanel(Gestore.this);
			}
		});
		
		
		
		button = new JButton("Lista match vinti da un team");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				southPanel.removeAll();
				
				southPanel.add(new JLabel("Nome team: "));
				JTextField nomeTeam = new JTextField(5);
				southPanel.add(nomeTeam);
				
				JButton button = new JButton("Search");
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						area.setText("Lista match vinti da un team(idMatch, idGioco, avversario, durata, data ): " + nomeTeam.getText() + "\n");
						
						try {
							ResultSet rs = exe.showMatchWon(nomeTeam.getText());
							
							while(rs.next()) {
								
								String t = "";
								String t1 = rs.getString("team1");
								String t2 = rs.getString("team2");
								String tv = rs.getString("teamVincitore");
								if(!t1.equals(tv))
									t = t1;
								else
									t = t2;
								
								String match = rs.getString("idMatch") + " " + rs.getString("idGioco") + " " + t + " " + rs.getString("data") + " " + rs.getString("durata");
								area.setText(area.getText() + match + "\n");
								//area.setText(area.getText() + rs.getString("idMatch") + " " + rs.getString("idGioco") + " " + rs.getString("team1") + " " + rs.getString("team2") + "\n");
							}
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
					}		
				});
				southPanel.add(button);
				
				
				frame.loadPanel(Gestore.this);
			}
		});
		buttons.add(button);
		
		
		
		button = new JButton("Lista giochi con rapporto possedimento/ore");
		buttons.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				southPanel.removeAll();
				
				area.setText("Lista giochi con rapporto possedimento/ore: \n");
				
				try {
					ResultSet rs = exe.showListGiochiRapportoPossedimentoOreGiocate();
					
					while(rs.next()) {
						String gioco = rs.getString("G.idGioco") + " " + rs.getString("G.nome") + " " + rs.getString("Rapporto");
						area.setText(area.getText() + gioco + "\n");
					}
				} catch (SQLException e1) {
					frame.getErrorPane().showMessageDialog(frame, "Errore.....");
					e1.printStackTrace();
				}
				
				
				frame.loadPanel(Gestore.this);
			}	
		});
		
		
		
		button = new JButton("ScoreBoard teams");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				southPanel.removeAll();
				
				try {
					ResultSet rs = exe.showScoreBoardTeams();
					
					area.setText("ScoreBoard Teams: (NomeTeam, Vittorie)\n");
					
					while(rs.next()) {
						String team = rs.getString("T.nome") + " " + rs.getString("Vittorie");
						area.setText(area.getText() + team + "\n");
					}
					
				} catch (SQLException e1) {
					frame.getErrorPane().showMessageDialog(Gestore.this, "Errore....");
					e1.printStackTrace();
				}
				
				frame.loadPanel(Gestore.this);
			}
		});
		buttons.add(button);
		
		
		button = new JButton("Stampa informazioni publishers");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				southPanel.removeAll();
				
				area.setText("Lista dei publisher: (idPublisher, nome, sedeLegale, numeroGiochi)\n");
				
				try {
					ResultSet rs = exe.showPublisher();
					
					while(rs.next()) {
						String publisher = rs.getString("idPublisher") + " " + rs.getString("nome") + " "
								+ rs.getString("sedeLegale") + " " + rs.getString("numberOfGames") + "\n";
						
						area.setText(area.getText() + publisher);
					}
					
				} catch (SQLException e1) {
					frame.getErrorPane().showMessageDialog(frame, "Errore nel caricamento dei publisher");
					e1.printStackTrace();
				}
				

			}
		});
		buttons.add(button);
		
		
		button = new JButton("Stampa developer per ruolo di sviluppo");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				area.setText("");
				
				southPanel.removeAll();
				
				southPanel.add(new JLabel("Ruolo: "));
				JTextField ruolo = new JTextField(5);
				southPanel.add(ruolo);
				
				southPanel.add(new JLabel("idGioco: "));
				JTextField idGioco = new JTextField(5);
				southPanel.add(idGioco);
				
				JButton search = new JButton("Search");
				search.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							ResultSet rs = exe.showDeveloperForRole(ruolo.getText(), Integer.parseInt(idGioco.getText()));
						
							area.setText("Lista dei developer per lo sviluppo del gioco con id " + idGioco.getText() + " con ruolo " + ruolo.getText() + ":\n");
							
							while(rs.next()) {		
								String developer = rs.getString("D.nome") + "\n"; 
								area.setText(area.getText() + developer);
							}
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							frame.getErrorPane().showMessageDialog(frame, "Errore......");
							e1.printStackTrace();
						}
					}
				});
				southPanel.add(search);
				
				frame.loadPanel(Gestore.this);
				
			}
		});
		buttons.add(button);
		
		
		
		
		
		//BUTTON PANEL
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(10, 1));
		this.add(buttonPanel, BorderLayout.EAST);
		
		for(JButton b : buttons) {
			b.setBorder(new EtchedBorder(60));
			buttonPanel.add(b);
		}
		
		this.area = new JTextArea();
		JScrollPane scroll = new JScrollPane(area);
		this.add(scroll);
	}
	
	private List<JButton> buttons;
	private JTextArea area;
	private JPanel southPanel;
	private QueryExecuter exe;
	private MainFrame frame;
}

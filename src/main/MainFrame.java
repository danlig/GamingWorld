package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.*;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 454228441946425534L;
	
	public MainFrame(QueryExecuter exe) {
		this.exe = exe;
		error = new JOptionPane();
		
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {				
				System.out.println("ciao ciao");
				MainFrame.this.shutDown();
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}	
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		
		login = createLoginPanel();
		this.gestore = new Gestore(exe, this);
		
		
		mod = createModPanel();
		currentPanel = mod;
		this.add(currentPanel);
		
		registration = createRegistrationPanel();
		
		gameListPanel = new GameListPanel();
		gameListWithFriendsPanel = new GameListWithFriends();
		createBookMarkPanel = new CreateBookMarkPanel();
		friendsWithGamePanel = new FriendsListWithGamePanel();
		reportPanel = new ReportPanel();
		deleteFriendPanel = new FriendPanel();
		
		profile  = new ProfilePanel();
		
		//TO REMOVE
//		selectedUsername = "antonio";
//		loadPanel(profile);
		
		this.setVisible(true);
	}
	
	
	public class BackToProfileListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainFrame.this.loadPanel(profile);
		}
	}
	
	public class BackToLoginListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainFrame.this.loadPanel(login);
			//System.out.println("HELLO WORLDOOOO");
		}
	}
	
	public class ShutdownListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainFrame.this.shutDown();
		}
	}
	
	public class ChangeButtonPanel extends JButton {
		public ChangeButtonPanel(JPanel panel, String name) {
			super(name);
			
			this.panel = panel;
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MainFrame.this.loadPanel(panel);
				}
			});
		}
		
		private JPanel panel;
	}
	
	public abstract class LoadablePanel extends JPanel{
		public abstract void load();
	}
	
	public class ProfilePanel extends LoadablePanel {
		public ProfilePanel() {
			this.setLayout(new BorderLayout());
			
			JPanel northPanel = new JPanel();
			this.add(northPanel, BorderLayout.NORTH);
			
			username = new JLabel();
			northPanel.add(username);
			
			counterFriends = new JLabel();
			northPanel.add(counterFriends);
			
			winrate = new JLabel();
			northPanel.add(winrate);
			
			JPanel southPanel = new JPanel();
			this.add(southPanel, BorderLayout.SOUTH);
			
			JButton deleteAccount = new JButton("Cancella Account");
			southPanel.add(deleteAccount);
			deleteAccount.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					try {
						exe.deleteAccount(selectedUsername);
						MainFrame.this.loadPanel(login);
					} catch (SQLException e1) {
						error.showMessageDialog(MainFrame.this, "Errore nel tentativo di cancellare l'account");
						e1.printStackTrace();
					}
				}
			});
			
			JButton exitButton = new JButton("Esci");
			exitButton.addActionListener(new BackToLoginListener());
			southPanel.add(exitButton);
			
			JPanel panel = new JPanel();
			this.add(panel);
			
			ChangeButtonPanel gameList = new ChangeButtonPanel(gameListPanel, "Giochi per categoria");
			panel.add(gameList);
			
			ChangeButtonPanel gameListOwned = new ChangeButtonPanel(gameListWithFriendsPanel, "Giochi in comune con amico");
			panel.add(gameListOwned);
			
			ChangeButtonPanel friendList = new ChangeButtonPanel(friendsWithGamePanel, "Amici con un gioco");
			panel.add(friendList);
			
			ChangeButtonPanel bookmark = new ChangeButtonPanel(createBookMarkPanel, "Crea Bookmark");
			panel.add(bookmark);
			
			ChangeButtonPanel report = new ChangeButtonPanel(reportPanel, "Lista report");
			panel.add(report);
			
			ChangeButtonPanel deleteFriend = new ChangeButtonPanel(deleteFriendPanel,"Sezione Amici");
			panel.add(deleteFriend);
		}
		
		public void load() {
			counterFriends.setText("Amici: " + Integer.toString(exe.countAmici(selectedUsername)));
			try {
				winrate.setText("WinRate: " + Double.toString(exe.winRate(selectedUsername)));
			} catch (SQLException e) {
				error.showMessageDialog(MainFrame.this, "Errore nel calcolo del winrate");
				e.printStackTrace();
			}
			
			username.setText("Username: " + selectedUsername);
		}
		
		private JLabel counterFriends;
		private JLabel winrate;
		private JLabel username;
	}
	
//	public class CreateFriend extends LoadablePanel {
//
//		public CreateFriend() {
//			
//			this.setLayout(new BorderLayout());
//			
//			JPanel panel = new JPanel();
//			this.add(panel);
//			
//			panel.add(new JLabel("Friend Username: "));
//			
//			friendUsername = new JTextField(5);
//			panel.add(friendUsername);
//			
//			JButton backButton = new JButton("BACK");
//			backButton.addActionListener(new BackToProfileListener());
//			this.add(backButton, BorderLayout.SOUTH);
//		}
//		
//		@Override
//		public void load() {
//			
//		}
//		
//		private JTextField friendUsername;
//	}
	
	public class FriendPanel extends LoadablePanel {
		public FriendPanel() {
			
			List<String> friends = new ArrayList<>();
			
			this.setLayout(new GridLayout(10, 1));
			
			JPanel deleteFriendPanel = new JPanel();
			this.add(deleteFriendPanel);
			
			JLabel label = new JLabel("Cancella amicizia: ");
			deleteFriendPanel.add(label);
			
			combo = new JComboBox();
			deleteFriendPanel.add(combo);
			
			JButton cancella = new JButton("Cancella");
			cancella.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						exe.deleteFriend(selectedUsername, (String)combo.getSelectedItem());
						
						MainFrame.this.loadPanel(FriendPanel.this);
					} catch (SQLException e1) {
						error.showMessageDialog(MainFrame.this, "Errore nella cancellazione dell'amicizia");
						e1.printStackTrace();
					}					
					
				}
			});
			deleteFriendPanel.add(cancella);
			
			
			JPanel createFriend = new JPanel();
			this.add(createFriend);
			
			label = new JLabel("Crea amicizia: ");
			createFriend.add(label);
			
			label = new JLabel("username: ");
			createFriend.add(label);
			
			JTextField friendUsername = new JTextField(5);
			createFriend.add(friendUsername);
			
			JButton create = new JButton("Crea");
			create.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String usernameF = friendUsername.getText();
					try {
						exe.createFriend(selectedUsername, usernameF);
						loadPanel(FriendPanel.this);
					} catch (SQLException e1) {
						error.showMessageDialog(MainFrame.this, "Errore durante la creazione dell'amicizia!!!");
					}
				}
				
			});
			createFriend.add(create);
			
			JPanel backPanel = new JPanel();
			this.add(backPanel);
			
			JButton backButton = new JButton("BACK");
			backPanel.add(backButton);
			backButton.addActionListener(new BackToProfileListener());
		}
		
		public void load() {
			
			System.out.println("wewe");
			
			combo.removeAllItems();
			
			try {
				ResultSet rs = exe.showFriends(selectedUsername);
				
				while(rs.next()) {
					
					String usernameFriend = "";
					String u1 = rs.getString("accountAccettante");
					String u2 = rs.getString("accountRichiedente");
					if(!u1.equals(selectedUsername))
						usernameFriend = u1;
					else 
						usernameFriend = u2;
					
					combo.addItem(usernameFriend);
				}
				
			} catch (SQLException e) {
				error.showMessageDialog(MainFrame.this, "Error......");
				e.printStackTrace();
			}
		}
		
		private JComboBox combo;
	}
	
	public class ReportPanel extends LoadablePanel {
		public ReportPanel() {
			
			this.setLayout(new BorderLayout());
			
			area = new JTextArea();
			this.add(area);
			
			JButton backButton = new JButton("BACK");
			backButton.addActionListener(new BackToProfileListener());
			this.add(backButton, BorderLayout.SOUTH);
		}
		
		public void load() {
			try {
				area.setText("Lista report: \n");
				
				ResultSet rs = exe.showReport(selectedUsername);
				
				while(rs.next()) {
					area.setText(area.getText() + "Username reporter: " + rs.getString("Reporter") + " motivo: " + rs.getString("motivo"));
				}

				
			} catch (SQLException e) {
				error.showMessageDialog(MainFrame.this, "Errore nella visualizzazione dei report");
				e.printStackTrace();
			}
		}
		
		private JTextArea area;
	}
	
	public class FriendsListWithGamePanel extends LoadablePanel {
		private static final long serialVersionUID = 4102504377089378584L;
		public FriendsListWithGamePanel() {
			
			this.setLayout(new BorderLayout());
			
			area = new JTextArea();
			this.add(area);
			
			JPanel northPanel= new JPanel();
			this.add(northPanel, BorderLayout.NORTH);
			
			northPanel.add(new JLabel("Nome Gioco: "));
			nomeGioco = new JTextField(5);
			northPanel.add(nomeGioco);
			
			JButton button = new JButton("Search");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					area.setText("Lista amici con il gioco: " + nomeGioco.getText() + "\n");
				
					try {
						ResultSet rs = exe.listaFriendWithAGame(selectedUsername, nomeGioco.getText());
					
						while(rs.next()) {
							area.setText(area.getText() + rs.getString("P.username") + "\n");
						}
						
					} catch (SQLException e1) {
						error.showMessageDialog(MainFrame.this, "Errore nella ricerca....");
						e1.printStackTrace();
					}
				}
			});
			northPanel.add(button);
			
			JButton backButton = new JButton("BACK");
			backButton.addActionListener(new BackToProfileListener());
			this.add(backButton, BorderLayout.SOUTH);
		}
		
		public void load() {
			area.setText("");
		}
		
		private JTextArea area;
		private JTextField nomeGioco;
		private JTextField username;
	}
	
	public class CreateBookMarkPanel extends LoadablePanel {
		public CreateBookMarkPanel() {
			
			this.setLayout(new BorderLayout());
			
			area = new JTextArea();
			JScrollPane scroll = new JScrollPane(area);
			this.add(scroll);
			
			JPanel northPanel = new JPanel();
			this.add(northPanel, BorderLayout.NORTH);
			
			northPanel.add(new JLabel("IdServer: "));
			idServer = new JTextField(5);
			northPanel.add(idServer);
			northPanel.add(new JLabel("NomeBookMark: "));
			nomeBookMark = new JTextField(5);
			northPanel.add(nomeBookMark);
			
			JButton button = new JButton("Crea bookmark");
			northPanel.add(button);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						exe.createBookMark(selectedUsername, Integer.parseInt(idServer.getText()), nomeBookMark.getText());
						loadPanel(CreateBookMarkPanel.this);
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						error.showMessageDialog(MainFrame.this, "Errore...");
						e1.printStackTrace();
					}
				}
			});
			
			JButton backButton = new JButton("BACK");
			backButton.addActionListener(new BackToProfileListener());
			this.add(backButton, BorderLayout.SOUTH);
		}
		
		public void load() {
			
            try {
				ResultSet rs = exe.listOfFavoritesServers(selectedUsername);
				StringBuilder result = new StringBuilder("");
				result.append("lista preferiti nome, idServer, latenzaMedia: \n");
				
				while(rs.next()) {
					String bookmarkName = rs.getString("nome");
					int idServer = rs.getInt("idServer");
					float latenza = rs.getFloat("latenzaMedia");
					
					result.append(bookmarkName + " ");
					result.append(idServer);
					result.append(" ");
					result.append(latenza);
					result.append("\n");
				}
				
				area.setText(result.toString());
				
			} catch (SQLException e) {
				error.showMessageDialog(MainFrame.this, "Errore nel caricamento dei bookmarks");
				//e.printStackTrace();
			}
		}
		
		private JTextField idServer;
		private JTextField nomeBookMark;
		private JTextArea area;
	}
		
	public class GameListPanel extends LoadablePanel {
		public GameListPanel() {
			this.setLayout(new BorderLayout());
			
			area = new JTextArea();
			JScrollPane scroll = new JScrollPane(area);
			this.add(scroll);
			
			JPanel southPanel = new JPanel();
			southPanel.add(new JLabel("Id Gioco: "));
			idGioco = new JTextField(5);
			southPanel.add(idGioco);
			
			JButton buy = new JButton("Buy");
			buy.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						exe.buyGame(selectedUsername, Integer.parseInt(idGioco.getText()));
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
						error.showMessageDialog(MainFrame.this, "Non è possibile comprare");
					}
					
			}});
			southPanel.add(buy);
			
			this.add(southPanel, BorderLayout.SOUTH);
			
			
			JPanel northPanel = new JPanel();
			this.add(northPanel, BorderLayout.NORTH);
			
			northPanel.add(new JLabel("Categoria"));
			categoria = new JTextField(5);
			northPanel.add(categoria);
			
			JButton search = new JButton("Search");
			search.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {	
						ResultSet rs = exe.listaGiochiPerCategoria(categoria.getText());
						
						area.setText("");
						area.setText("Lista giochi per categoria: idGioco, nome\n");
						
						while(rs.next()) {
							int id = rs.getInt("idGioco");
							String nome = rs.getString("nome");
							
							area.setText(area.getText() + Integer.toString(id) + " " + nome + "\n");
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
			});
			northPanel.add(search);
			
			JButton backButton = new JButton("BACK");
			backButton.addActionListener(new BackToProfileListener());
			southPanel.add(backButton);
		}
		
		public void load() {
			area.setText("");
			idGioco.setText("");
			categoria.setText("");
		}
		
		private JTextArea area;
		private JTextField idGioco;
		private JTextField categoria;
	}
	
	public class GameListWithFriends extends LoadablePanel {
		public GameListWithFriends() {
			this.setLayout(new BorderLayout());
			
			area = new JTextArea();
			JScrollPane scroll = new JScrollPane(area);
			this.add(scroll);
			
			JPanel northPanel = new JPanel();
			northPanel.add(new JLabel("Username amico: "));
			friend = new JTextField(5);
			northPanel.add(friend);
			
			JButton search = new JButton("Search");
			northPanel.add(search, BorderLayout.SOUTH);
			
			search.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						ResultSet rs = exe.listaGiochiPerAmici(selectedUsername, friend.getText());
						
						area.setText("Lista giochi che si hanno in comune con: " + friend.getText()+ "\n");
						
						while(rs.next()) {
							area.setText(area.getText() + rs.getString("nome") + "\n");
						}
					} catch (SQLException e1) {
						error.showMessageDialog(MainFrame.this, "Errore durante il caricamento della lista di giochi");
						e1.printStackTrace();
					}
				}
				
			});
			
			this.add(northPanel, BorderLayout.NORTH);
			JButton backButton = new JButton("BACK");
			backButton.addActionListener(new BackToProfileListener());
			this.add(backButton, BorderLayout.SOUTH);
		}
		
		public void load() {
			area.setText("");
			friend.setText("");
		}
		
		private JTextArea area;
		private JTextField friend;
	}
	
	
	public JPanel createRegistrationPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panel.add(new JLabel("Registra"), BorderLayout.NORTH);
		
		//CREATE CENTER PANEL
		JPanel centerPanel = new JPanel();
		panel.add(centerPanel);
		
		centerPanel.setLayout(new GridLayout(3, 2));

		centerPanel.add(new JLabel("Username: "));
		JTextField username = new JTextField(5);
		centerPanel.add(username);

		centerPanel.add(new JLabel("Mail: "));
		JTextField email = new JTextField(5);
		centerPanel.add(email);
		
		centerPanel.add(new JLabel("Password: "));
		JTextField password = new JTextField(5);
		centerPanel.add(password);
		
		//CRETATE SOUTH PANEL
		JPanel southPanel = new JPanel();
		panel.add(southPanel, BorderLayout.SOUTH);
		
		JButton registra = new JButton("Accedi");
		registra.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					exe.registra(username.getText(), email.getText(), password.getText());
					selectedUsername = username.getText();
					loadPanel(login);
				} catch (SQLException e1) {
					e1.printStackTrace();
					error.showMessageDialog(MainFrame.this, "Registrazione non andata a buon fine");
				}
			}
		});
		southPanel.add(registra);
		
		JButton exitButton = new JButton("Esci");
		exitButton.addActionListener(new BackToLoginListener());
		southPanel.add(exitButton);
		
		return panel;
	}
	
	public JPanel createModPanel() {
		JPanel panel = new JPanel();
		
		ChangeButtonPanel cliente = new ChangeButtonPanel(login, "Cliente");
		panel.add(cliente);
		ChangeButtonPanel gestore = new ChangeButtonPanel(this.gestore, "Gestore");
		panel.add(gestore);
		
		return panel;
	}
	
	
	public JPanel createLoginPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panel.add(new JLabel("GamingWorld"), BorderLayout.NORTH);
		
		//CREATE CENTER PANEL
		JPanel centerPanel = new JPanel();
		panel.add(centerPanel);
		
		centerPanel.setLayout(new GridLayout(3, 2));
		centerPanel.add(new JLabel("Username: "));
		JTextField email = new JTextField(5);
		centerPanel.add(email);
		
		centerPanel.add(new JLabel("Password: "));
		JTextField password = new JTextField(5);
		centerPanel.add(password);
		
		centerPanel.add(new JLabel("Non hai un account?"));
		
		final JLabel reg = new JLabel("Registrati");
		reg.setForeground(Color.blue);
		
		reg.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				loadPanel(registration);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				reg.setForeground(Color.green);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				reg.setForeground(Color.blue);
			}
		});
		
		centerPanel.add(reg);
		
		//CREATE SOUTH PANEL
		JPanel southPanel = new JPanel();
		panel.add(southPanel, BorderLayout.SOUTH);
		
		JButton accedi = new JButton("Accedi");
		accedi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet rs = exe.accedi(email.getText(), password.getText());
					if(!rs.next())
					{
						error.showMessageDialog(MainFrame.this, "Login non andato a buon fine");
					}
					else
					{
						selectedUsername = email.getText();
						loadPanel(profile);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		southPanel.add(accedi);
		
		//southPanel.add(exit);
		
		return panel;
	}
	
	public void loadPanel(JPanel panel) {
		if(currentPanel != null)
			this.remove(currentPanel);
		
		
		//MOMENTO AGGIUSTO
		if(panel instanceof LoadablePanel)
			((LoadablePanel)panel).load();
		
		currentPanel = panel;
		this.add(panel);
		
		this.invalidate();
		this.validate();
		this.repaint();
	}
	
	
	private void shutDown() {
		MainFrame.this.dispose();
		exe.end();
	}
	
	public String getSelectedUsername() {
		return selectedUsername;
	}
	
	public JOptionPane getErrorPane() {
		return error;
	}
	
	private JPanel currentPanel;
	private JPanel login;
	private JPanel mod;
	private JPanel registration;
	private ProfilePanel profile;
	
	private JPanel gestore;
	
	private LoadablePanel gameListPanel;
	private LoadablePanel gameListWithFriendsPanel;
	private LoadablePanel friendsWithGamePanel;
	private LoadablePanel createBookMarkPanel;
	private LoadablePanel reportPanel;
	private LoadablePanel deleteFriendPanel;
	
	private String selectedUsername;
	
	private QueryExecuter exe;
	private JOptionPane error;
}

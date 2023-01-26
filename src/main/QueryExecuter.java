package main;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;  

public class QueryExecuter {
	
	public QueryExecuter() {
		String utente="root";
		String password="%(dJ*6!tuB4PA^Fp";
			
	    try {
	            
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbTest",utente,password);
	        	
	    } catch (SQLException e) {
			e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
	    }
	}
	
	private String currentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now(); 
		return dtf.format(now);
	}
	
	public ResultSet accedi(String username, String password) throws SQLException {
		String query = "SELECT username FROM Account WHERE username = ? AND password = ?";
		
		ResultSet rs = null;
		
		PreparedStatement ps;
		ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, password);
		
		rs = ps.executeQuery();
		
		return rs;
	}
	
	public boolean registra(String username, String email, String password) throws SQLException {
		String query = "INSERT INTO Account values (?,?,?,?);";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setString(3, email);
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now(); 
		ps.setString(4, dtf.format(now));
		
		return ps.execute();
	}
	
	public ResultSet listOfFavoritesServers(String username) throws SQLException {		
		String query = """
					SELECT Sa.nome, S.idServer, S.latenzaMedia
					FROM Server S, Salva Sa
					WHERE S.idServer = Sa.idServer AND Sa.username = ?;
					""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public ResultSet listaGiochiPerCategoria(String categoria) throws SQLException{
		String query = """
				SELECT G.idGioco, G.nome
				FROM Gioco as G, Appartiene as A
				WHERE G.idGioco = A.idGioco AND A.nomeCategoria = ?;
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, categoria);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public ResultSet listaGiochiPerAmici(String username, String friend) throws SQLException{
		String query = """
					SELECT nome
					FROM Gioco
					WHERE idGioco in
					(SELECT idGioco
						FROM Possiede
						WHERE username = ?)
					AND idGioco in
					(SELECT idGioco
						FROM  Possiede
						WHERE username = ?);
					""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, friend);
		ps.setString(2, username);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	
	public int countAmici(String username) {
		String query = """
				SELECT COUNT(*) AS NumeroAmici
				FROM Account, Amicizia
				WHERE username = ? 
				AND (
					username = accountRichiedente
					OR username = accountAccettante
				);
				
				""";
		
		int amici = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				amici = rs.getInt("NumeroAmici");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return amici;
	}
	
	public double winRate(String username) throws SQLException {
		String query = """
					SELECT   
						(SELECT Count(*)
						FROM Team T, `Match` M, Compone C
						WHERE (T.nome = M.team1 OR T.nome = M.team2) 
						AND T.nome = C.nome
						AND C.componente = ?
						AND M.teamVincitore = T.nome) / Count(*) as WinRate
						FROM Team T, `Match` M, Compone C
						WHERE (T.nome = M.team1 OR T.nome = M.team2) 
                        AND T.nome = C.nome
                        AND (C.componente = ?);

					""";
		
		double winRate = 0.0f;
		
    	PreparedStatement ps = conn.prepareStatement(query);
    	
    	for(int i=1;i<2 +1;i++)
    		ps.setString(i, username);
    	
    	ResultSet rs = ps.executeQuery();
    	
    	if(rs.next())
    		 winRate = rs.getDouble("WinRate");
    	
    	return winRate;
	}
	
	public void buyGame(String username, int idGioco) throws SQLException {
		String query = "INSERT INTO Possiede (username, idGioco, dataAcquisto) values (?, ?, ?);";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ps.setInt(2, idGioco);
		ps.setString(3, currentDate());
		ps.execute();
	}
	
	public void createBookMark(String username, int idServer, String nome) throws SQLException {
		String query = "INSERT INTO Salva values (?,?,?);";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ps.setInt(2, idServer);
		ps.setString(3, nome);
		ps.execute();
	}
	
	public ResultSet achievementNeverUnlocked() throws SQLException {
		String query = """
				SELECT * 
				FROM Achievement
				WHERE NOT EXISTS(SELECT * FROM Sblocca WHERE nome = nomeAchievement);
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	
	public ResultSet giocatoriWinrateMoreThan50() throws SQLException {
		String query = """
				SELECT username
				FROM Account
				WHERE 0.5 < (SELECT   
					(SELECT Count(*)
						FROM Team T, `Match` M, Compone C
						WHERE (T.nome = M.team1 OR T.nome = M.team2) 
						AND C.componente = username 
						AND M.teamVincitore = T.nome  
                        AND C.nome = T.nome )
				/ Count(*) as WinRate
				FROM Team T, `Match` M, Compone C 
				WHERE (T.nome = M.team1 OR T.nome = M.team2) AND C.componente = username AND C.nome = T.nome);

				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	public ResultSet listaFriendWithAGame(String username, String nomeGioco) throws SQLException {
		
		String query = """
					SELECT DISTINCT P.username
					FROM Amicizia as AM, Possiede as P, Gioco as G
					WHERE G.nome = ? AND P.idGioco = G.idGioco AND
					(accountAccettante = ? AND P.username = accountRichiedente) 
					OR (accountRichiedente = ? AND P.username = accountAccettante);
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, nomeGioco);
		ps.setString(2, username);
		ps.setString(3, username);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	
	public ResultSet showReport(String username) throws SQLException {
		String query = """
					SELECT Reporter, motivo
					FROM Report as R
					WHERE R.Reported = ?;
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	
	public void deleteAccount(String username) throws SQLException {
		String query = """
				DELETE FROM Account WHERE username = ?;
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ps.execute();
	}
	
	public int usersForGame(int idGioco) throws SQLException {
		String query = """
				SELECT Count(*) as NumeroUtenti
				FROM Possiede as P
				WHERE P.idGioco = ?;
				""";
		
		int count = 0;
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idGioco);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next())
			count = rs.getInt("NumeroUtenti");
		
		return count;
	}
	
	public ResultSet showMatchWon(String nome) throws SQLException  {
		String query = """
				SELECT *
				FROM Team
				INNER JOIN `Match`
				ON nome = teamVincitore
				WHERE nome = ?;
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, nome);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	
	public ResultSet showListGiochiRapportoPossedimentoOreGiocate() throws SQLException {
		String query = """
					SELECT G.idGioco, G.nome, (SUM(P.oreGiocate) / COUNT(*)) AS Rapporto
					FROM Gioco AS G
					NATURAL JOIN Possiede AS P
					GROUP BY G.idGioco;
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	
	public void createFriend(String username, String usernameF) throws SQLException {
		String query = "INSERT INTO Amicizia values (?,?,?) ";

		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, usernameF);
		ps.setString(3, currentDate());
		ps.execute();
	}
	
	public ResultSet showScoreBoardTeams() throws SQLException {
		String query = """
				SELECT T.nome, (SELECT Count(*) as V FROM `Match` as M WHERE (T.nome = M.team1 OR T.nome = M.team2) AND T.nome = M.teamVincitore) as Vittorie
				FROM Team as T
				ORDER BY Vittorie;
			""";
	
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
	
		return rs;
	}
	
	public ResultSet showPublisher() throws SQLException {
		String query = """
					SELECT *
					FROM Publisher
					   """;
		
		PreparedStatement ps = conn.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	
	public ResultSet showServersServerGame(int idGioco) throws SQLException {
		String query = """
				SELECT * 
				FROM Server
				WHERE idGioco = ?;
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, idGioco);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	
	public ResultSet showDeveloperForRole(String role, int idGioco) throws SQLException {
		String query = """
				SELECT D.nome
				FROM Developer AS D
				NATURAL JOIN Sviluppa AS S
				WHERE ruolo = ? AND idGioco = ?;
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, role);
		ps.setInt(2, idGioco);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	
	public ResultSet showFriends(String username) throws SQLException {
		String query = """
				SELECT * 
				FROM Amicizia as A
				WHERE A.accountAccettante = ?
				OR A.accountRichiedente = ?;
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, username);
		ResultSet rs = ps.executeQuery();
		
		return rs;
	}
	
	public void deleteFriend(String username, String friend) throws SQLException {
		String query ="""
				DELETE FROM Amicizia 
				WHERE (accountAccettante = ? AND accountRichiedente = ?) 
				OR (accountAccettante = ? AND accountRichiedente = ?);
				""";
		
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, friend);
		ps.setString(3, friend);
		ps.setString(4, username);	
		ps.execute();	
	}
	
	public void end() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	Connection conn;
}

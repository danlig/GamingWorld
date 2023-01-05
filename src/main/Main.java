package main;

import java.sql.*;

public class Main {

	public static void main(String[] args) {
	   String utente="root";
	   String password="%(dJ*6!tuB4PA^Fp";
	   Connection conn;
		
        try {
            
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbTest",utente,password);
        	
//        	String input = "antonio";
//        	String query =  
//        			"SELECT"  
//        			+ "(SELECT Count(*)" 
//        			+ "	 	FROM Team T, `Match` M "
//        			+ "	 	WHERE (T.nome = M.team1 OR T.nome = M.team2)  "
//        			+ "	     AND (T.username1 = ? OR T.username2 = ? OR T.username3 = ? OR T.username4 = ? OR T.username5 = ? ) "
//        			+ "	 	AND M.teamVincitore = T.nome) / Count(*) as WinRate "
//        			+ "	 FROM Team T, `Match` M  "
//        			+ "	 WHERE (T.nome = M.team1 OR T.nome = M.team2) AND (T.username1 = ? OR T.username2 = ? OR T.username3 = ? OR T.username4 = ? OR T.username5 = ? );"; 
//
//        	PreparedStatement ps = conn.prepareStatement(query);
//        	
//        	for(int i=1;i<10 +1;i++)
//        		ps.setString(i, input);
//        	
//        	ResultSet rs = ps.executeQuery();
//        	
//        	while(rs.next()) {
//        		System.out.println(rs.getDouble("WinRate"));
//        	}
//        	
        	
        	
        	
        	conn.close();
        	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        
        
        
        
        MainFrame mainFrame = new MainFrame();
        
	}
}

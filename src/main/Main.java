package main;

import java.sql.*;

public class Main {

	public static void main(String[] args) {
        QueryExecuter queryExecuter = new QueryExecuter();
        
        MainFrame mainFrame = new MainFrame(queryExecuter);
	}
}

package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	public DBManager() { }
	
	private static Connection connection = null;
	
	public static Connection getConnection () throws Exception {
		try {
			// Chargement du driver jdbc mysql
			Class.forName ("com.mysql.cj.jdbc.Driver");
			
			// Ouverture de la connexion
         //   String url = "jdbc:mysql://localhost:3306/restodb";
            String url = "jdbc:mysql://localhost:3306/restodb?serverTimezone=Europe/Paris";

            String username = "root";
            String password = ""; // Remplacez par votre mot de passe si nécessaire
            
            connection = DriverManager.getConnection(url, username, password);
            
			// Ouverture de la connexion
			// connection = DriverManager.getConnection ("jdbc:mysql://localhost/dbusers", "root", "");
			return connection;
		} catch (ClassNotFoundException e) {
			throw new Exception ("Driver Class not found : '" + e.getMessage () + "' ");
		} catch (SQLException e) {
			throw new Exception ("Error : Unable to open connection with database !");
		}
	}
}

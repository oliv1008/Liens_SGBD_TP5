package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Associe;

public class AssocieDAO {

	/*===== ATTRIBUTES =====*/
	private static Connection con;
	private static PreparedStatement s;

	/*===== BUILDER =====*/
	public AssocieDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			System.err.println("Impossible de charger le driver JDBC");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			con = DriverManager.getConnection(Settings.pathToDatabase, "root", "");
		}
		catch(SQLException e) {
			System.err.println("Impossible de se connecter au serveur SQL");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/*===== METHODS =====*/
	
	/**
	 * Ajoute un utilisateur à la BDD
	 * @param name le nom de l'utilisateur
	 * @warning 2 utilisateurs ne peuvent pas avoir le même non
	 */
	public void insertUser(String name) {
		try {
			s = con.prepareStatement("INSERT INTO User (name) VALUES (?)");
			s.setString(1, name);
			s.executeUpdate();
			s.close();
			System.out.println("Ajout d'un utilisateur à la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime un utilisateur de la BDD
	 * @param name le nom de l'utilisateur
	 */
	public void deleteUser(String name) {
		try {
			s = con.prepareStatement("DELETE FROM User WHERE name = '" + name + "'");
			s.executeUpdate();
			s.close();
			System.out.println("Suppression d'un utilisateur à la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode pour récupérer un utilisateur à partir de son nom
	 * @param name le nom de l'utilisateur
	 * @return l'utilisateur du même nom, ou null si non-trouvé
	 */
	public Associe getUserByName(String name) {
		try {
			s = con.prepareStatement("SELECT id, name FROM User WHERE name = '" + name + "'");
			ResultSet result = s.executeQuery();

			while(result.next()){         
				return new Associe(result.getInt("id"), result.getString("name"));
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Méthode pour récupérer la liste de tous les livres du catalogueles utilisateurs
	 * @return la liste de tous les utilisateurs
	 */
	public ArrayList<Associe> getAllUsers(){
		try {
			s = con.prepareStatement("SELECT id, name FROM User");
			ResultSet result = s.executeQuery();

			ArrayList<Associe> users = new ArrayList<Associe>();

			while(result.next()){         
				users.add(new Associe(result.getInt("id"), result.getString("name")));
			}

			return users;

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}

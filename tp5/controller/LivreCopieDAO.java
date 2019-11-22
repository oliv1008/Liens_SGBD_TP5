package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LivreCopieDAO {

	/*===== ATTRIBUTES =====*/
	private static Connection con;
	private static PreparedStatement s;

	/*===== BUILDER =====*/
	public LivreCopieDAO() {
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
		}
	}

	/*===== METHODS =====*/
	
	/**
	 * Ajoute une copie d'un livre à la bibliothèque
	 * @param isbn l'isbn du livre à ajouter
	 */
	public void insertBookCopy(int isbn) {
		if(!(new PretDAO().isbnExist(isbn))) {
			System.err.println("Cet isbn ("+isbn+") n'existe pas");
		}
		else {
			try {
				s = con.prepareStatement("INSERT INTO Copy (isbn) VALUES (?)");
				s.setInt(1, isbn);
				s.executeUpdate();
				s.close();
				System.out.println("Ajout d'une copie d'un livre à la BDD");
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Supprime une copie d'un livre de la bibliothèque
	 * @param isbn l'isbn du livre à supprimer
	 * @throws Exception
	 */
	public void removeBookCopy(int isbn) throws Exception {
		if(!(new PretDAO().isbnExist(isbn))) {
			System.err.println("Cet isbn ("+isbn+") n'existe pas");
		}
		else {
			try {
				s = con.prepareStatement("SELECT id FROM Copy WHERE isbn = " + isbn);
				ResultSet result = s.executeQuery();

				int idCopyToDelete = -1;

				while(result.next()){         
					idCopyToDelete = result.getInt("id");
				}

				if(idCopyToDelete == -1) {
					throw new Exception("Il n'existe plus de copies de ce livre ("+isbn+")");
				}
				else {
					s = con.prepareStatement("DELETE FROM Copy WHERE isbn = " + isbn + " AND id = " + idCopyToDelete);
					s.executeUpdate();
				}

				s.close();
				System.out.println("Suppression d'une copie d'un livre de la BDD");
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

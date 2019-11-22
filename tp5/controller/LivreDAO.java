package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import model.Livre;

public class LivreDAO {

	/*===== ATTRIBUTES =====*/
	private static Connection con;
	private static PreparedStatement s;

	/*===== BUILDER =====*/
	public LivreDAO() {
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
	 * Ajoute un livre au catalogue
	 * @param isbn 
	 * @param name 
	 * @param author
	 */
	public void insertBook(int isbn, String name, String author) throws Exception {
		try {
			s = con.prepareStatement("INSERT INTO Book (isbn, name, author) VALUES (?, ?, ?)");
			s.setInt(1, isbn);
			s.setString(2, name);
			s.setString(3, author);
			s.executeUpdate();
			s.close();
			System.out.println("Ajout d'un livre à la BDD");
		} catch(SQLIntegrityConstraintViolationException e) {
			throw new Exception("Ce livre (" + isbn + ") est déjà dans la bibliothèque");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime un livre de la BDD
	 * @param isbn l'isbn du livre
	 */
	public void deleteBook(int isbn) {
		try {
			s = con.prepareStatement("DELETE FROM Book WHERE isbn = " + isbn);
			s.executeUpdate();
			s.close();
			System.out.println("Suppression d'un livre de la BDD");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode pour récupérer un livre à partir de son nom
	 * @param name le nom du livre
	 * @return le livre du même nom, ou null si non-trouvé
	 */
	public Livre getBookByName(String name) {
		try {
			s = con.prepareStatement("SELECT isbn, name, author FROM Book WHERE name = '"  + name + "'");
			ResultSet result = s.executeQuery();

			while(result.next()){         
				return new Livre(result.getInt("isbn"), result.getString("name"), result.getString("author"));
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Méthode pour récupérer une liste de livres écrits par un auteur
	 * @param author l'auteur des livres
	 * @return une liste de livres écrits du même-auteur, ou null si non-trouvé
	 */
	public ArrayList<Livre> getBooksByAuthor(String author) {
		try {
			s = con.prepareStatement("SELECT isbn, name, author FROM Book WHERE author = '" + author + "'");
			ResultSet result = s.executeQuery();

			ArrayList<Livre> books = new ArrayList<Livre>();

			while(result.next()){         
				books.add(new Livre(result.getInt("isbn"), result.getString("name"), result.getString("author")));
			}

			return books;

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Méthode pour récupérer la liste de tous les livres du catalogue
	 * @return la liste de tous les livres
	 */
	public ArrayList<Livre> getAllBooks(){
		try {
			s = con.prepareStatement("SELECT isbn, name, author FROM Book");
			ResultSet result = s.executeQuery();

			ArrayList<Livre> books = new ArrayList<Livre>();

			while(result.next()){         
				books.add(new Livre(result.getInt("isbn"), result.getString("name"), result.getString("author")));
			}

			return books;

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}

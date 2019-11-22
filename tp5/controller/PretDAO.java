package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class PretDAO {

	/*===== ATTRIBUTES =====*/
	private static Connection con;
	private static PreparedStatement s;

	/*===== BUILDER =====*/
	public PretDAO() {
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
	 * Méthode pour "emprunter un livre"
	 * @param userId l'id de l'utilisateur empruntant le livre
	 * @param isbn l'isbn du livre emprunté
	 * @param dateLoan la date d'emprunt
	 */
	public void loanBook(int userId, int isbn, Date dateLoan) {
		if(!idExist(userId)){
			System.err.println("Cet userId ("+userId+") n'existe pas");
		}
		else if(!isbnExist(isbn)) {
			System.err.println("Cet isbn ("+isbn+") n'existe pas");
		}

		else {
			try {
				LivreCopieDAO lcDAO = new LivreCopieDAO();
				lcDAO.removeBookCopy(isbn);

				try {
					s = con.prepareStatement("INSERT INTO Loan (id, isbn, date_loan) VALUES (?, ?, ?)");
					s.setInt(1, userId);
					s.setInt(2, isbn);
					s.setDate(3, dateLoan);
					s.executeUpdate();
					s.close();
					System.out.println("Ajout d'un pret à la BDD");
				} catch(SQLException e) {
					e.printStackTrace();
				}
			} catch(Exception e) {
				System.err.println("Ce livre ("+isbn+") n'est plus disponible en rayon");
			}
		}	
	}

	/**
	 * Méthode pour "retourner un livre"
	 * @param userId l'id de l'utilisateur empruntant le livre
	 * @param isbn l'isbn du livre emprunté
	 * @param dateReturn la date de retour
	 */
	public void returnBook(int userId, int isbn, Date dateReturn) {
		Date dateLoan = getDateLoanByIdAndIsbn(userId, isbn);
		if(dateLoan == null){
			System.err.println("Ce livre ("+isbn+") n'a pas été emprunté par cet utilisateur ("+userId+")");
		}
		else if(dateLoan.getTime() > dateReturn.getTime()) {
			System.err.println("La date de retour que vous avez spécifiée est AVANT la date d'emprunt ("+dateLoan.toString()+")");
		}
		else {
			try {
				s = con.prepareStatement("SELECT * FROM Loan WHERE id = " + userId + " AND isbn = " + isbn, 
						ResultSet.TYPE_SCROLL_INSENSITIVE, 
						ResultSet.CONCUR_UPDATABLE);

				ResultSet result = s.executeQuery();

				while(result.next()) {
					result.updateDate("date_return", dateReturn);
					result.updateRow();
				}

				LivreCopieDAO lcDAO = new LivreCopieDAO();
				lcDAO.insertBookCopy(isbn);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Ajoute directement un emprunt (date d'emprunt + date de retour) à la BDD
	 * @param userId
	 * @param isbn
	 * @param dateLoan
	 * @param dateReturn
	 */
	public void insertLoan(int userId, int isbn, Date dateLoan, Date dateReturn) {
		loanBook(userId, isbn, dateLoan);
		returnBook(userId, isbn, dateReturn);
	}

	/**
	 * Donne la date d'emprunt d'un livre en fonction d'un id utilisateur et d'un isbn
	 * @param userId
	 * @param isbn
	 * @return la date d'emprunt du livre concerné, ou null sin non-trouvé
	 */
	public Date getDateLoanByIdAndIsbn(int userId, int isbn) {
		try {
			s = con.prepareStatement("SELECT date_loan FROM Loan WHERE id = "  + userId + " AND isbn = " + isbn);
			ResultSet result = s.executeQuery();

			while(result.next()){         
				return new Date(result.getDate("date_loan").getTime());
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Permet de vérifier si un isbn existe bien dans la BDD
	 * @param isbn l'isbn à vérifier
	 * @return true si le livre est dans la catalogue, false sinon
	 * TODO : à bouger dans une autre classe ?
	 */
	public Boolean isbnExist(int isbn) {
		try {
			s = con.prepareStatement("SELECT isbn FROM Book");
			ResultSet result = s.executeQuery();

			while(result.next()){         
				if(result.getInt("isbn") == isbn) {
					return true;
				}
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Permet de vérifier si un userId existe bien dans la BDD
	 * @param userId l'id à vérifier
	 * @return true si l'id est dans la liste des utilisateurs, false sinon
	 * TODO : à bouger dans une autre classe ?
	 */
	public Boolean idExist(int userId) {
		try {
			s = con.prepareStatement("SELECT id FROM User");
			ResultSet result = s.executeQuery();

			while(result.next()){         
				if(result.getInt("id") == userId) {
					return true;
				}
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

}

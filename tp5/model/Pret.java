package model;

import java.time.LocalDateTime;

public class Pret {

	private int idAssocie;
	private int isbn;
	private LocalDateTime dateEmprunt;
	private LocalDateTime dateRetour;
	
	/**
	 * @return the dateEmprunt
	 */
	public LocalDateTime getDateEmprunt() {
		return dateEmprunt;
	}

	/**
	 * @param dateEmprunt the dateEmprunt to set
	 */
	public void setDateEmprunt(LocalDateTime dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}

	/**
	 * @return the dateRetour
	 */
	public LocalDateTime getDateRetour() {
		return dateRetour;
	}

	/**
	 * @param dateRetour the dateRetour to set
	 */
	public void setDateRetour(LocalDateTime dateRetour) {
		this.dateRetour = dateRetour;
	}

	/**
	 * @param id
	 * @param isbn
	 */
	public Pret(int idAssocie, int isbn) {
		super();
		this.idAssocie = idAssocie;
		this.isbn = isbn;
	}
	
	/**
	 * @return the id
	 */
	public int getIdAssocie() {
		return idAssocie;
	}
	/**
	 * @param id the id to set
	 */
	public void setIdAssocie(int id) {
		this.idAssocie = id;
	}
	/**
	 * @return the isbn
	 */
	public int getIsbn() {
		return isbn;
	}
	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}
}

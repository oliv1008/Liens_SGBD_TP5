package model;

public class LivreCopie {

	private int isbn;
	private int idCopy;
	
	/**
	 * @param isbn
	 * @param idCopy
	 */
	public LivreCopie(int isbn, int idCopy) {
		super();
		this.isbn = isbn;
		this.idCopy = idCopy;
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
	/**
	 * @return the idCopy
	 */
	public int getIdCopy() {
		return idCopy;
	}
	/**
	 * @param idCopy the idCopy to set
	 */
	public void setIdCopy(int idCopy) {
		this.idCopy = idCopy;
	}
}

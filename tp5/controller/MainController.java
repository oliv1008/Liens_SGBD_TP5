package controller;

import view.Main;

public class MainController {

	public static void main(String[] args) {
		LivreDAO livreDao = new LivreDAO();
		AssocieDAO associeDao = new AssocieDAO();
		PretDAO pretDao = new PretDAO();
		LivreCopieDAO livreCopieDAO = new LivreCopieDAO();
		
//		System.out.println(livreDao.getBookByName("Le royaume des champignons"));
		
//		ArrayList<Livre> books = livreDao.getAllBooks();
//		for(Livre l : books) {
//			System.out.println(l);
//		}
//		
//		System.out.println(associeDao.getUserByName("Ronan"));
		
//		associeDao.insertUser("Claude");
	
//		livreDao.insertBook(22487, "Charlie et la chocolaterie", "Booba");
		
//		System.out.println("Retourner un livre qui n'existe pas :");
//		pretDao.returnBook(2, 14896, new Date(2019, 11, 22));
//		
//		System.out.println("Retourner un livre non emprunté :");
//		pretDao.returnBook(2, 22487, new Date(2019, 11, 22));
		
//		pretDao.loanBook(3, 78495, new Date(2018, 12, 8));
		
//		pretDao.returnBook(3, 78495, new Date(2019, 7, 21));
		
		Main view = new Main();
	}
}

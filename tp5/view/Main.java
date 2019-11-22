package view;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import controller.AssocieDAO;
import controller.LivreCopieDAO;
import controller.LivreDAO;
import controller.PretDAO;
import model.Livre;

public class Main {

	public Main() {
		LivreDAO livreDao = new LivreDAO();
		AssocieDAO associeDao = new AssocieDAO();
		PretDAO pretDao = new PretDAO();
		LivreCopieDAO livreCopieDAO = new LivreCopieDAO();

		Scanner sc = new Scanner(System.in);
		String choice = "0";

		while(!choice.equals("-1")) {

			//-------------------- MENU PRINCIPAL --------------------
			if(choice.equals("0")) {
				clearScreen();
				System.out.println("====== MENU PRINCIPAL ======");
				System.out.println("[1] Gestion des utilisateurs");
				System.out.println("[2] Gestion des livres");
				System.out.println("[3] Gestion des copies");
				System.out.println("[4] Gestion des prêts");
				System.out.println("[x] Quitter l'application");
				System.out.print("Votre choix ? > ");
				choice = sc.nextLine();
			}
			//-------------------- MENU UTILISATEURS --------------------
			if(choice.equals("1")) {
				clearScreen();
				System.out.println("====== MENU UTILISATURS ======");
				System.out.println("[1] Ajouter un utilisateur");
				System.out.println("[2] Supprimer un utilisateur");
				System.out.println("[0] Retour menu principal");
				System.out.print("Votre choix ? > ");
				choice = sc.nextLine();
				//-------------------- AJOUT UTILISATEUR --------------------
				if(choice.equals("1")) {
					clearScreen();
					System.out.print("Nom : ");
					String name = sc.nextLine();
					associeDao.insertUser(name);
					choice = "1";
				}
				//-------------------- SUPPRESSION UTILISATEUR --------------------
				else if(choice.equals("2")) {
					clearScreen();
					System.out.print("Nom : ");
					String name = sc.nextLine();
					associeDao.deleteUser(name);
					choice = "1";
				}
			}
			//-------------------- MENU LIVRES --------------------
			if(choice.equals("2")) {
				clearScreen();
				System.out.println("====== MENU LIVRES ======");
				System.out.println("[1] Ajouter un livre");
				System.out.println("[2] Supprimer un livre");
				System.out.println("[3] Afficher catalogue");
				System.out.println("[0] Retour menu principal");
				System.out.print("Votre choix ? > ");
				choice = sc.nextLine();
				//-------------------- AJOUT LIVRE --------------------
				if(choice.equals("1")) {
					clearScreen();
					System.out.print("ISBN : ");
					int isbn = Integer.parseInt(sc.nextLine());
					System.out.print("Nom : ");
					String name = sc.nextLine();
					System.out.print("Auteur : ");
					String author = sc.nextLine();

					try {
						livreDao.insertBook(isbn, name, author);
					} catch(Exception e) {
						System.err.println(e.getMessage());
						System.out.println("### Appuyez sur ENTRER pour revenir au menu");
						sc.nextLine();
					}

					choice = "2";
				}
				//-------------------- SUPPRESSION LIVRE --------------------
				else if(choice.equals("2")) {
					clearScreen();
					System.out.print("ISBN : ");
					int isbn = Integer.parseInt(sc.nextLine());

					livreDao.deleteBook(isbn);

					choice = "2";
				}
				//-------------------- LISTE LIVRES --------------------
				else if(choice.equals("3")) {
					clearScreen();
					ArrayList<Livre> books = livreDao.getAllBooks();
					for(Livre l : books) {
						System.out.println(l);
					}
					choice = "2";
					System.out.println("### Appuyez sur ENTRER pour revenir au menu");
					sc.nextLine();
				}
			}
			//-------------------- MENU COPIES --------------------
			if(choice.equals("3")) {
				clearScreen();
				System.out.println("====== MENU COPIES ======");
				System.out.println("[1] Ajouter une copie");
				System.out.println("[2] Supprimer une copie");
				System.out.println("[0] Retour menu principal");
				System.out.print("Votre choix ? > ");
				choice = sc.nextLine();
				//-------------------- AJOUT COPIE --------------------
				if(choice.equals("1")) {
					clearScreen();
					System.out.print("ISBN : ");
					int isbn = Integer.parseInt(sc.nextLine());

					try {
						livreCopieDAO.insertBookCopy(isbn);
					} catch(Exception e) {
						System.err.println(e.getMessage());
						System.out.println("### Appuyez sur ENTRER pour revenir au menu");
						sc.nextLine();
					}

					choice = "3";
				}
				//-------------------- SUPPRESSION COPIE --------------------
				else if(choice.equals("2")) {
					clearScreen();
					System.out.print("ISBN : ");
					int isbn = Integer.parseInt(sc.nextLine());

					try {
						livreCopieDAO.removeBookCopy(isbn);
					} catch (Exception e) {
						System.err.println(e.getMessage());
						System.out.println("### Appuyez sur ENTRER pour revenir au menu");
						sc.nextLine();
					}

					choice = "3";
				}
			}
			//-------------------- MENU PRETS --------------------
			if(choice.equals("4")) {
				clearScreen();
				System.out.println("====== MENU PRETS ======");
				System.out.println("[1] Emprunter livre");
				System.out.println("[2] Retourner livre");
				System.out.println("[0] Retour menu principal");
				System.out.print("Votre choix ? > ");
				choice = sc.nextLine();
				//-------------------- EMPRUNT LIVRE --------------------
				if(choice.equals("1")) {
					clearScreen();
					System.out.print("Id utilisateur : ");
					int userId = Integer.parseInt(sc.nextLine());
					System.out.print("ISBN Livre : ");
					int isbn = Integer.parseInt(sc.nextLine());
					System.out.print("Jour : ");
					int day = Integer.parseInt(sc.nextLine());
					System.out.print("Mois : ");
					int month = Integer.parseInt(sc.nextLine());
					System.out.print("Année : ");
					int year = Integer.parseInt(sc.nextLine());

					try {
						pretDao.loanBook(userId, isbn, new Date(year-1900, month, day));
					} catch(Exception e) {
						System.err.println(e.getMessage());
						System.out.println("### Appuyez sur ENTRER pour revenir au menu");
						sc.nextLine();
					}

					choice = "4";
				}
				//-------------------- RETOUR LIVRE --------------------
				else if(choice.equals("2")) {
					clearScreen();
					System.out.print("Id utilisateur : ");
					int userId = Integer.parseInt(sc.nextLine());
					System.out.print("ISBN Livre : ");
					int isbn = Integer.parseInt(sc.nextLine());
					System.out.print("Jour : ");
					int day = Integer.parseInt(sc.nextLine());
					System.out.print("Mois : ");
					int month = Integer.parseInt(sc.nextLine());
					System.out.print("Année : ");
					int year = Integer.parseInt(sc.nextLine());

					try {
						pretDao.returnBook(userId, isbn, new Date(year-1900, month, day));
					} catch(Exception e) {
						System.err.println(e.getMessage());
						System.out.println("### Appuyez sur ENTRER pour revenir au menu");
						sc.nextLine();
					}

					choice = "4";
				}
			}
			if(choice.equals("x")) {
				System.out.println("\nFermeture de l'application...");
				System.exit(1);
			}

		}
	}

	public static void clearScreen() {  
		for(int i=0; i<20; i++) {
			System.out.println("");
		}
	} 

}

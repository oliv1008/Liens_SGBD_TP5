package view;

import java.util.Scanner;

public class Main {

	public Main() {
		Scanner sc = new Scanner(System.in);
		String choice = "0";

		while(!choice.equals("-1")) {

			if(choice.equals("0")) {
				clearScreen();
				System.out.println("====== MENU PRINCIPAL ======");
				System.out.println("[1] Gestion des utilisateurs");
				System.out.println("[2] Gestion des livres");
				System.out.println("[3] Gestion des copies");
				System.out.println("[4] Gestion des prêts");
				System.out.println("[0] Quitter l'application");
				System.out.print("Votre choix ? > ");
				choice = sc.nextLine();
			}
			if(choice.equals("1")) {
				clearScreen();
				System.out.println("====== MENU UTILISATURS ======");
				System.out.println("[1] Ajouter un utilisateur");
				System.out.println("[2] Supprimer un utilisateur");
				System.out.println("[0] Retour menu principal");
				System.out.print("Votre choix ? > ");
				choice = sc.nextLine();
				if(choice.equals("1")) {
					clearScreen();
					System.out.print("Nom : ");
					String name = sc.nextLine();
				}
			}
			
		}
	}
	
	public static void clearScreen() {  
		for(int i=0; i<10; i++) {
			System.out.println("");
		}
	} 

}

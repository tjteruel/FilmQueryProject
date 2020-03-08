package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		//app.test();
		app.launch();
	}

//	private void test() throws SQLException {
//		Film film = db.findFilmById(1);
//		System.out.println(film);
//	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		System.out.println("\t\t Welcome to the Film Database!");
		boolean menuLoop = true;
		while (menuLoop) {
			System.out.println("");
			System.out.println("\t What would you like to do? ");
			System.out.println("1. Look Up Film By ID Number. ");
			System.out.println("2. Look Up Film By Key Word. ");
			System.out.println("3. Exit. ");
			
		//TODO:fix scanner/userChoice
			switch (userChoice) {
			case 1:
				System.out.print("Please enter the Film ID: ");
				int id = input.nextInt();
				Film filmID = null;
				try {
					filmID = db.findFilmById(id);
				} catch (SQLException e) {
					System.err.println("Please enter choices 1-3. ");
				}
				if (filmID == null) {
					System.err.println("Film not found.");
				} else {
					System.out.println(filmID);
				}
				break;
			case 2:
				System.out.print("Please enter the keyword: ");
				String keyword = input.nextLine();
				List<Film> film = db.findFilmByKeyword(keyword);
				System.out.println(film);
				break;
			case 3:
				menuLoop = false;
				System.out.println("/t Thank You For Using The Database!");
				break;
			default:
				System.out.println("Please enter an option of 1-3. ");
			}
		}
	}

}

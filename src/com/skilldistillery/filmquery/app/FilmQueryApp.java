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
		// app.test();
		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		System.out.println("\t\t Welcome to the Film Database!");
		int userChoice = 0;
		boolean menuLoop = true;
		while (menuLoop) {
			System.out.println("");
			System.out.println("\t What would you like to do? ");
			System.out.println("1. Look Up Film By ID Number. ");
			System.out.println("2. Look Up Film By Key Word. ");
			System.out.println("3. Exit. ");

			try {
				userChoice = input.nextInt();
				if (userChoice < 1 || userChoice > 3) {
					System.out.println("please enter an integer between 1 and 3. ");
					startUserInterface(input);
				}
			} catch (Exception e) {
				System.out.println("Invalid input ");
				input.nextLine();
				startUserInterface(input);
			}

			switch (userChoice) {
			case 1:
				System.out.print("Please enter the Film ID: ");
				int id = input.nextInt();
				Film filmID = null;
				try {
					filmID = db.findFilmById(id);
				} catch (SQLException e1) {
					System.err.println("Please enter choices 1-3. ");
				}
				if (filmID == null) {
					System.out.println("\tFilm not found.");
				} else {
					System.out.println(filmID);
				}
				break;
			case 2:
				System.out.print("Please enter the keyword: ");
				String keyword = input.next();
				List<Film> films = db.findFilmByKeyword(keyword);
				if (films.size() > 0) {
					for (Film film : films) {
						System.out.println("Title: " + film.getTitle());
						System.out.println("Release Year: " + film.getReleaseYear());
						System.out.println("Rating: " + film.getRating());					
						System.out.println("Language: " + film.getLanguage());					
						System.out.println("Description: " + film.getDescription());	
						System.out.println("Cast: " + film.getActors());
						System.out.println("");}
				} else {
					System.out.println("\tNo Films Found With That Keyword. ");
				}
				break;
			case 3:
				menuLoop = false;
				System.out.println("\t Thank You For Using The Database!");
				break;
			default:
				System.out.println("Please enter an option of 1-3. ");
				break;
			}
		}
	}

}

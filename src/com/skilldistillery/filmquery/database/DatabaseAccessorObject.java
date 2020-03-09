package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// returns film by ID
	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String user = "student";
		String pass = "student";
		try (Connection conn = DriverManager.getConnection(URL, user, pass)) {

			String sql = "SELECT * FROM film WHERE id = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, filmId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				film = new Film();
				film.setTitle(rs.getString("title"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setRating(rs.getString("rating"));
				film.setDescription(rs.getString("description"));
				film.setId(rs.getInt("id"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setRating(rs.getString("rating"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setRentalDuration(rs.getInt("rental_duration"));
				film.setRentalRate(rs.getDouble("rental_rate"));
				film.setReplacementCost(rs.getDouble("replacement_cost"));
				film.setSpecialFeatures(rs.getString("special_features"));
				film.setActors(findActorsByFilmId(filmId));
				film.setLanguage(findLanguageById(filmId));
			}
			
			rs.close();
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;

	}

	// returns films by keyword, displays title, year, rating, description and
	// language
	public List<Film> findFilmByKeyword(String keyword) {
		String searchWord = "%" + keyword + "%";
		List<Film> films = new ArrayList<>();
		String user = "student";
		String pass = "student";
		try (Connection conn = DriverManager.getConnection(URL, user, pass)) {
			String sql = "SELECT * FROM film JOIN language ON film.language_id = language.id WHERE title like ? or description like ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, searchWord);
			pst.setString(2, searchWord);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Film film = new Film();
				film.setId(rs.getInt("id"));
				film.setTitle(rs.getString("title"));
				film.setDescription(rs.getString("description"));
				film.setReleaseYear(rs.getInt("release_year"));
				film.setLanguageId(rs.getInt("language_id"));
				film.setRentalDuration(rs.getInt("rental_duration"));
				film.setRentalRate(rs.getDouble("rental_rate"));
				film.setReplacementCost(rs.getDouble("replacement_cost"));
				film.setRating(rs.getString("rating"));
				film.setSpecialFeatures(rs.getString("special_features"));
				film.setActors(findActorsByFilmId(film.getId()));
				film.setLanguage(findLanguageById(film.getId()));
				films.add(film);
			}
			rs.close();
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;

	}

	// returns Actor by ID
	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		try (Connection conn = DriverManager.getConnection(URL, user, pass)) {
			String sql = "SELECT * FROM actor WHERE id = ?";

			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, actorId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				actor = new Actor();
				actor.setId(rs.getInt("id"));
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
			}
			rs.close();
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	// returns cast from film ID
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList();
		String user = "student";
		String pass = "student";
		try (Connection conn = DriverManager.getConnection(URL, user, pass)) {
			String sql = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id join film on film_actor.film_id = film.id where film.id = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, filmId);
			ResultSet rs = pst.executeQuery();
			Actor actor = null;
			while (rs.next()) {
				actor = new Actor();
				actor.setId(rs.getInt("id"));
				actor.setFirstName(rs.getString("first_name"));
				actor.setLastName(rs.getString("last_name"));
				actors.add(actor);
			}
			rs.close();
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;

	}

	// find language by film ID
	public String findLanguageById(int filmId) {
		String user = "student";
		String pass = "student";
		String languageName = "";
		try (Connection conn = DriverManager.getConnection(URL, user, pass)) {
			String sql = "SELECT language.name FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, filmId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				languageName = rs.getString("language.name");
			}
			rs.close();
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return languageName;
	}

}

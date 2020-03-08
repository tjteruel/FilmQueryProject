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
	
	//returns film by ID
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
			}
			rs.close();
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;

	}
	
	//returns films by keyword, displays title, year, rating, description and language
	public List<Film> findFilmByKeyword(String keyword) {
		String searchWord = "%" + keyword + "%";
		List<Film> films = new ArrayList<>();
		String user = "student";
		String pass = "student";
		try (Connection conn = DriverManager.getConnection(URL, user, pass)) {
			String sql = "SELECT * FROM film JOIN language ON film.language_id = language.id WHERE title like = ? or description like = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, searchWord);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				((Film) films).setTitle(rs.getString("title"));
				((Film) films).setReleaseYear(rs.getInt("release_year"));
				((Film) films).setRating(rs.getString("rating"));
				((Film) films).setDescription(rs.getString("description"));
			}
			rs.close();
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;

	}

	//returns Actor by ID
	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String user = "student";
		String pass = "student";
			try (Connection conn = DriverManager.getConnection(URL, user, pass)) {
				String sql = "SELECT * FROM actor WHERE id = ?";

		  PreparedStatement pst = conn.prepareStatement(sql);
		  pst.setInt(1,actorId);
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

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}

}

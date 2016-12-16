package de.hsh.dbs2.imdb.logic;

import java.util.*;

import de.hsh.dbs2.imdb.logic.dto.CharacterDTO;
import de.hsh.dbs2.imdb.logic.dto.MovieDTO;
import de.hsh.dbs2.imdb.logic.factory.CharacterFactory;
import de.hsh.dbs2.imdb.logic.factory.GenreFactory;
import de.hsh.dbs2.imdb.logic.factory.MovieFactory;
import de.hsh.dbs2.imdb.logic.factory.PersonFactory;
import de.hsh.dbs2.imdb.logic.model.Genre;
import de.hsh.dbs2.imdb.logic.model.Movie;
import de.hsh.dbs2.imdb.logic.model.MovieCharacter;

public class MovieManager {

	/**
	 * Ermittelt alle Filme, deren Filmtitel den Suchstring enthaelt.
	 * Wenn der String leer ist, sollen alle Filme zurueckgegeben werden.
	 * Der Suchstring soll ohne Ruecksicht auf Gross/Kleinschreibung verarbeitet werden.
	 * @param search Suchstring. 
	 * @return Liste aller passenden Filme als MovieDTO
	 * @throws Exception
	 */
	public List<MovieDTO> getMovieList(String search) throws Exception {
		List<MovieDTO> movieList = new ArrayList<>();
		List<Movie> movies;
		if (search.equals("")) {
			movies = MovieFactory.getAllMovie();
		} else {
			movies = MovieFactory.findByTitle(search);
		}
		
		for (Movie movie : movies) {
			MovieDTO movieDTO = getMovie(movie.getId());
			movieList.add(movieDTO);
		}
		
		return movieList;
	}

	/**
	 * Speichert die uebergebene Version des Films neu in der Datenbank oder aktualisiert den
	 * existierenden Film.
	 * Dazu werden die Daten des Films selbst (Titel, Jahr, Typ) beruecksichtigt,
	 * aber auch alle Genres, die dem Film zugeordnet sind und die Liste der Charaktere
	 * auf den neuen Stand gebracht.
	 * @param movieDTO Film-Objekt mit Genres und Charakteren.
	 * @throws Exception
	 */
	public void insertUpdateMovie(MovieDTO movieDTO) throws Exception {
		Movie movie = new Movie();
		movie.setTitle(movieDTO.getTitle());
		movie.setYear(movieDTO.getYear());
		movie.setType(movieDTO.getType());
		movie.setVersion(movieDTO.getVersion());

		if (movieDTO.getId() == null) {
			movie = MovieFactory.add(movie);
		} else {
			movie.setId(movieDTO.getId());
		}

		movie.getGenres().clear();
		Set<String> genres = movieDTO.getGenres();
		for (String s : genres) {
			Genre genre = GenreFactory.find(s);
			movie.getGenres().add(genre);
		}

		CharacterFactory.delete(movie.getId());
		List<CharacterDTO> characterDTOs = movieDTO.getCharacters();
		int index = 1;
		for (CharacterDTO cDto : characterDTOs) {
			MovieCharacter character = new MovieCharacter();
			character.setCharacter(cDto.getCharacter());
			character.setAlias(cDto.getAlias());
			character.setPosition(index++);
			character.setMovie(movie);
			character.setPerson(PersonFactory.findByName(cDto.getPlayer()).get(0));
			CharacterFactory.add(character);
		}

		MovieFactory.update(movie);
	}

	/**
	 * Loescht einen Film aus der Datenbank. Es werden auch alle abhaengigen Objekte geloescht,
	 * d.h. alle Charaktere und alle Genre-Zuordnungen.
	 * @param movieId
	 * @throws Exception
	 */
	public void deleteMovie(long movieId) throws Exception {
		CharacterFactory.delete(movieId);
		MovieFactory.delete(movieId);
	}

	public MovieDTO getMovie(long movieId) throws Exception {
		Movie movie = MovieFactory.findById(movieId);
		
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(movie.getId());
		movieDTO.setTitle(movie.getTitle());
		movieDTO.setType(movie.getType());
		movieDTO.setYear(movie.getYear());
		movieDTO.setVersion(movie.getVersion());

		for (Genre genre : movie.getGenres()) {
			movieDTO.addGenre(genre.toString());
		}

		// sort characterlist nach pos
		Set<MovieCharacter> characters = movie.getCharacters();
		int size = characters.size();
		MovieCharacter [] chs = new MovieCharacter[size];
		Arrays.sort(characters.toArray(chs), (o1, o2) -> o1.getPosition() - o2.getPosition());

		for (MovieCharacter character : chs) {
			CharacterDTO characterDTO = new CharacterDTO();
			characterDTO.setCharacter(character.getCharacter());
			characterDTO.setAlias(character.getAlias());
			characterDTO.setPlayer(character.getPerson().toString());
			movieDTO.addCharacter(characterDTO);
		}

		return movieDTO;
	}

}

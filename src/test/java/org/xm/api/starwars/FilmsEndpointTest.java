package org.xm.api.starwars;

import org.testng.annotations.Test;
import org.xm.helpers.RestApiHelper;
import org.xm.models.Character;
import org.xm.models.Film;
import org.xm.models.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.xm.helpers.RestApiHelper.getCharacter;
import static org.xm.helpers.RestApiHelper.getCharacters;

public class FilmsEndpointTest {
    private Film latestReleaseDateFilm;

    @Test(priority = 1)
    public void findTheFilmWithLatestReleaseDate() {
        List<Film> filmList = RestApiHelper.getFilms();
        List<Date> releaseDates = filmList.stream().map(s -> s.releaseDate()).collect(Collectors.toList());
        Date latestReleaseDate = releaseDates.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList()).get(0);

        this.latestReleaseDateFilm = filmList
                .stream()
                .filter(film -> film.releaseDate().equals(latestReleaseDate))
                .findFirst()
                .orElse(null);

        System.out.println("Film with latest release date is "
                + latestReleaseDateFilm.title() + "and it's release date is "
                + latestReleaseDateFilm.releaseDate());
    }

    @Test(priority = 2)
    public void findTheTallestPersonInTheLatestReleaseDateFilm() {
        List<Character> charactersList = this.latestReleaseDateFilm.characters().stream()
                .map(character -> getCharacter(character.split("/")[character.split("/").length - 1]))
                .collect(Collectors.toList());
        Integer maximumHeightCharacterForLatestReleaseFilm = charactersList
                .stream().map(character -> Integer.valueOf(character.height()))
                .sorted(Collections.reverseOrder())
                .findFirst()
                .orElse(null);

        Character theTallestCharacterForFilmWithLatestReleaseDate = charactersList
                .stream()
                .filter(character -> character.height().equals(maximumHeightCharacterForLatestReleaseFilm.toString()))
                .findFirst()
                .orElse(null);

        System.out.println("The tallest person in " + this.latestReleaseDateFilm.title() + " film is "
                + theTallestCharacterForFilmWithLatestReleaseDate.name() + " and them height is " + theTallestCharacterForFilmWithLatestReleaseDate.height());
    }

    @Test(priority = 3)
    public void findTheTallestCharacterForAllFilms() {
        Page<Character> pageWithCharacters = getCharacters("1");
        List<Character> fullListOfCharacters = new ArrayList<Character>();
        while (pageWithCharacters.next() != null) {
            fullListOfCharacters.addAll(pageWithCharacters.results());
            pageWithCharacters = getCharacters(pageWithCharacters.next().split("=")[1]);
        }
        fullListOfCharacters.addAll(pageWithCharacters.results()); // adds results from the last page

        Integer theTallestCharacterHeight = fullListOfCharacters
                .stream()
                .filter(character -> !character.height().equals("unknown"))
                .map(character -> Integer.valueOf(character.height()))
                .sorted(Collections.reverseOrder())
                .findFirst()
                .orElse(null);

        Character theTallestCharacter = fullListOfCharacters
                .stream()
                .filter(character -> character.height().equals((theTallestCharacterHeight).toString()))
                .findFirst()
                .orElse(null);

        System.out.println("The tallest person ever played in any Star Wars film is "
                + theTallestCharacter.name() + "and them height is " + theTallestCharacter.height());
    }
}

package org.xm.api.starwars;

import org.testng.annotations.Test;
import org.xm.helpers.RestApiHelper;
import org.xm.models.Character;
import org.xm.models.Film;
import org.xm.models.Page;

import java.util.*;
import java.util.stream.Collectors;

import static org.xm.helpers.RestApiHelper.getCharacter;
import static org.xm.helpers.RestApiHelper.getCharacters;

public class FilmsEndpointTest {
    private Film latestReleaseDateFilm;

    @Test(priority = 1)
    public void findTheFilmWithLatestReleaseDate() {
        List<Film> filmList = RestApiHelper.getFilms();
        this.latestReleaseDateFilm =
                filmList.stream()
                        .max(Comparator.comparing(Film::releaseDate))
                        .stream().collect(Collectors.toList())
                        .get(0);

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
        List<Character> fullListOfCharacters = new ArrayList<>();
        List<Character> charactersWithMaximumHeightOnThePage;
        while (pageWithCharacters.next() != null) {
            charactersWithMaximumHeightOnThePage = maximumHeightCharactersInTheList(pageWithCharacters.results());
            fullListOfCharacters.addAll(charactersWithMaximumHeightOnThePage);
            pageWithCharacters = getCharacters(pageWithCharacters.next().split("=")[1]);
        }
        fullListOfCharacters
                .addAll(maximumHeightCharactersInTheList(pageWithCharacters.
                        results())); // adds results from the last page

        Character theTallestCharacter = maximumHeightCharactersInTheList(fullListOfCharacters)
                .get(0);

        System.out.println("The tallest person ever played in any Star Wars film is "
                + theTallestCharacter.name() + "and them height is " + theTallestCharacter.height());
    }

    private static List<Character> maximumHeightCharactersInTheList(List<Character> listOfCharacters) {
        return listOfCharacters.stream()
                .filter(character -> !character.height().equals("unknown"))
                .max(Comparator.comparing(character -> Integer.valueOf(character.height())))
                .stream().collect(Collectors.toList());
    }
}

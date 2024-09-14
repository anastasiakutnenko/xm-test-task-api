package org.xm.api.starwars;

import org.testng.annotations.Test;
import org.xm.helpers.RestApiHelper;
import org.xm.models.Character;
import org.xm.models.Film;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.xm.helpers.RestApiHelper.getCharacter;

public class FilmsEndpointTest {
    @Test
    public void findTheFilmWithLatestReleaseDate() {

        List<Film> filmList = RestApiHelper.getFilms();
        List<Date> releaseDates = filmList.stream().map(s -> s.releaseDate()).collect(Collectors.toList());

//        List<LocalDate> localDates = releaseDates.stream().map(date ->
//                LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date))).collect(Collectors.toList());
        Date latestReleaseDate = releaseDates.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList()).get(0);
        Film filmWitLatestReleaseDate = filmList
                .stream()
                .filter(film -> film.releaseDate().equals(latestReleaseDate))
                .findFirst()
                .orElse(null);

        List<Character> charactersList = filmWitLatestReleaseDate.characters().stream()
                .map(character -> getCharacter(character.split("/")[character.split("/").length - 1]))
                .collect(Collectors.toList());
        String maximumHeightCharacter = charactersList.stream().map(character -> character.height())
                .sorted(Collections.reverseOrder())
                .findFirst()
                .orElse(null);

        System.out.println("Character with maximum height");
        System.out.println(maximumHeightCharacter);
    }
}

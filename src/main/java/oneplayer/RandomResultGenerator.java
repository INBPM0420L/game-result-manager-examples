package oneplayer;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Locale;

import com.github.javafaker.Faker;

import gameresult.OnePlayerGameResult;
import gameresult.manager.OnePlayerGameResultManager;
import gameresult.manager.json.JsonOnePlayerGameResultManager;

public class RandomResultGenerator {

    private static final Faker FAKER = new Faker(Locale.ENGLISH);

    private static OnePlayerGameResult createGameResult() {
        return OnePlayerGameResult.builder()
                .playerName(FAKER.name().firstName())
                .solved(FAKER.bool().bool())
                .numberOfMoves(FAKER.number().numberBetween(10, 50))
                .duration(Duration.ofSeconds(FAKER.number().numberBetween(10, 100)))
                .created(ZonedDateTime.now().minusMinutes(FAKER.number().numberBetween(0, 60)))
                .build();
    }

    public static void main(String[] args) throws IOException {
        OnePlayerGameResultManager manager = new JsonOnePlayerGameResultManager(Path.of("one-player-results.json"));
        for (var i = 0; i < 100; i++) {
            manager.add(createGameResult());
        }
        manager.getBestByNumberOfMoves(10).forEach(System.out::println);
    }

}

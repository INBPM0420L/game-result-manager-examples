package twoplayer;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Locale;

import com.github.javafaker.Faker;

import game.State;
import gameresult.TwoPlayerGameResult;
import gameresult.manager.TwoPlayerGameResultManager;
import gameresult.manager.json.JsonTwoPlayerGameResultManager;

public class RandomResultGenerator {

    private static final Faker FAKER = new Faker(Locale.ENGLISH);

    private final static String[] PLAYER_NAMES;

    static {
        var playerNames = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            playerNames.add(FAKER.name().firstName());
        }
        PLAYER_NAMES = playerNames.toArray(new String[0]);
    }

    private static TwoPlayerGameResult createGameResult() {
        return TwoPlayerGameResult.builder()
                .player1Name(FAKER.options().option(PLAYER_NAMES))
                .player2Name(FAKER.options().option(PLAYER_NAMES))
                .numberOfTurns(FAKER.number().numberBetween(10, 50))
                .status(FAKER.options().option(State.Status.class))
                .duration(Duration.ofSeconds(FAKER.number().numberBetween(10, 100)))
                .created(ZonedDateTime.now().minusMinutes(FAKER.number().numberBetween(0, 60)))
                .build();
    }

    public static void main(String[] args) throws IOException {
        TwoPlayerGameResultManager manager = new JsonTwoPlayerGameResultManager(Path.of("two-player-results.json"));
        for (var i = 0; i < 100; i++) {
            manager.add(createGameResult());
        }
        manager.getPlayersWithMostWins(10).forEach(System.out::println);
    }

}


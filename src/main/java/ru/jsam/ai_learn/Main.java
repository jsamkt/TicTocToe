package ru.jsam.ai_learn;

import ru.jsam.ai_learn.platforms.Platform;
import ru.jsam.ai_learn.platforms.TerminalPlatform;
import ru.jsam.ai_learn.platforms.UIPlatform;
import ru.jsam.ai_learn.users.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


        int gameNum = 1000;

        Platform platform = new UIPlatform();

        User userX = new AIBotUser("/home/user/model", SIGN.X, true); new RealUser(platform);
        User userO = new AIBotUser("/home/shamil/dl4j-examples-data/model", SIGN.O, false);
        Board board = new BoardImpl();

        Game game = new Game(platform, board, userX, userO);

        for (int i = 0; i < gameNum; i++) {
            game.runGame();
            game.showResult();
        }
        game.showResult();
    }
}

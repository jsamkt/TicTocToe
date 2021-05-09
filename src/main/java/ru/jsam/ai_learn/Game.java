package ru.jsam.ai_learn;

import ru.jsam.ai_learn.platforms.Platform;
import ru.jsam.ai_learn.users.User;

public class Game {


    private int xWin = 0;
    private int oWin = 0;
    private int standoff = 0;

    private final Platform platform;
    private final Board board;

    private User userX;
    private final User userO;

    public Game(Platform platform, Board board, User userX, User userO) {
        this.platform = platform;
        this.board = board;
        this.userX = userX;
        this.userO = userO;
    }

    public void setUserX(User user) {
        this.userX = user;
    }

    public void runGame() {
        board.init();
        platform.draw(board.getState());


        boolean xTurning = true;
        while (!board.isFinish()) {
            SIGN turnOrder = xTurning ? SIGN.X : SIGN.O;
            User turnOrderUser = xTurning ? userX : userO;

            platform.inviteUserToTurn(turnOrder);
            int turnResult = getUsersTurn(turnOrderUser);
            board.setState(turnResult, turnOrder);
            platform.draw(board.getState());

            xTurning = !xTurning;
        }

        if (board.isFinish()) {
            SIGN winner = board.getWinner();
            if (winner != null) {
                if (winner.equals(SIGN.X)) {
                    platform.winX();
                    userX.win();
                    userO.lose();
                    xWin++;
                } else if (winner.equals(SIGN.O)) {
                    userX.lose();
                    userO.win();
                    platform.winO();
                    oWin++;
                }
            } else {
                platform.standOff();
                userX.standoff();
                userO.standoff();
                standoff++;
            }

        }
    }


    public void showResult() {
        platform.showResult(xWin, oWin, standoff);
    }

    private boolean validateIsIndexIsFree(int index) {
        return index != -1 && board.getState(index) == SIGN.SPACE;
    }

    private int getUsersTurn(User user) {
        int index = -1;
        while (index == -1) {
            index = user.makeTurn(board.getState());
            if (!validateIsIndexIsFree(index)) {
                index = -1;
            }
        }

        return index;
    }
}

package ru.jsam.ai_learn.platforms;

import ru.jsam.ai_learn.SIGN;

public interface Platform {

    void inviteUserToTurn(SIGN t);
    void draw(SIGN[] board);
    int getUserTurn();
    void showResult(int xWin, int oWin, int standoff);

    void winX();
    void winO();
    void standOff();

}

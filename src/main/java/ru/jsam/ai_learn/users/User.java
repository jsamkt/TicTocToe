package ru.jsam.ai_learn.users;

import ru.jsam.ai_learn.SIGN;

public interface User {
    int makeTurn(SIGN[] board);

    void win();
    void lose();
    void standoff();
}

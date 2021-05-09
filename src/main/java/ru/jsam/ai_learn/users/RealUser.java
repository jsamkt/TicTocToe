package ru.jsam.ai_learn.users;

import ru.jsam.ai_learn.SIGN;
import ru.jsam.ai_learn.platforms.Platform;

public class RealUser implements User {

    private final Platform platform;

    public RealUser(Platform platform) {
        this.platform = platform;
    }

    public int makeTurn(SIGN[] board) {
        return platform.getUserTurn();
    }

    @Override
    public void win() {

    }

    @Override
    public void lose() {

    }

    @Override
    public void standoff() {

    }
}

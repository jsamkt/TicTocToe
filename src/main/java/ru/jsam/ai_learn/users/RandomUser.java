package ru.jsam.ai_learn.users;

import ru.jsam.ai_learn.SIGN;

import java.util.List;
import java.util.Random;

public class RandomUser extends BotUser {
    @Override
    public int makeTurn(SIGN[] board) {
        int[] binaryBoard = getBinaryBoard(board);
        List<Integer> indexes = getFreeIndexes(binaryBoard);

        return indexes.get(new Random().nextInt(indexes.size()));
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

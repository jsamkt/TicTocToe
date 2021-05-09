package ru.jsam.ai_learn.users;

import ru.jsam.ai_learn.SIGN;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AlgorithmBotUser extends BotUser {

    private final SIGN myT;
    private final SIGN apponentT;

    public AlgorithmBotUser(SIGN t) {
        myT = t;
        apponentT = t.getApposite();
    }

    @Override
    public int makeTurn(SIGN[] board) {
        int[] binaryBoard = getBinaryBoard(board);

        int finish = getTurnForStrategy(binaryBoard, myT);
        if (finish != -1) {
            return finish;
        }

        int protect = getTurnForStrategy(binaryBoard, apponentT);
        if (protect != -1) {
            return protect;
        }

        List<Integer> freeIndexes = getFreeIndexes(binaryBoard);
        return freeIndexes.get(new Random().nextInt(freeIndexes.size()));
    }

    @Override
    public void win() {
        return;
    }

    @Override
    public void lose() {
        return;
    }

    @Override
    public void standoff() {

    }


    private int getTurnForStrategy(int[] board, SIGN t) {
        return Stream.of(
            new int[]{1, 2, 3},
            new int[]{4, 5, 6},
            new int[]{7, 8, 9},

            new int[]{1, 4, 7},
            new int[]{2, 5, 8},
            new int[]{3, 6, 9},

            new int[]{1, 5, 9},
            new int[]{3, 5, 7}
        )
            .map(arr -> IntStream.of(arr).map(i -> i - 1).toArray())
            .filter(arr -> IntStream.of(arr).filter(i -> board[i] == t.getIntValue()).count() == 2)
            .map(arr -> getFreeIndex(arr, board))
            .filter(i -> i != -1)
            .findFirst()
            .orElse(-1);
    }

    private int getFreeIndex(int[] arr, int[] board) {
        for (int i = 0; i < arr.length; i++) {
            if (board[arr[i]] == 0) {
                return arr[i];
            }
        }
        return -1;
    }
}

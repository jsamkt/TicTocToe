package ru.jsam.ai_learn;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class BoardImpl implements Board {

    private static final int[][] WIN_STATE = new int[][]
        {
            new int[]{0, 1, 2},
            new int[]{3, 4, 5},
            new int[]{6, 7, 8},
            new int[]{0, 4, 8},
            new int[]{2, 4, 6},
            new int[]{0, 3, 6},
            new int[]{1, 4, 7},
            new int[]{2, 5, 8}
        };

    private SIGN[] state = new SIGN[9];

    @Override
    public void init() {
        state = new SIGN[9];
        Arrays.fill(state, SIGN.SPACE);
    }

    @Override
    public SIGN[] getState() {
        return state;
    }

    @Override
    public SIGN getState(int index) {
        return state[index];
    }

    @Override
    public void setState(int index, SIGN t) {
        state[index] = t;
    }

    @Override
    public SIGN getWinner() {
        if (!isFinish()) {
            return null;
        }
        return Stream.of(WIN_STATE)
            .filter(this::isArrayFinish)
            .findFirst()
            .map(arr -> state[arr[0]])
            .orElse(null);
    }

    @Override
    public boolean isFinish() {
        return
            Stream.of(WIN_STATE)
                .anyMatch(this::isArrayFinish)
                || Stream.of(state)
                .allMatch(s -> s != SIGN.SPACE);
    }

    private boolean isArrayFinish(int[] arr) {
        return Objects.equals(state[arr[0]], state[arr[1]])
            && Objects.equals(state[arr[0]], state[arr[2]]) && state[arr[0]] != SIGN.SPACE;
    }
}

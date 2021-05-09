package ru.jsam.ai_learn.users;

import org.nd4j.common.primitives.Pair;
import ru.jsam.ai_learn.SIGN;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MinMaxAlgorithmBotUser extends BotUser {

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

    private final SIGN myT;
    private final SIGN apnT;

    public MinMaxAlgorithmBotUser(SIGN t) {
        myT = t;
        apnT = t.getApposite();
    }


    @Override
    public int makeTurn(SIGN[] board) {
        int[] binaryBoard = getBinaryBoard(board);

        Integer result = getFreeIndexes(binaryBoard)
            .stream()
            .map(i -> Pair.of(i, getMinMaxAlgorithmTurns(binaryBoard, i, myT)))
            .max(Comparator.comparing(Pair::getValue))
            .get()
            .getKey();

        return result;
    }

    private Integer getMinMaxAlgorithmTurns(int[] board, int index, SIGN orderT) {
        int[] new_board = Arrays.copyOf(board, board.length);
        new_board[index] = orderT.getIntValue();

        if (isFinish(new_board)) {
            int winner = getWinner(new_board);
            if (winner == myT.getIntValue()) {
                return 2;
            } else if (winner == apnT.getIntValue()) {
                return 0;
            } else {
                return 1;
            }
        }


        SIGN nextOrderD = orderT.getApposite();

        List<Integer> indexes = getFreeIndexes(new_board);
        if (nextOrderD == apnT) {
            return indexes
                .stream()
                .map(i -> getMinMaxAlgorithmTurns(new_board, i, nextOrderD))
                .min(Integer::compareTo)
                .get();
        } else {
            return indexes
                .stream()
                .map(i -> getMinMaxAlgorithmTurns(new_board, i, nextOrderD))
                .max(Integer::compareTo)
                .get();
        }

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


    private int getWinner(int[] board) {
        if (!isFinish(board)) {
            return 0;
        }
        return Stream.of(WIN_STATE)
            .filter(arr -> isArrayFinish(arr, board))
            .findFirst()
            .map(arr -> board[arr[0]])
            .orElse(0);
    }

    public boolean isFinish(int[] board) {
        return
            Stream.of(WIN_STATE)
                .anyMatch(arr -> isArrayFinish(arr, board))
                || IntStream.of(board)
                .allMatch(i -> i != 0);
    }

    private boolean isArrayFinish(int[] arr, int[] board) {
        return Objects.equals(board[arr[0]], board[arr[1]])
            && Objects.equals(board[arr[0]], board[arr[2]]) && board[arr[0]] != 0;
    }
}

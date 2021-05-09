package ru.jsam.ai_learn.users;

import ru.jsam.ai_learn.SIGN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BotUser implements User {

    List<Integer> getFreeIndexes(int[] board){
        List<Integer> indexes = new ArrayList<>();
        int index = -1;
        do {
            index = getNextFreePlaceIndex(board, index + 1);
            if (index != -1) {
                indexes.add(index);
            }
        } while (index != -1);

        return indexes;
    }

    int[] getBinaryBoard(SIGN[] board) {
        List<Integer> list = Stream.of(board)
            .map(SIGN::getIntValue)
            .collect(Collectors.toList());
        int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    private static int getNextFreePlaceIndex(int[] board, int index) {
        int _index = Math.max(index, 0);
        for (int i = _index; i < board.length; i++) {
            if (board[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}

package ru.jsam.ai_learn.platforms;

import ru.jsam.ai_learn.SIGN;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TerminalPlatform implements Platform {

    private final Scanner scanner;

    public TerminalPlatform() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void inviteUserToTurn(SIGN t) {
        System.out.println(t.getStringValue().toUpperCase() + " take your turn");
    }

    @Override
    public void draw(SIGN[] board) {
        for (int i = 0; i < 9; i += 3) {
            String line = IntStream.range(i, i + 3)
                .mapToObj(j -> board[j] == null ? (" ") : board[j].getStringValue())
                .collect(Collectors.joining("|"));
            System.out.println(line);
        }
    }

    @Override
    public int getUserTurn() {
        try {
            return Integer.parseInt(scanner.next()) - 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void showResult(int xWin, int oWin, int standoff) {
        System.out.println("GAME finish");
        System.out.println("X scores : " + xWin);
        System.out.println("O scores : " + oWin);
        System.out.println("Standoffs: " + standoff);
    }

    public void winX() {
        System.out.println("Winner is: X");

    }

    public void winO() {
        System.out.println("Winner is: O");
    }

    public void standOff() {
        System.out.println("Standoff");
    }
}

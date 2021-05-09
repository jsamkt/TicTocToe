package ru.jsam.ai_learn;

public interface Board {

    void init();

    SIGN[] getState();

    SIGN getState(int index);

    void setState(int index, SIGN t);

    SIGN getWinner();

    boolean isFinish();
}

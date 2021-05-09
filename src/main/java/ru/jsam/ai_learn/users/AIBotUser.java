package ru.jsam.ai_learn.users;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.primitives.Pair;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.shade.guava.primitives.Floats;
import ru.jsam.ai_learn.AI;
import ru.jsam.ai_learn.SIGN;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AIBotUser extends BotUser {
    private int EPOCH_NUM = 1;

    private final File modelFile;
    private final boolean teaching;
    private final MultiLayerNetwork model;
    private final SIGN side;
    private final List<int[]> turns;

    public AIBotUser(String file, SIGN side, boolean teaching) throws IOException {
        modelFile = new File(file + side);
        this.model = AI.createMultiLayerNetwork(modelFile);
        model.init();
        this.side = side;
        turns = new ArrayList<>();
        this.teaching = teaching;
    }

    public int makeTurn(SIGN[] board) {
        int[] binaryBoard = getBinaryBoard(board);
        turns.add(binaryBoard);
        return tryTurn(binaryBoard, side, model);
    }

    private List<DataSet> getDataSets(int[] intLabels) {
        return turns.stream()
            .map(arr -> new DataSet(
                Nd4j.create(
                    Floats.toArray(
                        IntStream.of(arr).mapToObj(i -> (float) i).collect(Collectors.toList())
                    ),
                    1, arr.length
                ),
                Nd4j.create(
                    Floats.toArray(IntStream.of(intLabels).mapToObj(i -> (float) i).collect(Collectors.toList())),
                    1, intLabels.length
                )
            )).collect(Collectors.toList());
    }

    @Override
    public void win() {
        if (!teaching) {
            return;
        }
        List<DataSet> dataSets = getDataSets(new int[]{0, 1});
        for (int i = 0; i < EPOCH_NUM; i++) {
            for (int j = 0; j < dataSets.size(); j++) {
                for (int z = 0; z <= j; z++) {
                    model.fit(dataSets.get(j));
                }
            }
        }
        turns.clear();
        try {

            model.save(modelFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lose() {
        if (!teaching) {
            return;
        }
        List<DataSet> dataSets = getDataSets(new int[]{1, 0});
        for (int i = 0; i < EPOCH_NUM; i++) {
            for (int j = 0; j < dataSets.size(); j++) {
                for (int z = 0; z <= j; z++) {
                    model.fit(dataSets.get(j));
                }
            }
        }
        turns.clear();
        try {
            model.save(modelFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void standoff() {
        if (!teaching) {
            return;
        }
        List<DataSet> dataSets = getDataSets(new int[]{0, 1});
        for (int i = 0; i < EPOCH_NUM; i++) {
            for (int j = 0; j < dataSets.size(); j++) {
                model.fit(dataSets.get(j));
            }
        }
        turns.clear();
        try {
            model.save(modelFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int tryTurn(int[] board, SIGN sign, MultiLayerNetwork model) {
        List<Integer> indexes = getFreeIndexes(board);

        Integer resultIndex = indexes
            .stream()
            .map(i -> {
                int[] _board = Arrays.copyOf(board, board.length);
                _board[i] = sign.getIntValue();

                INDArray in = mapBoardToINDArray(_board);
                INDArray out = model.output(in);

                float result = out.getFloat(0, 1);
                return Pair.of(i, result);
            })
            .max(Comparator.comparing(Pair::getRight))
            .map(Pair::getFirst)
            .orElse(-1);

        if (resultIndex == -1) {
            throw new IllegalStateException("Can't make a turn");
        }

        return resultIndex;
    }

    private int randomTurn(int[] board) {
        List<Integer> indexes = getFreeIndexes(board);
        return indexes.get(new Random().nextInt(indexes.size()));
    }


    private static INDArray mapBoardToINDArray(int[] board) {
        float[] _board = new float[board.length];
        for (int i = 0; i < board.length; i++) {
            _board[i] = board[i];
        }
        return Nd4j.create(_board, 1, 9);
    }

    private static boolean isPositive(INDArray out) {
        return getMaxIndex(out) == 1;
    }

    private static int getMaxIndex(INDArray out) {
        if (out.getFloat(0, 0) > out.getFloat(0, 1)) {
            return 0;
        } else {
            return 1;
        }
    }
}

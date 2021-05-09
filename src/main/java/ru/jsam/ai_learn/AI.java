package ru.jsam.ai_learn;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;

public class AI {

    public static MultiLayerNetwork createMultiLayerNetwork(File modelFile) throws IOException {
        long rngSeed = 123;
        double learningRate = 0.001;
        double momentum = .9;
        int numIn = 9;
        int numOut = 2;
        int numHid = 1000;


        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(rngSeed)
            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            .weightInit(WeightInit.XAVIER)
            .updater(new Adam(learningRate))
            .list()
            .layer(new DenseLayer.Builder().nIn(numIn).nOut(numHid).activation(Activation.RELU).build())
            .layer(new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD).activation(Activation.SOFTMAX).nOut(numOut).build())
            .build();

        MultiLayerNetwork model = modelFile.exists() ? MultiLayerNetwork.load(modelFile, true) : new MultiLayerNetwork(conf);
        model.init();
        model.addListeners(new ScoreIterationListener(10));

        return model;
    }

}

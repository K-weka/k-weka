import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.Random;


public class MLP {

    public static void main(String[] args) {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("fertility_Diagnosis.arff");
            Instances instances = source.getDataSet(9);

            Evaluation eval;

            MultilayerPerceptron mlp = new MultilayerPerceptron();
            mlp.setLearningRate(0.3);
            mlp.setMomentum(0.2);
            mlp.setTrainingTime(500);
            for (int i = 4; i < 10; i++) {
                eval=new Evaluation(instances);
                mlp.setHiddenLayers(i+"");
                eval.crossValidateModel(mlp, instances,10,new Random(1));
                System.out.println("Camadas ocultas = "+i);
                System.out.println("Acurácia: "+(100-(eval.errorRate()*100.0))+"%");

            }


        }catch (Exception ignored){
            ignored.printStackTrace();
        }



    }
}

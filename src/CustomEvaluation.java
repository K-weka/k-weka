import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;

import java.util.Random;

/**
 * Created by allan on 26/02/16.
 */
public class CustomEvaluation extends Evaluation {


    public CustomEvaluation(Instances data) throws Exception {
        super(data);
    }



    public String manyEvaluations(Instances dataSet,Classifier classifier) throws Exception {

        String saida="";
        Instances iTeste;
        Instances iTreinamento;
        Instances instances = dataSet.resample(new Random(1));

        for(int i=2;i<20;i++){

            for (int j=1;j<i;j++){

                iTeste = instances.testCV(i, j);
                iTreinamento = instances.trainCV(i, j);
                classifier.buildClassifier(iTreinamento);
                super.evaluateModel(classifier,iTeste);
                saida+="\nTeste: "+iTeste.numInstances()+" , Treinamento: "+iTreinamento.numInstances();
                saida+="\nTaxa de Erro: "+ super.errorRate()+"\n";

            }

        }


        return saida;
    }
}

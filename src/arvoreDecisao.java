import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.Random;

/**
 * Created by allan on 26/02/16.
 */
public class arvoreDecisao {
    public static void main(String[] args) {

        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("fertility_Diagnosis.arff");
            Instances instances = source.getDataSet(9);

            instances = instances.resample(new Random(1));


            Instances iTeste = instances.testCV(5, 2);
            System.out.println(iTeste.numInstances() + " Cados de Teste");

            Instances iTreinamento = instances.trainCV(5, 2);
            System.out.println(iTreinamento.numInstances() + " Casos de treinamento");
            Evaluation eval=new Evaluation(instances);

            J48 arvore = new J48();
            arvore.setConfidenceFactor(0.4f);
            arvore.setReducedErrorPruning(true);
            arvore.setBinarySplits(false);
            arvore.setCollapseTree(false);



            arvore.buildClassifier(iTreinamento);

            eval.evaluateModel(arvore,iTeste);
//            CustomEvaluation myEval = new CustomEvaluation(instances);
//            System.out.println(myEval.manyEvaluations(instances,arvore));



            System.out.println(eval.toSummaryString());

        }catch (Exception ignored){
            ignored.printStackTrace();
        }

    }
}

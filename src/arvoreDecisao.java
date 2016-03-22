import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.Random;


public class arvoreDecisao {
    public static void main(String[] args) {

        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("fertility_Diagnosis.arff");
            Instances instances = source.getDataSet(9);

            Evaluation eval=new Evaluation(instances);

            J48 arvore = new J48();
            arvore.setConfidenceFactor(0.1f);
            arvore.setReducedErrorPruning(false);
            arvore.setBinarySplits(false);
            arvore.setCollapseTree(false);
            arvore.setUseLaplace(false);
            arvore.setUseMDLcorrection(true);
            arvore.setUnpruned(true);
            arvore.setCollapseTree(false);
            arvore.setReducedErrorPruning(false);
            arvore.setSubtreeRaising(false);
            arvore.setNumFolds(30);




            arvore.buildClassifier(instances);
            eval.crossValidateModel(arvore,instances,10,new Random(1));




            System.out.println(eval.toSummaryString());
            System.out.println(eval.toMatrixString());

        }catch (Exception ignored){
            ignored.printStackTrace();
        }

    }
}

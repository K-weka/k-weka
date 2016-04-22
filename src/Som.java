import weka.clusterers.ClusterEvaluation;

import weka.clusterers.SelfOrganizingMap;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.util.Random;

/**
 * Created by allan on 22/04/16.
 */
public class Som {
    public static void main(String[] args) {
    try {

        ConverterUtils.DataSource source = new ConverterUtils.DataSource("fertility_Diagnosis.arff");
        Instances instances = source.getDataSet();
        instances.deleteAttributeAt(9);



        SelfOrganizingMap som = new SelfOrganizingMap();

        som.setConvergenceEpochs(1000);

        som.setLearningRate(0.3);
        som.setOrderingEpochs(50);


        som.setWidth(2);
        som.setCalcStats(true);
        som.buildClusterer(instances);
//        System.out.println(som.getClusters());


//        ClusterEvaluation eval = new ClusterEvaluation();
//
//        eval.setClusterer(som);
//
//        eval.evaluateClusterer(instances);
//
//        System.out.println(eval.clusterResultsToString());
        System.out.println(som.toString());


//        System.out.println(som.toString());












    }catch (Exception ignored){
        ignored.printStackTrace();
    }

}
}

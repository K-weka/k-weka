
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.lazy.IBk;

import weka.core.*;
import weka.core.converters.ConverterUtils;
import weka.core.neighboursearch.LinearNNSearch;
import java.util.ArrayList;
import java.util.Random;
public class KNN {


    public static void main(String[] args) {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("fertility_Diagnosis.arff");
            Instances instances = source.getDataSet(9);

            instances= instances.resample(new Random(1));



            Instances iTeste = instances.testCV(4,2);
            System.out.println(iTeste.numInstances() + " Cados de Teste");

            Instances iTreinamento = instances.trainCV(4,2);
            System.out.println(iTreinamento.numInstances() + " Casos de treinamento");



            //Recebe o conjunto Total para avaliação
//            Evaluation eval=new Evaluation(instances);
            System.out.println(instances.numInstances()+" Total de instancias");

            IBk i = new IBk();//Algoritmo que será utilizado
            LinearNNSearch search;

            i.setKNN(1);

            ArrayList<NormalizableDistance> listaDistancias = new ArrayList<NormalizableDistance>();
            listaDistancias.add(new EuclideanDistance());
            listaDistancias.add(new ChebyshevDistance());
            listaDistancias.add(new ManhattanDistance());
            listaDistancias.add(new MinkowskiDistance());



            Evaluation eval;
                for (NormalizableDistance n : listaDistancias) {
                    eval=new Evaluation(instances);
//                    search = new LinearNNSearch();
//                    search.setDistanceFunction(n);
//                    i.setNearestNeighbourSearchAlgorithm(search);
                    i.getNearestNeighbourSearchAlgorithm().setDistanceFunction(n);

                    i.buildClassifier(iTreinamento);//insere a base de treinamento no classificador
                    eval.evaluateModel(i, iTeste);//Executa a avaliação
                    System.out.println("Algoritmo"+ n.getClass());
                    System.out.println(eval.errorRate());
//                    System.out.println(eval.toSummaryString());
//                    System.out.println(eval.toClassDetailsString());
                }





//            System.out.println(i.toString());

//            System.out.println(eval.toClassDetailsString());



//            Resample.runFilter(new Resample(),new String[]{"-i","fertility_Diagnosis.arff","-o","src/saida.arff","-Z","15"});






//            Resample.runFilter(new Discretize(), new String[]{"-i", "entrada.arff","-o","src/saida.arff","-c","first"});

//            IBk i = new IBk();
//
//            i.setKNN(3);
//            i.buildClassifier(instances);

//            System.out.println(i.getRevision());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
//LinearNNSearch knn = new LinearNNSearch(instances);
//Instances nearestInstances= knn.kNearestNeighbours(instances.firstInstance(),3);
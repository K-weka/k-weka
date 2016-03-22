
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

            System.out.println(instances.numInstances()+" Total de instancias");

            IBk i = new IBk();//Algoritmo que será utilizado

            int k=3;
            i.setKNN(k);
            System.out.println("Valor de K configurado como sendo = "+k+"\n");

            ArrayList<NormalizableDistance> listaDistancias = new ArrayList<NormalizableDistance>();
            listaDistancias.add(new EuclideanDistance());
            listaDistancias.add(new ChebyshevDistance());
            listaDistancias.add(new ManhattanDistance());
            listaDistancias.add(new MinkowskiDistance());



            Evaluation eval;
                for (NormalizableDistance n : listaDistancias) {
                    eval=new Evaluation(instances);

                    i.getNearestNeighbourSearchAlgorithm().setDistanceFunction(n);

                    i.buildClassifier(instances);//insere a base de treinamento no classificador

                      eval.crossValidateModel(i,instances,10,new Random(1));
                    System.out.println("Algoritmo de : "+ n.getClass());
                    System.out.println(100.0-(eval.errorRate()*100));


                }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

package br.com.ufal.classificacao;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

//Algoritmo j48 para C4.5
public class ArvoreDecisao {
	public static void main(String[] args) {
		try {
			// carrega arquivo
			Instances data = DataSource.read("data/fertility_Diagnosis.arff");

			// usa ultimo atributo como classe
			data.setClassIndex(data.numAttributes() - 1);
			
			// randomiza o dataset
			data.randomize(new java.util.Random());

			// Percent split
			double percent = 90.0;
			
			// casos de treinamento
			int trainSize = (int) Math.round(data.numInstances() * percent
					/ 100);
			
			Instances train = new Instances(data, 0, trainSize);

			// casos de teste
			int testSize = data.numInstances() - trainSize;
			Instances test = new Instances(data, trainSize, testSize);
			
			Classifier j48 = new J48();
			j48.buildClassifier(train);

			Evaluation e = new Evaluation(test);
			e.evaluateModel(j48, test);

			// printa resultados
			String s = e.toSummaryString();
			System.out.println(s);
			s = e.toMatrixString();
			System.out.println(s);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
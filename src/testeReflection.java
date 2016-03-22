import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by allan on 29/02/16.
 */
public class testeReflection {

    public ArrayList<Method> getBooleanMethods(Method[] metodos) {
        ArrayList<Method> boolMetodos = new ArrayList<Method>();
        for (Method f : metodos) {
            if (f.getParameterCount() == 1 && f.getParameterTypes()[0] == boolean.class) {
                boolMetodos.add(f);

            }
        }
        return boolMetodos;
    }

    public ArrayList<Method> getMethods(Method[] metodos, Class c) {
        ArrayList<Method> boolMetodos = new ArrayList<Method>();
        for (Method f : metodos) {
            if (f.getParameterCount() == 1 && f.getParameterTypes()[0] == c) {
                boolMetodos.add(f);

            }
        }
        return boolMetodos;
    }

    public J48 resetObject(J48 j48,ArrayList<Method> metodos){
        for(Method m1: metodos){
            try {
                m1.invoke(j48,false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return j48;
    }
    public static void main(String[] args) {
        J48 j48 = new J48();
        ConverterUtils.DataSource source = null;
        try {
            source = new ConverterUtils.DataSource("fertility_Diagnosis.arff");
            Instances instances = source.getDataSet(9);


//            instances = instances.resample(new Random(1));


            Instances iTeste = instances.testCV(4, 2);
            System.out.println(iTeste.numInstances() + " Cados de Teste");

            Instances iTreinamento = instances.trainCV(4, 2);
            System.out.println(iTreinamento.numInstances() + " Casos de treinamento");

            Evaluation eval;


            testeReflection t = new testeReflection();
            ArrayList<Method> m = t.getBooleanMethods(J48.class.getMethods());


            double acuracia;

        J48 temp;
        Map<Double,ArrayList<Method>> resultado = new TreeMap<>();
        ArrayList<Method> tempArr;
        int N = m.size();
        int allmasks = (1 << N);
        for (int i = 1; i < allmasks; i++) {
            temp = t.resetObject(j48, m);
            tempArr=new ArrayList<Method>();
            temp.setConfidenceFactor(0.1f);
//            temp.setMinNumObj(2);
            temp.setNumFolds(2);
            String sub = "";
            eval = new Evaluation(instances);
            for (int j = 0; j < N; j++) {
                if ((i & (1 << j)) > 0) {
                    try {
                        tempArr.add(m.get(j));
                        m.get(j).invoke(temp, true);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }

            }
            temp.buildClassifier(iTreinamento);
//            eval.evaluateModel(temp,iTeste);
            eval.crossValidateModel(temp,instances,10,new Random(1));
            acuracia =(100.0f - (eval.errorRate() * 100.0f));
//            if (acuracia>=89.0f){
//                System.out.println(tempArr);
//            }
            if(resultado.containsKey(acuracia) && resultado.get(acuracia).size()>tempArr.size()){
                resultado.replace(acuracia,tempArr);
            }else if(!resultado.containsKey(acuracia)){
                resultado.put(acuracia,tempArr);
            }

        }

            for(double d : resultado.keySet()){
                System.out.println(d+" | "+resultado.get(d));
            }

            System.out.println(allmasks);
    }catch (Exception e) {
            e.printStackTrace();
        }




    }
}

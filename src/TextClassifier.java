import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by wesley150 on 19/09/17.
 */

public class TextClassifier {
    private String train_folder;
    private String test_folder;
    private Classifier naive;
    private StringToWordVector filter;
    private Instances train;
    private Instances test;
    private Instances test2;

    TextClassifier(String train_folder,String test_folder){
        this.test_folder = test_folder;
        this.train_folder = train_folder;
        naive = new NaiveBayes();
    }

    public void CreateFilter(){
        StringToWordVector filter_temp = new StringToWordVector();
        filter_temp.setWordsToKeep(1000000);
        filter_temp.setTFTransform(true);
        filter_temp.setLowerCaseTokens(true);
        filter_temp.setOutputWordCounts(true);
        this.filter = filter_temp;
    }
    public void CreateTraining() throws Exception {
        Instances train_temp = new Instances(new BufferedReader(new FileReader(train_folder)));
        train_temp.setClassIndex(1);
        filter.setInputFormat(train_temp);
        train_temp = Filter.useFilter(train_temp, filter);
        this.train = train_temp;
    }
    public void CreateTest() throws Exception {
        Instances test_temp = new Instances(new BufferedReader(new FileReader(test_folder)));
        test_temp.setClassIndex(1);
        Instances test2 = Filter.useFilter(test_temp, filter);
        this.test2 =test2;
        this.test = test_temp;
        naive.buildClassifier(train);
    }
    public void ShowConsole() throws Exception {
        for(int i=0; i<test2.numInstances(); i++) {
            System.out.println(test.instance(i));
            double index = naive.classifyInstance(test2.instance(i));
            double probs[] = naive.distributionForInstance(test2.instance(i));
            String className = train.classAttribute().value((int)index);
            System.out.println("Chance de ser pos:" + probs[0]);
            System.out.println("Chance de ser neg:" + probs[1]);
            System.out.println("Chance de ser neutro:" + probs[2]);
            System.out.println(className);
        }
    }
    public int[] GetClassifierCount() throws Exception {
        int qt_neutro = 0, qt_positivo = 0, qt_negativo = 0;
        int[] ClassifierCount = new int[3];
        BufferedWriter buffWrite2 = new BufferedWriter(new FileWriter("base_dados/negative_comments.txt"));
        buffWrite2.append("");
        buffWrite2.close();
        for(int i=0; i<test2.numInstances(); i++) {
            double index = naive.classifyInstance(test2.instance(i));
            String className = train.classAttribute().value((int)index);
            if(className.equals("negativo")){
                qt_negativo++;
                BufferedWriter buffWrite = new BufferedWriter(new FileWriter("base_dados/negative_comments.txt",true));
                buffWrite.append(test.instance(i)+"\n");
                buffWrite.close();
            }
            else if(className.equals("positivo")){
                qt_positivo++;
            }
            else{
                qt_neutro++;
            }
        }
        ClassifierCount[0] = qt_positivo;
        ClassifierCount[1] = qt_negativo;
        ClassifierCount[2] = qt_neutro;
        return ClassifierCount;
    }
    public double GetPrecision() throws Exception {
        String [] comparador = new String[11];
        int qt_acerto = 0;
        FileReader arq = new FileReader("base_dados/array_teste.txt");
        BufferedReader lerArq = new BufferedReader(arq);
        int indice = 0;
        String linha = lerArq.readLine();
        while (linha != null) {
            comparador[indice] = linha;
            linha = lerArq.readLine();
            indice++;
        }
        arq.close();


        Instances precicion_temp = new Instances(new BufferedReader(new FileReader("base_dados/precision.arff")));
        precicion_temp.setClassIndex(1);
        Instances precision2 = Filter.useFilter(precicion_temp, filter);
        naive.buildClassifier(train);

        for(int i=0; i<precision2.numInstances(); i++) {
            double index = naive.classifyInstance(precision2.instance(i));
            String className = train.classAttribute().value((int)index);
            if(className.equals(comparador[i])){
                qt_acerto++;
            }
        }

        double precision_prob;
        precision_prob = (double)qt_acerto/11;
        return precision_prob;
    }

}

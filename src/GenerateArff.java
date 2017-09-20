import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by wesley150 on 19/09/17.
 */
public class GenerateArff {
    String caminho;

    GenerateArff(String caminho) throws IOException {
        BufferedWriter buffWrite2 = new BufferedWriter(new FileWriter(caminho));
        buffWrite2.append("");
        buffWrite2.close();
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(caminho,true));
        buffWrite.append("@relation comentarios\n"+"@attribute comment string\n" +
                "@attribute classe {positivo, negativo, neutro}\n"+"@data\n");
        buffWrite.close();
        this.caminho =caminho;
    }
    void WriteArff(String key, String value)throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(caminho,true));
        buffWrite.append("\""+key+"\""+","+value+"\n");
        buffWrite.close();
    }
}

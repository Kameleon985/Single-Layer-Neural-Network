
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkLayer {
    List<String>classes;
    List<Perceptron>classifiers;
    double accuracy;


    public NeuralNetworkLayer(File testData,File trainData,double alpha,double k){
        classes = new ArrayList<>();
        classifiers = new ArrayList<>();

        DataReader dr = new DataReader(trainData);
        for(ObjectData od : dr.data){
            if(!classes.contains(od.y)) {
                classes.add(od.y);
                classifiers.add(new Perceptron(trainData,alpha,od.y));
            }
        }

        for(int i=0;i<k-1;i++){
            for(int j=0;j<dr.data.size();j++){
                for(Perceptron p: classifiers){
                    p.train(dr.data.get(j));
                }
            }
        }

        for(Perceptron p : classifiers){
            p.showWeight();
        }
        int [][] macierzOmylek = new int[classes.size()][classes.size()];
        dr=new DataReader(testData);
        accuracy=0.0;
        for(ObjectData objectData : dr.data){
            int indexI = classes.indexOf(objectData.y);
            int indexJ = classes.indexOf(getY(objectData));
            macierzOmylek[indexI][indexJ]+=1;
            if(getY(objectData).equals(objectData.y)) {
                accuracy += 1;
            }
        }
        System.out.print("  ");
        for (String s:classes){
            System.out.print(s+" ");
        }
        System.out.println();
        for(int i=0;i<classes.size();i++){
            System.out.print(classes.get(i)+" ");
            for(int j=0;j<classes.size();j++){
                System.out.print(macierzOmylek[i][j]+" ");
            }
            System.out.println();
        }

        accuracy=accuracy/dr.data.size();
        System.out.println("Celnosc: "+accuracy+" dla alpha:"+alpha+" i ilosc iteracji:"+k);
    }

    public String getY(ObjectData objectData){
        double tmp=-10;
        String y="";
        for(Perceptron p : classifiers){
            if(tmp<=p.test(objectData)) {
                tmp = p.test(objectData);
                y=p.y;
            }
        }
        return y;
    }
}

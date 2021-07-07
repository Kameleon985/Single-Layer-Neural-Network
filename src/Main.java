import java.io.File;

public class Main {
    public static void main(String[]args){
        File file = new File("train.csv");
        File filet = new File("test.csv");
        //for(int i=34;i<40;i+=1){
            //for(double j=0.1;j<0.2;j+=0.01){
                NeuralNetworkLayer m = new NeuralNetworkLayer(filet,file,0.1,35);
           //}
        //}


    }
}


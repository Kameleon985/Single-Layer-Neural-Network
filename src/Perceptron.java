import java.io.File;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    double alpha;
    List<ObjectData>training;
    ObjectData weight;
    String y;
    BigDecimal threshold;

    public Perceptron(File dataTrain, double alpha, String y){
        training = new ArrayList<>();
        threshold= new BigDecimal(1,MathContext.DECIMAL128);
        this.alpha=alpha;
        this.y=y;
        DataReader dr = new DataReader(dataTrain);
        for(int i=0;i<dr.data.size();i++){
            training.add(dr.data.get(i));
        }
        weight = dr.data.get(0);
        weight.DataVector.replaceAll(d->d=1.0);
        weight.y=y;
        for(ObjectData od:training){
            train(od);
        }
    }

    public double train(ObjectData objectData){
        MathContext m = MathContext.DECIMAL128;
        if(objectData.DataVector.size()!=weight.DataVector.size())
            throw new IllegalArgumentException();
        double d = test(objectData);
        double expectedResult;
        double calculatedResult;
        if(objectData.y.equals(y))
            expectedResult = 1;
        else
            expectedResult = -1;
        if(d<0)
            calculatedResult=-1;
        else
            calculatedResult=1;
        BigDecimal sum=new BigDecimal(0,m);
        BigDecimal learn = new BigDecimal((expectedResult-calculatedResult)*alpha,m);
        threshold=threshold.subtract(learn,m);
        for(int i=0;i<weight.DataVector.size();i++){
            BigDecimal tmp = new BigDecimal(objectData.DataVector.get(i),m);
            tmp=tmp.multiply(learn,m);
            tmp=tmp.add(new BigDecimal(weight.DataVector.get(i),m));
            weight.DataVector.set(i,tmp.doubleValue());
            tmp = tmp.pow(2,m);
            sum=sum.add(tmp,m);
        }
        if(learn.doubleValue()!=0) {
            sum = sum.sqrt(m);
            for (int i = 0; i < weight.DataVector.size(); i++) {
                BigDecimal norm = new BigDecimal(weight.DataVector.get(i), m);
                norm = norm.divide(sum, m);
                weight.DataVector.set(i, norm.doubleValue());
            }
        }
        return d;
    }

    public double test(ObjectData objectData){
        MathContext m = MathContext.DECIMAL128;
        BigDecimal d = new BigDecimal(0,m);
        for(int i=0;i<weight.DataVector.size();i++){
            BigDecimal tmp = new BigDecimal(weight.DataVector.get(i),m);
            tmp=tmp.multiply(new BigDecimal(objectData.DataVector.get(i),m),m);
            d=d.add(tmp,m);
        }
        return d.subtract(threshold,m).doubleValue();
    }

    public void showWeight(){
        System.out.println(weight.DataVector+"  "+threshold+"  "+y);
    }
}
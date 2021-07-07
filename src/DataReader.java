import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    public List<ObjectData>data;

    public DataReader(File file){
        data=new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            data.add(new ObjectData(line.split(";")));
            while((line= br.readLine())!=null){
                data.add(new ObjectData(line.split(";")));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

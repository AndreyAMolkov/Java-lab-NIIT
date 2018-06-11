import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WisdomDemo {
    private String addressWisdomFile;
    private String marker;
    private ArrayList<String> arrayListWisdom;

    WisdomDemo(String addressWisdomFile){
        this.marker="***";
        this.arrayListWisdom=new ArrayList <>(0);
        setWisdomArray(addressWisdomFile);
    }
public String getRandomWisdom(){
        String wisdom=null;
        ArrayList<String>wisdomList=getArrayListWisdom();
        try {
            int max = wisdomList.size();
            int x = (int) (Math.random() * max);
            wisdom = wisdomList.get(x);
        }catch (NullPointerException e){
            System.out.println(" Error empty list of wisdom;");
            System.out.println(e);
        }
        return wisdom;
}
public ArrayList<String>getArrayListWisdom(){
            return arrayListWisdom;
}
    private void setWisdomArray(String addressWisdomFile) {
        BufferedReader fin;

        try {
            InputStream inputStream=System.class.getResourceAsStream(addressWisdomFile);
            fin=new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));

            String line=" ";
            StringBuilder buf=new StringBuilder();
            for(line = fin.readLine();line!=null;line = fin.readLine())
                if(!line.equals(marker))
                    buf.append(line);
                    else{
                arrayListWisdom.add(buf.toString());
                buf=new StringBuilder();
            }

        } catch (FileNotFoundException exc) {
            System.out.println("Error Input of the file: " + addressWisdomFile + " " + exc);
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}

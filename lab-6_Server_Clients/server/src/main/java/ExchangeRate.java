import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ExchangeRate {
    private String addressXML;
    private String lineExchangeRate;
    ExchangeRate() throws IOException {
        this.addressXML="http://www.ecb.europa.eu//stats//eurofxref//eurofxref-daily.xml";
                       // http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml
        this.lineExchangeRate="";
        download();
    }

    private void download() throws IOException {
    URL url = null;
    URLConnection urlConn = null;
        StringBuilder builder=new StringBuilder();
        try{
        url = new URL(addressXML );
        urlConn = url.openConnection();
    } catch(IOException ioe){
        ioe.printStackTrace();
    }
        try {
        XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(urlConn.getInputStream());

        while (xmlr.hasNext()) {

            xmlr.next();
            if (xmlr.isStartElement()&&xmlr.getAttributeCount()==2&&!xmlr.getAttributeValue(0).isEmpty()) {
                builder.append(xmlr.getAttributeValue(0));
                builder.append(" " + xmlr.getAttributeValue(1) + ",");
            }
        }
    } catch (FileNotFoundException | XMLStreamException ex) {
        ex.printStackTrace();
    }
    lineExchangeRate=builder.toString().substring(0,builder.toString().lastIndexOf(","));

}
public String getLineExchangeRate(){
        return lineExchangeRate;
}
}

import com.sun.javafx.collections.MappingChange;
import javafx.beans.property.MapPropertyBase;

import java.util.*;

public class ExchangeCalculate {
    private String markerPair;
    private String markerSeparate;
    private String line;
    private SortedMap<String,Double> exchangeMap;
    private ArrayList<String>nameOfCurrency;

    ExchangeCalculate(String line){
    this.markerPair=" ";
    this.markerSeparate=",";
    this.line=line;
    this.nameOfCurrency=new ArrayList <>(0);
    this.exchangeMap=new TreeMap<>();     setExchangeMap();
    }
    private void setExchangeMap(){
        String buf[]=line.split(markerSeparate);

        String pair[];
        for (String s:buf) {
            pair=s.split(markerPair);
            nameOfCurrency.add(pair[0]);
            exchangeMap.put(pair[0],Double.parseDouble(pair[1]));

        }

    }
    public ArrayList<String>getNameOfCurrency(){
        return nameOfCurrency;
    }

    private Map <String, Double> getExchangeMap() {
        return exchangeMap;
    }

    public String getCalculateExchangePair(String nameCurrencyTo,double amountCurrencyTo, String nameCurrencyFrom)
    {
        Double result= -1.0;
        if(!(nameCurrencyFrom.isEmpty()||nameCurrencyTo.isEmpty())) {
            result = exchangeMap.get(nameCurrencyTo) / exchangeMap.get(nameCurrencyFrom) * amountCurrencyTo;
        }
       Integer start=result.intValue();
        Double acc=100*(result-start);
        Integer afterDot=acc.intValue();

                return start.toString()+"."+afterDot.toString();


    }
}

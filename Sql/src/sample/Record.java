package sample;

public class Record {
    private Integer id;
    private  Float temperature;

    public Record(Integer id,Float temperature){
    this.id=id;
    this.temperature=temperature;
}

    public Integer getId(){
        return id;
    }
    public  Float getTemperature(){
        return  temperature;
    }
}

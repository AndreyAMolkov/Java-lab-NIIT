public class DateTimeSeparate {

    private String hour;
    private String min;
    private String sec;
    private String date;
    DateTimeSeparate(String dateTime){
        String []buf=dateTime.split("T");
        this.date=buf[0];
        String time=buf[1];
       // String[]dateSplit=date.split("-");
        String[]timeSplit=time.split(":");
        this.hour=(timeSplit[0]);
        this.min=(timeSplit[1]);
        Double d=Double.parseDouble(timeSplit[2]);
        Integer i=d.intValue();
       if(i==0)
           this.sec="00";
           else if(i<10)
               this.sec="0"+i.toString();
               else

         this.sec=i.toString();

    }


    public String getHour() {
        return hour;
    }

    public String getMin() {
        return min;
    }

    public String getSec() {
        return sec;
    }
public String getDate(){
        return date;
}
}

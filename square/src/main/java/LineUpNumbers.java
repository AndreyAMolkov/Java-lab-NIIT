public class LineUpNumbers {
    public static void main(String[] args){
//String line="1,2,3,4-7,20-24,100";
        String separate =",";
        String dash="-";
       String []buf=args[0].split(separate);
       // String []buf=line.split(separate);
        DeployString Deploy=new DeployString(buf,separate,dash);
        String result=Deploy.CreateLine();
        System.out.print(result);
    }
}
class DeployString{
    String[] arr;
    String dash;
    String separate ;
    DeployString(String[]arr,String separate,String dash){
        this.arr=arr;
        this.separate=separate;
        this.dash=dash;
    }
    String CreateLine(){
        String result="";
        int length=arr.length;
        for(int i=0;i<length;i++){
            if(arr[i].indexOf(dash)<0)
                result+=arr[i]+separate;
            else
                result+=DashToNumbers(arr[i])+separate;// find symbol dash (for example 3-8)

        }
        int lastSymbol=result.lastIndexOf(separate);

    return result.toString().substring(0,lastSymbol)+".";

    }


    String DashToNumbers(String line){
        String []buf=line.split(dash);
        int min=Integer.parseInt(buf[0]);
        int max=Integer.parseInt(buf[1]);
        int count=max-min;
        String result="";
      for (int i=0;i<count;i++ ){
          result+=(min+i)+separate;
      }
        result+=buf[1];// add the last number
        return result;
    }


}
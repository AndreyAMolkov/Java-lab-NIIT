public class NumberToDiapason {

    public static void main(String[] args) {
//String line="1,2,4,5,7,45,46,67,68,69,89,1001,1002,1000003,10000004";
        String separate = ",";
        String[] buf = args[0].split(separate);

        Minimize Minimize = new Minimize(buf, separate);
        String result = Minimize.CreateLine();
        System.out.print(result);
    }
}
class Minimize{

    String[] arr;
    String dash="-";
    String separate ;
    int length;
    Minimize(String[]arr,String separate){
        this.arr=arr;
        this.separate=separate;
    }
    String CreateLine(){
        String result=arr[0].toString();
        length=arr.length;
        int first=Integer.parseInt(arr[0]);
        int second;
       // String insert="";
       int lastSymbol=0;
        for(int i=1;i<length;i++){
           second=Integer.parseInt(arr[i]);

            if(second-first==1) {
                i = NumbersToDash(i, result);

                if(lastSymbol>0)
                    result=result.toString().substring(0,lastSymbol);// delete the last symbol separate

                result+=dash+arr[i].toString();
            }else
                result+=arr[i];

            result+=separate;
            first=second;
            lastSymbol=result.lastIndexOf(separate);
        }

        return result.toString().substring(0,lastSymbol)+".";

    }
    private int NumbersToDash(int first,String result){
        int last=first,max;

        int previous,next;
      do {
          previous=Integer.parseInt(arr[last-1]);
          next=Integer.parseInt(arr[last]);
        }while((next-previous==1)&&(length>++last));
        max=last-1;
        return max;
    }
}
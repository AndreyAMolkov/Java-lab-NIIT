public class Main {
    public static void main(String[] args)
    {
        double val=Double.parseDouble(args[0]);
        String precision=args[1];

          //  double val=2.0;
          //  String precision="0.001";
        Sqrt sqrt=new Sqrt(val,Double.parseDouble(precision));
        double result=sqrt.calc();
        int count_precision=precision.length()-2;// define of count precisins
        Precisions.Print(val,result,count_precision);
    }
}

class Precisions{
   int precision;


   public static void Print(double val,double result,int precision)
    {
       // System.out.println("Sqrt of "+val+"="+result);// the old version
        String text= "Sqrt of "+val+"=%."+precision+"f";
        System.out.printf(text,+result);
    }

}
class Sqrt
{
    double delta;
    double arg;

    Sqrt(double arg,double delta) {
        this.arg=arg;
        this.delta=delta;
    }
    double average(double x,double y) {
        return (x+y)/2.0;
    }
    boolean good(double guess,double x) {
        return Math.abs(guess*guess-x)<delta;
    }
    double improve(double guess,double x) {
        return average(guess,x/guess);
    }
    double iter(double guess, double x) {
        if(good(guess,x))
            return guess;
        else
            return iter(improve(guess,x),x);
    }
    public double calc() {
        return iter(1.0,arg);
    }
}


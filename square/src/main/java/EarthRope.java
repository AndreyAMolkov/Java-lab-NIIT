public class EarthRope {
    public static void main(String[] args) {
        double rope = 0.001;// 1 meter
        //     double ference=Double.parseDouble(args[0]);
        String line = "6378.1";// kilometres
        double radius = Double.parseDouble(line);
        InsertRope Rope = new InsertRope(rope);
        double gap=Rope.CalculationGap(radius);
        System.out.format("%.9f",+gap);

    }
}

    class InsertRope{
   private Circle Circle;
   private double rope;

    InsertRope(double rope){
        this.rope=rope;
    }

    double CalculationGap(double radiusEarth){
        Circle Earth=new Circle();
        Earth. setRadius(radiusEarth);
        double oldFerence=Earth.getFerence();
        double newFerence=oldFerence+rope;
        Circle newSize= new Circle();
        newSize.setFerence(newFerence);
        double newRadius=newSize.getRadius();
        double gap=newRadius-radiusEarth;
        return gap;
    }
}

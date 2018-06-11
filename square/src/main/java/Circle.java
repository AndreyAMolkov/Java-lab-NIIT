public class Circle {
    private double radius,area,ference;

  void setFerence(double ference){
      this.ference=ference;
      radius= (ference/(2*Math.PI));
      area=Math.PI*radius*radius;
    }
  void setRadius(double radius){
      this.radius=radius;
        ference= 2*Math.PI*radius;
      area=Math.PI*radius*radius;
    }
   void setArea(double area){
      this.area=area;
      radius=Math.sqrt(area);
       ference= 2*Math.PI*radius;
    }

    double getRadius(){
      return radius;
    }
    double getArea(){
      return area;
    }
    double getFerence(){
      return ference;
    }
}

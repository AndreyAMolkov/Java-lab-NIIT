public class Pool {
    public static void main(String[] args) {
        int costConcrete = 1000;//rub
        int costBarrier = 2000;// rub
        int radiusPool = 3;//meters
        int weightPath = 1;

        /*Необходимо рассчитать стоимость материалов для бетонной дорожки вокруг круглого бассейна,
        а также стоимость материалов ограды вокруг бассейна (вместе с дорожкой).
        Стоимость 1 квадратного метра бетонного покрытия 1000 р. Стоимость 1 погонного метра ограды 2000 р.
        Радиус бассейна 3 м. Ширина бетонной дорожки вокруг бассейна 1 м.
        */
        //way fr walk

        Calculation Calculation = new Calculation();
        int barrier = Calculation.barrier((radiusPool + weightPath), costBarrier);
        int path = Calculation.path(radiusPool, weightPath, costConcrete);
        System.out.println("The cost the track around the pool = " + path + " Rub.");
        System.out.println("The cost the barrier around the pool = " + barrier + " Rub.");
    }
}
   class Calculation {
       // Circle Circle;

        int barrier(double radiusBarrier, int costBarrier) {
            Circle barrier = new Circle();
            barrier.setRadius(radiusBarrier);
            int product=(int) (barrier.getFerence() * costBarrier);
            return product;
        }
        int path(double radiusPool,double weightPath,int costConcrete){
            Circle Pool = new Circle();
            Pool.setRadius(radiusPool);
            double areaPool = Pool.getArea();
            Circle Path = new Circle();
            Path.setRadius(radiusPool+weightPath);
            double areaOut=Path.getArea();
            double areaPath = areaOut - areaPool;
            double path = areaPath * costConcrete;
                    return (int)path;
        }
    }

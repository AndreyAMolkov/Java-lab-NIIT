public class CollatzDemo {
    public static void main(String[]args){
        Collatz Collatz=new Collatz();
        long sequenceMax=Collatz.maxSequence(1_000_000);
        long number=Collatz.numberMax;

        System.out.println("The number: "+number+" have the max sequence: "+sequenceMax);
    }
}
class Collatz{

    Long sequenceMax=0L;
    Long numberMax;

Collatz(){
    sequenceMax=0L;
    numberMax=0L;
}
      long calculation(long number) {
        long count=1;
        for ( ;number!=1;count++){
            if (number % 2 == 0)
             number = number / 2;
            else
             number = number * 3 + 1;
         }
    return count;
    }
    long maxSequence(int limit){
        long sequence=0L;
        numberMax=0L;
        for(long i=1;i<=limit;i++) {
            sequence = calculation(i);
            if (sequenceMax < sequence){
                sequenceMax=sequence;
                numberMax=i;
            }
        }
            return sequenceMax;
    }

}
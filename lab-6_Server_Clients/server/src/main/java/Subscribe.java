public class Subscribe {
    int timerSec;
    Subscribe(){
        this.timerSec=60;
    }
    public Boolean startTimer(){
        Runnable timer=()->{
            try {
                Thread.sleep(timerSec*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread=new Thread(timer);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

}

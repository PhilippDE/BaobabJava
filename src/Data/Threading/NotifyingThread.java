package Data.Threading;

/**
 * Created by Marcel on 07.04.2017.
 */
public class NotifyingThread extends Thread {

    private ThreadFinishedListener tfl;

    public final void setListener(ThreadFinishedListener threadFinishedListener){
        this.tfl=threadFinishedListener;
    }


    private final void notifyListener(){
        if(isInterrupted()){
            return;
        }
        tfl.notifyThreadFinished(this);
    }

    public void task(){

    }

    @Override
    public final void run(){
            try {
                task();
                if(Thread.interrupted()) {
                    return;
                }
            } finally {
                notifyListener();
            }
    }

}

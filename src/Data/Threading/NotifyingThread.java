package Data.Threading;

/**
 * The Thread class that implements the logic for notification of a ThreadFinishedListener.
 * Subclasses only need to implement the task method
 *
 * Created by Marcel on 07.04.2017.
 */
public class NotifyingThread extends Thread {

    private ThreadFinishedListener tfl;

    /**
     * Sets this threads listener
     * @param threadFinishedListener the listener that will be notified when the thread finished
     *
     * @see ThreadFinishedListener
     */
    public final void setListener(ThreadFinishedListener threadFinishedListener){
        this.tfl=threadFinishedListener;
    }

    /**
     * Notifies this threads listener
     */
    private final void notifyListener(){
        if(isInterrupted()){
            return;
        }
        if(tfl!=null) {
            tfl.notifyThreadFinished(this);
        }else{
            System.out.println("I'm null ... how did that happen?");
        }
    }

    /**
     * This method can be used to perform a specific task by overriding it
     */
    public void task(){}

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

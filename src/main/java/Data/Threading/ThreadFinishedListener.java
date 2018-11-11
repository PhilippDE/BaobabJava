package main.java.Data.Threading;

/**
 * Interface that is needed for the NotifyingThread to notify
 * Created by Marcel on 07.04.2017.
 */
public interface ThreadFinishedListener {

     /**
      * This method will be called by a NotifyingThread or an extending class when the thread has finished.
      * @param thread the thread that has finished
      *
      * @see NotifyingThread
      *
      */
     void notifyThreadFinished(NotifyingThread thread);
}

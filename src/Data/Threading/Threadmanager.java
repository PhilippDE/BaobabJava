package Data.Threading;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static Data.Settings.threadCountLimit;

/**
 * Created by Marcel on 07.04.2017.
 */
public final class Threadmanager implements ThreadFinishedListener{

    private static ArrayList<NotifyingThread> threads=new ArrayList<>();

    private static Threadmanager listener=new Threadmanager();

    public static void addThread(NotifyingThread thread){
        threads.add(thread);
        int count=0;
        for(NotifyingThread t:threads ){
            if(t.isAlive()){
                count++;
            }
        }
        if(count<threadCountLimit)
            thread.start();
    }

    public static Threadmanager getListener(){
        return listener;
    }


    private Threadmanager(){}

    @Override
    public void notifyThreadFinished(NotifyingThread thread) {
        threads.remove(thread);
        System.out.println(threads.size());
        int count=0;
        try {
            for (NotifyingThread t : threads) {
                if (t.isAlive()) {
                    count++;
                } else {
                    if (count < threadCountLimit) {
                        t.start();
                        count++;
                    }
                }
            }
        }catch(ConcurrentModificationException ignored){

        }
    }
}

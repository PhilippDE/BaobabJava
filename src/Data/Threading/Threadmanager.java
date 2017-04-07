package Data.Threading;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static Data.Settings.threadCountLimit;

/**
 * Created by Marcel on 07.04.2017.
 */
public final class Threadmanager implements ThreadFinishedListener{

    private static ArrayList<NotifyingThread> threads=new ArrayList<>();

    private static boolean end=false;

    private static Threadmanager listener=new Threadmanager();

    public static void addThread(NotifyingThread thread){
        if(!end) {
            threads.add(thread);
            int count = 0;
            boolean flag=true;
            while(flag) {
                try{
                    flag=false;
                    for (NotifyingThread t : threads) {
                        if (t.isAlive()) {
                            count++;
                        }
                    }
                }
                catch (ConcurrentModificationException ignored) {
                    System.out.println("CCM in Threadmanager adding");
                    flag=true;
                }
            }
            if (count < threadCountLimit)
                thread.start();
        }
    }

    public static Threadmanager getListener(){
        return listener;
    }

    @Override
    public void notifyThreadFinished(NotifyingThread thread) {
        threads.remove(thread);
        int count=0;
        if(!end) {
            boolean flag=true;
            while(flag) {
                try{
                    flag=false;
                for (NotifyingThread t : threads) {
                    if (t.isAlive()) {
                        count++;
                    } else {
                        if (count < threadCountLimit) {
                            try {
                                t.start();
                            } catch (IllegalThreadStateException ignored) {

                            }
                               count++;
                            }
                        }
                    }
                } catch (ConcurrentModificationException ignored) {
                    System.out.println("CCM in Threadmanager notification");
                    flag=true;
                }
            }
        }
    }

    public static void stopThreads(){
        end=true;
                boolean flag=true;
                while(flag&& threads.size()!=0) {
                    try {
                        flag=false;
                        for (NotifyingThread t : threads) {
                            while(t.isAlive()) {
                                t.interrupt();

                                if (t.isInterrupted() && !t.isAlive()) {
                                    threads.remove(t);
                                }
                            }
                            if(!t.isAlive()){
                                threads.remove(t);
                            }
                        }
                    }
                    catch (ConcurrentModificationException ignored) {
                        flag=true;
                    }
                }
                end=false;
    }

    private Threadmanager(){}


}

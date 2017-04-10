package Data.Threading;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static Data.Settings.threadCountLimitProcessing;
import static Data.Settings.threadCountLimitTree;

/**
 * This class manages the threads and controls that the amount of threads do not exceed the threadlimit set in the
 * settings class.
 * This class only is responsible for threads that have been added using the addThread method
 * Created by Marcel on 07.04.2017.
 */
public final class Threadmanager implements ThreadFinishedListener{

    private static ArrayList<NotifyingThread> threadsProcessing=new ArrayList<>();
    private static ArrayList<NotifyingThread> threadsTree=new ArrayList<>();

    private static boolean end=false;

    private static Threadmanager listener=new Threadmanager();

    /**
     * Adds a thread to the list of threads that will be done.
     *
     * Note that the thread may not be started instantly as the maximum threadlimit may be reached.
     * In this case the thread will be called depending on which position the thread is in the list. Threads that have
     * been added earlier will be started earlier.
     *
     * Note that the thread also needs to be NotifyingThread or extend that class in order for the Threadmanager to
     * work, since every thread that will be added will get the default ThreadFinishedListener of this class set at
     * its own ThreadFinishedListener
     *
     * @param thread  the thread that will be handled by this class
     *
     * @see NotifyingThread
     * @see ThreadFinishedListener
     */
    public static void addThreadProcessing(NotifyingThread thread){
        if(!end) {
            threadsProcessing.add(thread);
            int count = 0;
            boolean flag=true;
            while(flag) {
                try{
                    flag=false;
                    for (NotifyingThread t : threadsProcessing) {
                        if (t.isAlive()) {
                            count++;
                        }
                    }
                }
                catch (ConcurrentModificationException ignored) {
                    System.err.println("ConcurrentModificationException in class Threadmanager " +
                            " \n      in addThreadProcessing()");
                    flag=true;
                }
            }
            if (count < threadCountLimitProcessing)
                thread.start();
        }
    }

    /**
     * Adds a thread to the list of threads that will be done.
     *
     * Note that the thread may not be started instantly as the maximum threadlimit may be reached.
     * In this case the thread will be called depending on which position the thread is in the list. Threads that have
     * been added earlier will be started earlier.
     *
     * Note that the thread also needs to be NotifyingThread or extend that class in order for the Threadmanager to
     * work, since every thread that will be added will get the default ThreadFinishedListener of this class set at
     * its own ThreadFinishedListener
     *
     * @param thread  the thread that will be handled by this class
     *
     * @see NotifyingThread
     * @see ThreadFinishedListener
     */
    public static void addThreadTree(NotifyingThread thread){
        if(!end) {
            threadsTree.add(thread);
            int count = 0;
            boolean flag=true;
            while(flag) {
                try{
                    flag=false;
                    for (NotifyingThread t : threadsTree) {
                        if (t.isAlive()) {
                            count++;
                        }
                    }
                }
                catch (ConcurrentModificationException ignored) {
                    System.err.println("ConcurrentModificationException in class Threadmanager " +
                            " \n      in addThreadTree()");

                    flag=true;
                }
            }
            if (count < threadCountLimitTree)
                thread.start();
        }
    }

    /**
     * Returns the default listener of the Threadmanager class
     * @return the default ThreadFinishedListener
     *
     * @see ThreadFinishedListener
     */
    public static Threadmanager getListener(){
        return listener;
    }


    @Override
    public void notifyThreadFinished(NotifyingThread thread) {
        if (threadsProcessing.contains(thread)) {
            threadsProcessing.remove(thread);
            int count = 0;
            if (!end) {
                boolean flag = true;
                while (flag) {
                    try {
                        flag = false;
                        for (NotifyingThread t : threadsProcessing) {
                            if (t.isAlive()) {
                                count++;
                            } else {
                                if (count < threadCountLimitProcessing) {
                                    if (t.getState() == Thread.State.TERMINATED) {
                                        threadsProcessing.remove(t);
                                    } else {
                                        try {
                                            if (t.getState() == Thread.State.NEW) {
                                                t.start();
                                            }
                                        } catch (IllegalThreadStateException ignored) {
                                        }
                                    }
                                    count++;
                                }
                            }
                        }
                    } catch (ConcurrentModificationException ignored) {
                        System.err.println("ConcurrentModificationException in class Threadmanager \n" +
                                "      in  notifyThreadFinished() in processing Processingthread list");
                        flag = true;
                    }
                }
            }
        } else if (threadsTree.contains(thread)) {
            threadsTree.remove(thread);
            int count = 0;
            if (!end) {
                boolean flag = true;
                while (flag) {
                    try {
                        flag = false;
                        for (NotifyingThread t : threadsTree) {
                            if (t.isAlive()) {
                                count++;
                            } else {
                                if (count < threadCountLimitProcessing) {
                                    if (t.getState() == Thread.State.TERMINATED) {
                                        threadsProcessing.remove(t);
                                    } else {
                                        try {
                                            if (t.getState() == Thread.State.NEW) {
                                                t.start();
                                            }
                                        } catch (IllegalThreadStateException ignored) {
                                        }
                                    }
                                    count++;
                                }
                            }
                        }
                    } catch (ConcurrentModificationException ignored) {
                        System.err.println("ConcurrentModificationException in class Threadmanager \n" +
                                "      in  notifyThreadFinished()  in processing of Treethread list");
                        flag = true;
                    }
                }
            }
        }
    }

    /**
     * Stops all threads that have been commited to the Threadmanager. This will be done through calling
     * Thread.interrupt().
     * This may take a few seconds and will NOT be done in a separate thread.
     */
    public static void stopThreads(){
        end=true;
        boolean flag=true;
        while(flag&& threadsProcessing.size()!=0) {
            try {
                flag=false;
                for (NotifyingThread t : threadsProcessing) {
                    while(t.isAlive()) {
                        t.interrupt();

                        if (t.isInterrupted() && !t.isAlive()) {
                            threadsProcessing.remove(t);
                        }
                    }
                    if(!t.isAlive()){
                        threadsProcessing.remove(t);
                    }
                }
            }
            catch (ConcurrentModificationException ignored) {
                flag=true;
            }
        }
        flag=true;
        while(flag&& threadsTree.size()!=0) {
            try {
                flag=false;
                for (NotifyingThread t : threadsTree) {
                    while(t.isAlive()) {
                        t.interrupt();

                        if (t.isInterrupted() && !t.isAlive()) {
                            threadsTree.remove(t);
                        }
                    }
                    if(!t.isAlive()){
                        threadsTree.remove(t);
                    }
                }
            }
            catch (ConcurrentModificationException ignored) {
                flag=true;
            }
        }
        end=false;
    }

    /**
     * Private constructor so this class cant be instantiated from outside
     */
    private Threadmanager(){}


}

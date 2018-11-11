package main.java.Data;

/**
 * Class containing all Settings
 * Created by Marcel on 07.04.2017.
 */
public final class Settings {

    public static boolean multiThreadingProcessing=true;

    public static int threadCountLimitProcessing;

    public static boolean multiThreadingTree =true;

    public static int threadCountLimitTree;

    static{
        threadCountLimitProcessing =Runtime.getRuntime().availableProcessors();
        threadCountLimitTree=Runtime.getRuntime().availableProcessors();
    }

    private Settings(){}
}

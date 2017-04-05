package Data;

/**
 * Created by Marcel on 05.04.2017.
 */
public class OSDependingData {


    private static String fileViewer;
    private static boolean isWindows;
    private static boolean isMac;
    private static boolean isLinux;

    static {
        isWindows= System.getProperty("os.name").contains("Windows");
        isMac= System.getProperty("os.name").contains("mac");
        isLinux= System.getProperty("os.name").contains("nix")|| System.getProperty("os.name").contains("nux")
        || System.getProperty("os.name").contains("aix");

        if(isLinux)
            fileViewer="Filemanager";
        else if(isMac)
            fileViewer="Finder";
        else if(isWindows)
            fileViewer="Explorer";
        else
            fileViewer="Fileviewer";
    }


    public static String getFileViewer(){
        return fileViewer;
    }
}

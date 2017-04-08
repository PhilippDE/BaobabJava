package Data;

/**
 * This class holds all data that depend on the operating system such as names
 * Created by Marcel on 05.04.2017.
 */
public class OSDependingData {


    private static String fileViewer;

    static {
        boolean isWindows = System.getProperty("os.name").contains("Windows");
        boolean isMac = System.getProperty("os.name").contains("mac");
        boolean isLinux = System.getProperty("os.name").contains("nix") || System.getProperty("os.name").contains("nux")
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

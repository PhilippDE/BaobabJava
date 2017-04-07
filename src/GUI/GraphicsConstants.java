package GUI;

import java.awt.*;

/**
 * Created by Marcel on 06.04.2017.
 */
public class GraphicsConstants {

    public static Font standardFont=new Font("Arial",Font.PLAIN,15);

    public static Font standardFontLarger=new Font("Arial",Font.PLAIN,16);

    public static Font  labelFont=new Font("",Font.PLAIN,16);

    public static int nameLabelX=200;
    public static int nameLabelY=40;

    public static final int Width4K=4096;
    public static final int Height4K=2160;

    private static final int nLabelXF4k=400;
    private static final int nLabelYF4k=80;

    private static final int fontSizeF4K=11;
    private static final int fontSizeLabelF4K=11;



    public static final int WidthFULLHD=1920;
    public static final int HeightFULLHD=1080;

    private static final int nLabelXFHD=150;
    private static final int nLabelYFHD=40;

    private static final int fontSizeFULLHD=11;
    private static final int fontSizeLabelFULLHD=11;

    public static int sunviewPrefferedFULLHDX=1200;
    public static int sunviewPrefferedFULLHDY=1080;

    public static int treeviewPrefferedFULLHDX=400;
    public static int treeviewPrefferedFULLHDY=1080;
    public static int treeRowHeight=22;

    public static int nodeViewerFULLHDX=350;
    public static int nodeViewerFULLHDY=460;

    public static int settingsPanelFULLHDX=400;
    public static int settingsPanelFULLHDY=1080;



    public static void update(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        double scaleFactor;
        if(width/(double)WidthFULLHD>height/(double)HeightFULLHD){
            scaleFactor=height/HeightFULLHD;
        }else{
            scaleFactor=(width/(double)WidthFULLHD);
        }

        System.out.println(scaleFactor);
        nameLabelX=(int)(scaleFactor*nLabelXFHD);
        nameLabelY=(int)(scaleFactor*nLabelYFHD);

        //nodeViewerFULLHDX=(int)(350*scaleFactor);
        //nodeViewerFULLHDY=(int)(460*scaleFactor);
        treeRowHeight=(int)(22*scaleFactor);

        settingsPanelFULLHDX=(int)(300*scaleFactor);
        settingsPanelFULLHDY=(int)(200*scaleFactor);

        standardFont=new Font("Arial",Font.PLAIN,1+(int)Math.ceil(fontSizeFULLHD*scaleFactor));
        standardFontLarger=new Font("Arial",Font.PLAIN,2+(int)Math.ceil(fontSizeFULLHD*scaleFactor));
        labelFont=new Font("Arial",Font.PLAIN,1+(int)Math.ceil(fontSizeLabelFULLHD*scaleFactor));
    }

    static {
        update();
    }

}
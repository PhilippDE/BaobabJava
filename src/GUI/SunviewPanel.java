package GUI;

import Data.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Marcel on 06.03.2017.
 */
public class SunviewPanel {
    private JPanel rootPanel;

    private void createUIComponents() {
        rootPanel=new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(buffer,0,0,null);
            }
        };
    }

    private final static double degreeOffset=3;

    BufferedImage buffer=new BufferedImage(800,800,BufferedImage.TYPE_INT_ARGB);


    private final static double degreeSpacer=3;


    public void drawNode(Node node){
        System.out.print("Started drawing!");
        buffer=new BufferedImage(800,800,BufferedImage.TYPE_INT_ARGB);
        rootPanel.repaint();
        double offset=0;
        double radius=0;
        for(Node n:node.getSubNodes()){
            radius=360*((double)n.getSize())/((double)node.getSize())-degreeOffset;
            if(radius>degreeSpacer) {
                drawArc(radius, offset, 0);
                drawNode(1, n, offset, ((double) n.getSize()) / ((double) node.getSize()));
                offset += radius + degreeOffset;
            }
        }
    }

    private void drawNode(int layer,Node node,double offset_,double percentage){
        if(layer<5) {
            double offset = offset_;
            double radius = 0;
            for (Node n : node.getSubNodes()) {
                radius = 360 * percentage*((double) n.getSize()) / ((double) node.getSize()) - degreeOffset;
                if(radius>degreeSpacer) {
                    drawArc(radius, offset, layer);
                    drawNode(layer + 1, n, offset, (((double) n.getSize()) / ((double) node.getSize())) * percentage);
                    offset += radius + degreeOffset;
                }
            }
        }
    }

    public void drawArc(double degree,double degreeOffset,int layer){
        Graphics2D g2=(Graphics2D)buffer.getGraphics();
        Graphics2D g2Panel=(Graphics2D)rootPanel.getGraphics();
        g2.setColor(Color.red);
        double minDistance=getLayerStart(layer);
        double maxDistance=getLayerEnd(layer);
        for(int i=(int)((rootPanel.getWidth()/2)-maxDistance);i<=rootPanel.getWidth()/2+maxDistance;i++){
            for(int j=(int)((rootPanel.getHeight()/2)-maxDistance);j<rootPanel.getHeight()/2+maxDistance;j++){
                double distance=Math.sqrt(Math.pow(i-rootPanel.getWidth()/2,2)+Math.pow(j-rootPanel.getHeight()/2,2));
                double angle=Math.asin(Math.abs((double)j-rootPanel.getHeight()/2)/distance);
                //1 & 2 quadrant
                if(j>rootPanel.getHeight()/2) {
                    //2 quadrant
                    if (i < rootPanel.getWidth() / 2) {
                        angle=Math.toRadians(90)-angle;
                        angle += Math.toRadians(90);
                    }
                }
                // 3 & 4 quadrant
                else {
                    //3 quadrant
                    if (i > rootPanel.getWidth() / 2) {
                        angle=Math.toRadians(90)-angle;
                        angle += Math.toRadians(90);
                    }
                    angle+=Math.toRadians(180);
                }
                if(angle<=Math.toRadians(degree)+Math.toRadians(degreeOffset)&&angle>=Math.toRadians(degreeOffset)){
                    if(distance>minDistance&&distance<maxDistance) {
                        g2.drawOval(i, j, 1, 1);
                        g2Panel.drawOval(i, j, 1, 1);
                    }
                }
            }
        }
    }

    private final static double layerThickness=50;
    private final static double layerOffset=30;

    private static double getLayerStart(int layer){
        if(layer==0){
            return layerOffset;
        }else{
            return layer*layerThickness+layerOffset+5;
        }
    }

    private static double getLayerEnd(int layer){
        if(layer==0){
            return layerOffset+layerThickness;
        }else{
            return layer*layerThickness+layerThickness+layerOffset;
        }
    }


}


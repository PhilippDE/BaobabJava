package GUI;

import Data.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;

/**
 * Created by Marcel on 06.03.2017.
 */
public class SunviewPanel implements DataVisualizer{
    private JPanel rootPanel;
    private JPanel drawPanel;
    private JPanel infoPanel;
    private JButton update;
    private JLabel nameLabel;
    private JLabel sizeLabel;
    private JFormattedTextField layerCountField;

    private void createUIComponents() {
        rootPanel=new JPanel();
        drawPanel=new JPanel(){
        @Override
        public void paintComponent(Graphics g) {
                super.paintComponent(g);
            if (!startedRendering) {
                int size;
                if (drawPanel.getWidth() == drawPanel.getHeight()) {
                    size = drawPanel.getWidth();
                } else if (drawPanel.getWidth() > drawPanel.getHeight()) {
                    size = drawPanel.getHeight();
                } else {
                    size = drawPanel.getWidth();
                }
                g.drawImage(scale(buffer, size, (double) size / (double) buffer.getHeight()), 0, 0, null);
            }
        }
        };

        infoPanel=new JPanel();
        update=new JButton();
        update.addActionListener(e->{
             layerCount=(Integer)layerCountField.getValue();
             updateLayers();
             drawPanel.repaint();
             setColorsBasedOnAngle(this.superNode);
             drawNode(superNode);
             setNodeInformation(superNode.getName(),Node.sizeFormated(superNode.getSize()));
        });
        nameLabel=new JLabel();
        sizeLabel=new JLabel();
        layerCountField=new JFormattedTextField();
        layerCountField.setValue(7);
        layerCountField.setText("Layers: ");

    }

    public void setNodeInformation(String name,String size){
        this.nameLabel.setText("Folder: "+ name);
        this.sizeLabel.setText("Size: "+size);
    }

    BufferedImage buffer=new BufferedImage(400,400,BufferedImage.TYPE_INT_ARGB);

    private final static double degreeOffset=3.0;
    private final static double degreeSpacer=3.0;

    private static double ringFactor=1.35;
    private static int layerCount=7;
    private boolean startedRendering=false;

    private static double layerThickness=50*ringFactor*(5.0/(double)layerCount);
    private static double layerOffset=30*ringFactor*(5.0/(double)layerCount);

    private Node superNode;

    private void updateLayers(){
        int size;
        if(drawPanel.getWidth()==drawPanel.getHeight()){
            size=drawPanel.getWidth();
        }else if(drawPanel.getWidth()>drawPanel.getHeight()){
            size=drawPanel.getHeight();
        }else if(drawPanel.getWidth()<drawPanel.getHeight()){
            size=drawPanel.getWidth();
        }else{
            size=100;
        }
        ringFactor=(double)size/(double)600;
        layerThickness=50*ringFactor*(5.0/(double)layerCount);
        layerOffset=30*ringFactor*(5.0/(double)layerCount);
    }

    public static void setColorsBasedOnAngle(Node supernode) {
        int count=0;

        for(int i=0;i<supernode.getSubNodes().length;i++){
            if(360*(supernode.getSubNodes()[i].getUsagePercentOfParent())-degreeOffset>degreeSpacer) {
                count++;
            }
        }
        double[] angles=new double[count];
        double angleCount=0;
        for(int i=0;i< angles.length;i++){
            double percentage=supernode.getSubNodes()[i].getUsagePercentOfParent();
            angleCount+=percentage;
            angles[i]=angleCount;
            if(i!=0) {
                supernode.getSubNodes()[i].setOwnColor(Color.getHSBColor((float)angles[i-1],1,1));
                setColorsBasedOnAngle(supernode.getSubNodes()[i],angles[i]-angles[i-1],angles[i-1],1);
            }else{
                supernode.getSubNodes()[i].setOwnColor(Color.getHSBColor(0,1,1));
                setColorsBasedOnAngle(supernode.getSubNodes()[i],angles[0],0,1);
            }
        }
    }

    public static void setColorsBasedOnAngle(Node supernode,double maximum,double start,int layer){
        if(layer<6) {
            int count=0;
            for(int i=0;i<supernode.getSubNodes().length;i++){

                if(360*((double)supernode.getSubNodes()[i].getSize())/((double)supernode.getSize())-degreeOffset>degreeSpacer) {
                    count++;
                }
            }
            double percentagePerNode = (maximum) / (double) (count);

            for (int i = 0; i < count; i++) {
                if (i != 0) {
                    supernode.getSubNodes()[i].setOwnColor(
                            Color.getHSBColor((float) ((float) (i) * percentagePerNode) + (float) start, 1, 1));
                    setColorsBasedOnAngle(
                            supernode.getSubNodes()[i],percentagePerNode,
                            start+(double)i*percentagePerNode,layer+1);
                } else {
                    supernode.getSubNodes()[i].setOwnColor(Color.getHSBColor((float) start, 1, 1));
                    setColorsBasedOnAngle(supernode.getSubNodes()[0],percentagePerNode, start,layer+1);
                }
            }
        }
    }

    public void drawNode(Node node) {
        new Thread(() -> {
            startedRendering=true;
            SunviewPanel.this.superNode=node;
            updateLayers();
            System.out.print("Started drawing!");
            int size;
            if(drawPanel.getWidth()==drawPanel.getHeight()) {
                size = drawPanel.getWidth();
            }else if(drawPanel.getWidth()>drawPanel.getHeight()) {
                size = drawPanel.getHeight();
            }else {
                size = drawPanel.getWidth();
            }

            buffer=new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            drawPanel.repaint();
            double offset = 0;
            double radius = 0;
            for(Node n:node.getSubNodes()) {
                radius = 360 * ((double) n.getSize()) / ((double) node.getSize()) - degreeOffset;
                if (radius > degreeSpacer) {
                    System.out.println((double) n.getSize() / ((double) node.getSize()));
                    drawArc(radius, offset, 0, n.getOwnColor());
                    drawNode(1, n, offset, ((double) n.getSize()) / ((double) node.getSize()));
                    offset += radius + degreeOffset;
                }
            }
            startedRendering=false;
            SunviewPanel.this.rootPanel.repaint();
    }).start();
    }

    private void drawNode(int layer,Node node,double offset_,double percentage){
        if(layer<layerCount) {
            double offset = offset_;
            double radius = 0;
            for (Node n : node.getSubNodes()) {
                radius = 360 * percentage*((double) n.getSize()) / ((double) node.getSize()) - degreeOffset;
                if(radius>degreeSpacer) {
                    drawArc(radius, offset, layer,n.getOwnColor());
                    drawNode(layer + 1, n, offset,
                            (((double) n.getSize()) / ((double) node.getSize())) * percentage);
                    offset += radius + degreeOffset;
                }
            }
        }
    }

    public void drawArc(double degree,double degreeOffset,int layer,Color color){
        //Calculating where the arc would end in
        double totalAngle=degree+degreeOffset;

        //Calculating the distances for the given layer the data is in
        double minDistance=getLayerStart(layer);
        double maxDistance=getLayerEnd(layer);

        //Center of image
        int xCenter=buffer.getHeight()/2;
        int yCenter=buffer.getHeight()/2;

        //Bounds indicating the most upper point,most right point,lowest point and most righter point
        int northbound;
        int eastbound;
        int southbound;
        int westbound;

        //Explanation for the quadrants
        // Third quadrant | Fourth Quadrant
        //         3      |       4
        //----------------+---------------
        // Second Quadrant| First Quadrant
        //         2      |       1

        //First and second quadrant
        if(totalAngle<=180){
            if(Math.abs(Math.sin(Math.toRadians(totalAngle))*minDistance)<
                    Math.abs(Math.sin(Math.toRadians(degreeOffset))*minDistance)){
                northbound=(int)(Math.sin(Math.toRadians(totalAngle))*minDistance);
            }else{
                northbound=(int)(Math.sin(Math.toRadians(degreeOffset))*minDistance);

            }

            // First quadrant
            if(totalAngle<=90){
                eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
                southbound=(int)(Math.sin(Math.toRadians(totalAngle))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(totalAngle))*minDistance);
            }
            //Lower half
            else{
                //Starts in first quadrant
                if(degreeOffset>90){
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*minDistance);
                    southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*maxDistance);
                }
                //Starts in second quadrant
                else{
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
                    southbound=(int)(Math.sin(Math.toRadians(90))*maxDistance);
                }
                westbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
            }
        //Ends in third quadrant
        }else if(totalAngle<=270){
            //Starts in first quadrant
            if(degreeOffset<90){
                northbound=(int)(Math.sin(Math.toRadians(totalAngle))*maxDistance);
                eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
                southbound=(int)(Math.sin(Math.toRadians(90))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(180))*maxDistance);
            }
            //Starts in second quadrant
            else if(degreeOffset<180){
                northbound=(int)(Math.sin(Math.toRadians(totalAngle))*maxDistance);
                if(Math.abs(Math.cos(Math.toRadians(totalAngle))*minDistance)<
                        Math.abs(Math.cos(Math.toRadians(degreeOffset))*minDistance)){
                    eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*minDistance);
                }else{
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*minDistance);
                }
                southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(180))*maxDistance);
            }
            //Starts in second quadrant
            else{
                northbound=(int)(Math.sin(Math.toRadians(totalAngle))*maxDistance);
                if(Math.abs(Math.cos(Math.toRadians(totalAngle))*minDistance)<
                        Math.abs(Math.cos(Math.toRadians(degreeOffset))*minDistance)){
                    eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*minDistance);
                }else{
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*minDistance);
                }
                southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*minDistance);
                westbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
            }
        }
        //Ends in fourth quadrant
        else if(totalAngle<=360){
            //Starts in first quadrant
            if(degreeOffset<90){
                northbound=(int)(Math.sin(Math.toRadians(270))*maxDistance);
                if(Math.abs(Math.cos(Math.toRadians(totalAngle))*maxDistance)>
                        Math.abs(Math.cos(Math.toRadians(degreeOffset))*maxDistance)){
                    eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
                }else{
                    eastbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
                }
                southbound=(int)(Math.sin(Math.toRadians(90))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(180))*maxDistance);
            }
            //Starts in second quadrant
            else if(degreeOffset<180){
                northbound=(int)(Math.sin(Math.toRadians(270))*maxDistance);
                eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
                southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*maxDistance);
                westbound=(int)(Math.cos(Math.toRadians(180))*maxDistance);
            }
            //Starts in third quadrant
            else if(degreeOffset<270){
                northbound=(int)(Math.sin(Math.toRadians(270))*maxDistance);
                eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
                if(Math.abs(Math.sin(Math.toRadians(totalAngle))*minDistance)<
                        Math.abs(Math.sin(Math.toRadians(degreeOffset))*minDistance)){
                    southbound=(int)(Math.sin(Math.toRadians(totalAngle))*minDistance);
                }else{
                    southbound=(int)(Math.sin(Math.toRadians(degreeOffset))*minDistance);
                }
                westbound=(int)(Math.cos(Math.toRadians(degreeOffset))*maxDistance);
            }
            //Starts in fourth quadrant
            else{
                northbound=(int)(Math.sin(Math.toRadians(degreeOffset))*maxDistance);
                eastbound=(int)(Math.cos(Math.toRadians(totalAngle))*maxDistance);
                southbound=(int)(Math.sin(Math.toRadians(totalAngle))*minDistance);
                westbound=(int)(Math.cos(Math.toRadians(degreeOffset))*minDistance);
            }
        }
        //Ends in any quadrant but would override content; is dismissed
        else{
            northbound=0;
            eastbound=0;
            southbound=0;
            westbound=0;
        }


        //System.out.println("Angle: "+totalAngle+" offset "+degreeOffset+
        //        " \nNorth: "+ northbound+" East: "+eastbound+" South: "+southbound+" West: "+westbound+
        //        " \n Maxdistance: "+maxDistance+" Mindistance: "+minDistance);

        Graphics2D g2=(Graphics2D)buffer.getGraphics();
        Graphics2D g2Panel=(Graphics2D)drawPanel.getGraphics();

        //Shift the values to the center of image
        northbound+=yCenter;
        eastbound+=xCenter;
        southbound+=yCenter;
        //Correction of errors while calculating the westbound (arcs are cut off on the left side without it)
        westbound+=xCenter-5;

        //g2.setStroke(new BasicStroke(2));
        //g2.drawLine(0,northbound,xCenter*2,northbound);
        //g2.drawLine(eastbound,0,eastbound,yCenter*2);
        //g2.drawLine(0,southbound,xCenter*2,southbound);
        //g2.drawLine(westbound,0,westbound,yCenter*2);


        if(color==null){
            color=Color.orange;
        }
        Color draw=color;
        float[] value=Color.RGBtoHSB(draw.getRed(),draw.getGreen(),draw.getBlue(),null);
        for(int i=0;i<layer;i++){
            value[2]-=0.1*(5.0/(double)layerCount);
        }
        draw=Color.getHSBColor(value[0],value[1],value[2]);
        g2.setColor(draw);
        g2Panel.setColor(Color.darkGray);
        for(int i=westbound-1;i<=eastbound;i++){
            for(int j=northbound-1;j<=southbound;j++){
                double distance=Math.sqrt(Math.pow(i-xCenter,2)+Math.pow(j-yCenter,2));
                double angle=Math.asin(Math.abs((double)j-yCenter)/distance);
                //1 & 2 quadrant
                if(j>yCenter) {
                    //2 quadrant
                    if (i < xCenter) {
                        angle=Math.toRadians(90)-angle;
                        angle += Math.toRadians(90);
                    }
                }
                // 3 & 4 quadrant
                else {
                    //3 quadrant
                    if (i > xCenter) {
                        angle=Math.toRadians(90)-angle;
                        angle += Math.toRadians(90);
                    }
                    angle+=Math.toRadians(180);
                }
                if(angle<=Math.toRadians(totalAngle)&&angle>=Math.toRadians(degreeOffset)){
                    if(distance>minDistance&&distance<maxDistance) {
                        g2.drawOval(i, j, 1, 1);
                        g2Panel.drawOval(i, j, 1, 1);
                    }
                }
            }
        }
    }

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

    /**
     * scale image
     *
     * @param source image to scale
     * @param sizeDestination width of destination image
     * @param fSize x-factor for transformation / scaling
     * @return scaled image
     */
    public static BufferedImage scale(BufferedImage source, int sizeDestination, double fSize) {
        BufferedImage dbi = null;
        if(source != null) {
            dbi = new BufferedImage(sizeDestination, sizeDestination, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fSize, fSize);
            try {
                AffineTransformOp atp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                dbi=atp.filter(source,dbi);
            }catch(ImagingOpException ignored){
                ignored.printStackTrace();
            }
        }
        return dbi;
    }

    @Override
    public void displayClaculatingMesssage() {
        buffer=new BufferedImage(drawPanel.getWidth(),drawPanel.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=(Graphics2D)buffer.getGraphics();
        g.setColor(Color.darkGray);
        g.setFont(new Font("Arial",Font.BOLD,40));
        g.drawString("Calculating Node",drawPanel.getWidth()/2-100,drawPanel.getHeight()/2-70);
        rootPanel.repaint();
    }
}


package ui;

import drawables.*;
import drawables.Point;
import drawables.Polygon;
import utils.Renderer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PgrfFrame extends JFrame implements MouseMotionListener {
    static int FPS = 1000/60;
    private BufferedImage img;
    static int width = 800;
    static int height = 600;
    private JPanel panel;
    private Renderer renderer;
    private int coorX, coorY;
    private int clickX = 300;
    private int clickY = 300;
    private int count = 5;
    private boolean firstClick = true;
    private DrawableType type = DrawableType.LINE;
// zk
    Polygon polygon = new Polygon();

    // todo vypsat klávesové zkratky a co dělají
    // todo do drawables přidat pravidelný polygon
    private List<Drawable> drawables;


    public static void main(String... args){
        PgrfFrame pgrfFrame = new PgrfFrame();
        pgrfFrame.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pgrfFrame.init(width,height);
    }

    private void init(int width, int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Počítačová grafika");
        drawables = new ArrayList<>();

        panel = new JPanel();
        add(panel);



        panel.addMouseMotionListener(this);
/*
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                if (type == DrawableType.LINE){
                    // zadávání úsečky
                    if (firstClick){
                        clickX = e.getX();
                        clickY = e.getY();

                    } else{
                        drawables.add(new Line(clickX, clickY, e.getX(), e.getY()));
                        super.mouseReleased(e);

                    }
                    firstClick = !firstClick; // uloží do firstClick opačnou hodnotu firstClick
                }

                if (type == DrawableType.POLYGON){
                    // todo váš n-úhelník
                }

                if (type == DrawableType.REGULAR_POLYGON) {
                    // todo polygon
                }

            }

        });

*/

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (type == DrawableType.LINE){
                /*
                    if (drawables.size() > 1){
                        drawables.remove(drawables.size()-1);
                    }
                    drawables.add(new Line(clickX, clickY, e.getX(), e.getY()));
                */
                    renderer.lineDDA(clickX, clickY, e.getX(), e.getY());
                }


                // todo dodělat
                if (type == DrawableType.POLYGON){

                }

                if (type == DrawableType.REGULAR_POLYGON){
                    renderer.regularPolygon(clickX, clickY, e.getX(), e.getY(), count);
                }

            }
        });

        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (type == DrawableType.LINE){
                    // zadávání úsečky
                        clickX = e.getX();
                        clickY = e.getY();
                }

                // todo dodělat
                if (type == DrawableType.POLYGON){
                    if (firstClick){
                        drawables.add(polygon);
                        polygon.addPoint(new Point(e.getX(), e.getY()));
                    } else{
                        polygon.addPoint(new Point(e.getX(), e.getY()));
                    }

                }

                if (type == DrawableType.REGULAR_POLYGON){
                    clickX = e.getX();
                    clickY = e.getY();
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (type == DrawableType.LINE){
                    drawables.add(new Line(clickX, clickY, e.getX(), e.getY()));
                }

                if (type == DrawableType.POLYGON){
                }

                if (type == DrawableType.REGULAR_POLYGON){
                    drawables.add(new RegularPolygon(clickX, clickY, e.getX(), e.getY(), count));
                }

            }

        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    // šipka nahoru
                    count++;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    // šipka dolů
                    if (count > 3) {
                        count--;
                    }

                }

                if (e.getKeyCode() == KeyEvent.getExtendedKeyCodeForChar(108)){
                    // písmeno l, změna na čáru
                    type = DrawableType.LINE;
                }

                if (e.getKeyCode() == KeyEvent.getExtendedKeyCodeForChar(112)){
                    // písmeno p, změna na pravidelný polygon
                    type = DrawableType.REGULAR_POLYGON;
                }

                if (e.getKeyCode() == KeyEvent.getExtendedKeyCodeForChar(110)){
                    // písmeno n, změna na nepravidelný polygon
                    type = DrawableType.POLYGON;
                }

                if (e.getKeyCode() == KeyEvent.VK_ADD){
                    // plus na numerické klávesnici
                }
                super.keyReleased(e);
            }
        });

        setLocationRelativeTo(null);

        renderer = new Renderer(img);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 100, FPS);

        draw();
    }

    private void draw() {
        img.getGraphics().fillRect(0,0, img.getWidth(), img.getHeight());

        for (Drawable drawable : drawables){
            drawable.draw(renderer);
        }



        panel.getGraphics().drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null );
        panel.paintComponents(getGraphics());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        coorX = e.getX();
        coorY = e.getY();
    }
}


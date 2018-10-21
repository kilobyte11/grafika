package ui;

import drawables.*;
import drawables.Point;
import drawables.Polygon;
import utils.Renderer;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PgrfFrame extends JFrame implements MouseMotionListener {
    private BufferedImage img;
    static int width = 800;
    static int height = 600;
    private JPanel panel;
    private Renderer renderer;
    private int clickX = 300;
    private int clickY = 300;
    private int count = 5;
    private boolean firstClick = true;
    private DrawableType type = DrawableType.LINE;
    Polygon polygon;

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

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (type == DrawableType.LINE){
                    if (drawables.size() > 1){
                        drawables.remove(drawables.size()-1);
                    }
                    drawables.add(new Line(clickX, clickY, e.getX(), e.getY()));
                }


                if (type == DrawableType.REGULAR_POLYGON){
                    if (drawables.size() > 1){
                        drawables.remove(drawables.size()-1);
                    }
                    drawables.add(new RegularPolygon(clickX, clickY, e.getX(), e.getY(), count));
                }

                draw();
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

                if (type == DrawableType.POLYGON){
                    if (firstClick){
                        polygon = new Polygon();
                        drawables.add(polygon);
                        polygon.addPoint(new Point(e.getX(), e.getY()));
                        firstClick = !firstClick;
                    } else{
                        polygon.addPoint(new Point(e.getX(), e.getY()));
                    }

                }

                if (type == DrawableType.REGULAR_POLYGON){
                    clickX = e.getX();
                    clickY = e.getY();
                }

                draw();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (type == DrawableType.LINE){
                    drawables.add(new Line(clickX, clickY, e.getX(), e.getY()));
                }

                if (type == DrawableType.REGULAR_POLYGON){
                    drawables.add(new RegularPolygon(clickX, clickY, e.getX(), e.getY(), count));
                }

                draw();
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

                super.keyReleased(e);
            }
        });

        setLocationRelativeTo(null);
        renderer = new Renderer(img);
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
    }
}


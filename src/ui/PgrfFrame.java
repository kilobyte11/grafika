package ui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import drawables.*;
import drawables.Point;
import drawables.Polygon;
import transforms.Col;
import utils.Renderer;

public class PgrfFrame extends JFrame implements MouseMotionListener {

    static int FPS = 1000 / 30;
    private BufferedImage img;
    static int width = 800;
    static int height = 600;
    private JPanel panel;
    private Renderer renderer;
    private int clickX, clickY;
    private JLabel lbShape;
    private JLabel lbHelp;
    private String text = "tvar: čára";
    private String textPrev;

    private List<Drawable> drawables;
    private Drawable drawable;
    private boolean firstClickLine = true;
    private DrawableType type = DrawableType.LINE;
    private boolean fillMode = false;
    private boolean scanLine = false;



    public static void main(String... args) {
        PgrfFrame pgrfFrame = new PgrfFrame();
        pgrfFrame.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pgrfFrame.init(width, height);
    }

    private void init(int width, int height) {
        Font fontShape = new Font("Arial", Font.BOLD, 20);
        Font fontHelp = new Font("Arial", Font.BOLD, 15);

        lbShape = new JLabel();
        lbShape.setText(text);
        lbShape.setFont(fontShape);
        lbShape.setForeground(Color.BLACK);
        lbShape.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbShape, BorderLayout.NORTH);

        lbHelp = new JLabel();
        lbHelp.setText(" U = úsečka, N = n-úhelník, F = vyplňování(zapnout/vypnout), SPACE = nový n-úhelník, S = scan-line");
        lbHelp.setFont(fontHelp);
        lbHelp.setForeground(Color.BLACK);
        lbHelp.setHorizontalAlignment(SwingConstants.LEFT);
        add(lbHelp, BorderLayout.SOUTH);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Počítačová grafika");
        drawables = new ArrayList<>();
        panel = new JPanel();
        add(panel);


        panel.addMouseMotionListener(this);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!fillMode) {
                    if (type == DrawableType.LINE) {
                        // zadávání úsečky
                        if (firstClickLine) {
                            clickX = e.getX();
                            clickY = e.getY();
                            drawable = new Line(clickX, clickY, clickX, clickY);
                        } else {
                            drawable = null;
                            drawables.add(new Line(clickX, clickY, e.getX(), e.getY()));
                        }
                        firstClickLine = !firstClickLine;
                    }
                    if (type == DrawableType.POLYGON) {
                        if (drawable == null) {
                            drawable = new Polygon();
                        }
                        if (drawable instanceof Polygon) {
                            if (e.getClickCount() == 2){
                                finishPolygon();
                            } else {
                                ((Polygon) drawable).addPoint(
                                        new Point(e.getX(), e.getY()));
                            }
                        } else {
                            drawable = null;
                        }
                    }


                } else {
                    renderer.seedFillTexture(e.getX(), e.getY(), img.getRGB(e.getX(),e.getY()));
                }

                super.mouseClicked(e);
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ADD) {
                    // plus na numerické klávesnici
                }
                if (e.getKeyCode() == KeyEvent.VK_U) {
                    type = DrawableType.LINE;
                    if (!fillMode){
                        text ="tvar: čára";
                    }

                }
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    type = DrawableType.POLYGON;
                    if (!fillMode){
                        text = "tvar: n-úhelník";
                    }

                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    scanLine = false;
                    finishPolygon();
                }
                if (e.getKeyCode() == KeyEvent.VK_F) {
                    fillMode = !fillMode;
                    if (!fillMode){
                        text = textPrev;
                    }else{
                        textPrev = text;
                        text = "vyplňování";
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    scanLine = !scanLine;
                    if (!scanLine){
                        text = textPrev;
                    }else{
                        textPrev = text;
                        text = "vyplňování - scanline";
                    }
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
                lbShape.setText(text);
            }
        }, 100, FPS);

        draw();
    }

    private void finishPolygon() {
        if (drawable != null) {
            if (drawable instanceof Polygon) {
                ((Polygon) drawable).setDone(false);
                drawables.add(drawable);
                drawable = null;
            }
        }
    }

    private void draw() {
        if (!fillMode) {
            img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight()); //clear
        }

        for (Drawable drawable : drawables) {
            drawable.draw(renderer);
        }
        if (drawable != null) {
            drawable.draw(renderer);
        }

        if(scanLine){
            renderer.scanLine(((Polygon) drawable).getPoints(), Color.BLUE.getRGB(), Color.GREEN.getRGB());
        }

        panel.getGraphics().drawImage(img, 0, 0, null);
        panel.paintComponents(getGraphics());
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (drawable != null) {
            drawable.modifyLastPoint(e.getX(), e.getY());
        }
    }
}
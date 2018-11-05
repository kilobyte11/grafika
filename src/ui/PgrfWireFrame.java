package ui;

import solids.Solid;
import transforms.Camera;
import utils.Transformer;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PgrfWireFrame extends JFrame {
    static int FPS = 1000/30;
    private BufferedImage img;
    static int width = 800;
    static int height = 600;
    private JPanel panel;
    private Camera camera;
    private List<Solid> solids;
    private Transformer transformer;

    public static void main(String[] args) {
        PgrfWireFrame frame = new PgrfWireFrame();
        frame.init(width, height);

    }

    private void init(int width, int height){
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // nastavení frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(width, height);
        setTitle("Drátový model");
        panel = new JPanel();
        add(panel);
        solids = new ArrayList<>();
        transformer = new Transformer(img);
        camera = new Camera();

        // listeners
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });

        // timer pro refresh draw()
        setLocationRelativeTo(null);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        }, 100, FPS);


    }

    private void draw(){
        img.getGraphics().fillRect(0, 0, img.getWidth(), img.getHeight()); //clear

        for (Solid solid : solids){
            transformer.drawWireFrame(solid);
        }

        panel.getGraphics().drawImage(img, 0, 0, null);
        panel.paintComponents(getGraphics());
    }

}

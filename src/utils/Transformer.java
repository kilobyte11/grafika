package utils;

import solids.Solid;
import transforms.Mat4;
import transforms.Point3D;

import java.awt.image.BufferedImage;

public class Transformer {

    private BufferedImage img;

    private Mat4 model;
    private Mat4 view;
    private Mat4 projection;

    public Transformer(BufferedImage img){
        this.img = img;
    }

    // FUNKCE
    public void drawWireFrame(Solid solid){
        // výsledná matice zobrazení
        Mat4 matFinal = model.mul(view).mul(projection);

        // první index: 1. bod, druhý index: druhý bod úsečky
        for (int i = 0; i < solid.getIndicies().size(); i+=2) {
            Point3D p1 = solid.getVerticies().get(solid.getIndicies().get(i));
            Point3D p2 = solid.getVerticies().get(solid.getIndicies().get(i+1));
            transformEdge(matFinal, p1, p2);
        }
    }

    private void transformEdge(Mat4 mat, Point3D p1, Point3D p2){
        // todo 1.) vynásobit body maticí
        // 2.) ořez dle w z bodů
        // 3.) tvorba z vektorů dehomogenizací (Point3D.dehomog())
        // 4.) přepočet souřadnic na výšku/šířku našeho okna (viewport)
        // 5.) výsledek vykreslit - draLIne(..)
    }

    public void lineDDA(int x1, int y1, int x2, int y2, int color) {

        float k, g, h; //G = PŘÍRŮSTEK X, H = PŘÍRŮSTEK Y
        int dy = y2 - y1;
        int dx = x2 - x1;
        k = dy / (float) dx;

        //určení řídící osy
        if (Math.abs(dx) > Math.abs(dy)) {
            g = 1;
            h = k;
            if (x1 > x2) { // prohození
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        } else {
            g = 1 / k;
            h = 1;
            if (y1 > y2) { //otočení
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        }

        float x = x1;
        float y = y1;
        int max = Math.max(Math.abs(dx), Math.abs(dy));

        for (int i = 0; i <= max; i++) {
            drawPixel(Math.round(x), Math.round(y), color);
            x += g;
            y += h;
        }
    }

    private void drawPixel(int x, int y, int color) {
        if (x < 0 || x >= 800) return;
        if (y < 0 || y >= 600) return;
        img.setRGB(x, y, color);
    }
}

package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.*;

import drawables.Edge;
import drawables.Point;

public class Renderer {

    private int color;
    private BufferedImage img;
    private Color texture[][];
    private int[][] texture1[][];

    public Renderer(BufferedImage img) {
        this.img = img;
        color = Color.RED.getRGB();
    }

    private void drawPixel(int x, int y) {
        drawPixel(x, y, color);
    }

    private void drawPixel(int x, int y, int color) {
        if (x < 0 || x >= 800) return;
        if (y < 0 || y >= 600) return;
        img.setRGB(x, y, color);
    }

    public void lineTrivial(int x1, int y1, int x2, int y2) {
        // y = kx + q
        int dx = x1 - x2;
        int dy = y1 - y2;

        if (Math.abs(dx) > Math.abs(dy)) {
            // řídící osa X
            // otočení
            if (x1 > x2) {
                int p = x1;
                x1 = x2;
                x2 = p;
                p = y1;
                y1 = y2;
                //y2 = p;
            }

            float k = (float) dy / (float) dx;
            for (int x = x1; x < x2; x++) {
                int y = y1 + (int) (k * (x - x1));
                drawPixel(x, y);
            }

        } else {
            // řídící osa Y

        }
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

    public void polygon(int x1, int y1, int x2, int y2, int count) {
        double x0 = x2 - x1;
        double y0 = y2 - y1;
        double circleRadius = 2 * Math.PI;
        double step = circleRadius / (double) count;
        for (double i = 0; i < circleRadius; i += step) {
            // dle rotační matice
            double x = x0 * Math.cos(step) + y0 * Math.sin(step);
            double y = y0 * Math.cos(step) - x0 * Math.sin(step);
            lineDDA((int) x0 + x1, (int) y0 + y1,
                    (int) x + x1, (int) y + y1, color);
            // potřeba změnit x0, y0
        }
    }

    public void seedFill(int x, int y, int oldColor, int newColor) {
        if (oldColor == img.getRGB(x, y)) {
            drawPixel(x, y, newColor);

            seedFill(x - 1, y, oldColor, newColor);
            seedFill(x + 1, y, oldColor, newColor);
            seedFill(x, y + 1, oldColor, newColor);
            seedFill(x, y - 1, oldColor, newColor);
        }
    }

    public void seedFillTexture(int x, int y, int oldColor){
        final int ye = Color.YELLOW.getRGB();
        final int bl = Color.BLUE.getRGB();
        int[][] texture1 = {
                { ye, ye, ye, ye, ye },
                { ye, bl, ye, bl, ye },
                { bl, ye, bl, ye, bl },
                { bl, ye, ye, ye, bl },
                { bl, ye, bl, ye, bl }
        };

        
        if (oldColor == img.getRGB(x, y)) {
            drawPixel(x, y, texture1[x%5][y%5]);

            seedFillTexture(x - 1, y, oldColor);
            seedFillTexture(x + 1, y, oldColor);
            seedFillTexture(x, y + 1, oldColor);
            seedFillTexture(x, y - 1, oldColor);
        }
    }


    public void scanLine(List<Point> points, int borderColor, int fillColor) {
        int yMax = 0;
        int yMin = img.getHeight();
        List<Edge> edges = new ArrayList<>();
        List<Integer> intersection = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            Point firstPoint = points.get(i);
            Point secondPoint = points.get((i + 1) % points.size());
            Edge edge = new Edge(firstPoint, secondPoint);   //vytvoreni hrany

            if(edge.isHorizontal() == false)
            {
                edge.order();

                edges.add(edge);
            }

            yMin = edge.yMin(points.get(i).getY());
            yMax = edge.yMax(points.get(i).getY());
        }

        for (int y = yMin; y < yMax; y++) {
            for (Edge edge : edges)
            {
                if (edge.isIntersection(y)){
                    intersection.add(edge.findX(y));    //ukladani pruseciku do seznamu
                }

            }
            Collections.sort(intersection);
            //vykresleni useku
            for (int i = 0; i < intersection.size() - 1 ; i+=2) {
                lineDDA(intersection.get(i), y, intersection.get(i + 1), y, fillColor);
            }

            intersection.clear();

        }
        for (int i = 0; i < points.size(); i++) {
            lineDDA(points.get(i).getX(), points.get(i).getY(),points.get((i + 1) % points.size()).getX(), points.get((i + 1) % points.size()).getY(),borderColor);
        }
            // vytváření úseček (Edge)
            // volání určitých metod
            // hledání hraničních y
            // přidání Edge do seznamu Edges
        }

        /* TODO
            2) definice seznamu úseček
                - z bodů Edges
                - seřadit dle (y1 < y2)
                - vypočítat koeficienty k a q
                - oříznout poslední pixel
            3) for cyklus od yMin do yMax
                - pro každé y hledáme průsečík s úsečkami
                - seznam průsečíků
                - pro sudý počet průsečíků ..
                    - seřadit dle x
            4) obtažení okrajů
         */
}

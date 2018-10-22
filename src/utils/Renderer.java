package utils;

import drawables.Point;
import drawables.Polygon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Renderer {
    private int color;
    private BufferedImage img;

    private int startX, startY;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Renderer(BufferedImage img) {
        this.img = img;
        color = Color.RED.getRGB();
    }

    private void drawPixel(int x, int y){
        drawPixel(x, y, color);
    }

    private void drawPixel(int x, int y, int color) {
        if (x < 0 || x >= 800) return;
        if (y < 0 || y >= 600) return;
        img.setRGB(x, y, color);
    }


    public void lineTrivial(int x1, int y1, int x2, int y2) {
        // y = k*x+q
        int dx = x1 - x2;
        int dy = y1 - y2;

        if (Math.abs(dx) > Math.abs(dy)) {

            if (x1 > x2) {
                int p = x1;
                x1 = x2;
                x2 = p;
                y1 = y2;
            }

            float k = (float) dy / (float) dx;
            for (int x = x1; x < x2; x++) {
                int y = y1 + (int) (k * (x - x1));
                drawPixel(x, y);
            }


        } else {

            if (y1 > y2) {
                int p = y1;
                y1 = y2;
                y2 = p;
                x1 = x2;
            }

            float k = (float) dx / (float) dy;
            for (int y = y1; y < y2; y++) {
                int x = x1 + (int) (k * (y - y1));
                drawPixel(x, y);
            }
        }

    }

    public void lineDDA(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        float x, y, k, g, h;

        k = dy / (float) dx;

        if (Math.abs(dx) > Math.abs(dy)) {
            g = 1;
            h = k;

            if (x1 > x2) {
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

            if (y1 > y2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
        }

        int max = Math.max(Math.abs(dx), Math.abs(dy));
        x = x1;
        y = y1;
        for (int i = 0; i <= max; i++) {
            drawPixel(Math.round(x), Math.round(y));
            x = x + g;
            y = y + h;
        }


    }

    public void polygon(List<Point> points){
        for (int i =1 ; i < points.size(); i++){
            startX = points.get(i-1).getX();
            startY = points.get(i-1).getY();
            int endX = points.get(i).getX();
            int endY = points.get(i).getY();
            lineDDA(startX, startY, endX, endY);
        }
        lineDDA(points.get(0).getX(), points.get(0).getY(), points.get(points.size()-1).getX(), points.get(points.size()-1).getY());
    }

    public void regularPolygon(int x1, int y1, int x2, int y2, int count) {
        double x0 = x2 - x1;
        double y0 = y2 - y1;
        double circleRadius = 2 * Math.PI;
        double step = circleRadius / (double) count;
        int startX = (int) x0 + x1;
        int startY = (int) y0 + y1;

        for (double i = 0; i < count+4; i += step) {
            double x = x0 * Math.cos(i) + y0 * Math.sin(i);
            double y = y0 * Math.cos(i) - x0 * Math.sin(i);
            lineDDA(startX, startY, (int) x + x1, (int) y + y1);
            startX = (int) x + x1;
            startY = (int) y + y1;
        }


    }

    public void seedFill(int x, int y, int oldColor, int newColor){
        if (oldColor == img.getRGB(x, y)){
            drawPixel(x, y, Color.BLUE.getRGB());
            seedFill(x+1, y, oldColor, newColor);
            seedFill(x-1, y, oldColor, newColor);
            seedFill(x, y+1, oldColor, newColor);
            seedFill(x, y-1, oldColor, newColor);
            // todo
        }
    }

}

package drawables;

import utils.Renderer;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements Drawable{

    List<Point> points;

    public Polygon(){
        points =  new ArrayList<>();
    }

    public void addPoint(Point p){
        points.add(p);
    }

    @Override
    public void draw(Renderer renderer) {
        if (points.size() > 1){
            for (int i = 0; i < points.size(); i++){
                Point point1 = points.get(i);
                Point point2 = points.get((i+1) % points.size());
                renderer.lineDDA(point1.getX(), point1.getY(), point2.getX(), point2.getY());
            }
        }
        //renderer.polygon(points);
    }

    @Override
    public void modifyLastPoint(int x, int y) {
        // todo
    }
}

package drawables;

import utils.Renderer;

import java.util.ArrayList;
import java.util.List;

public class RegularPolygon implements Drawable{
    int x1, y1, x2, y2, count;

    public RegularPolygon(int x1, int y1, int x2, int y2, int count){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.count = count;
    }


    @Override
    public void draw(Renderer renderer) {
        renderer.regularPolygon(x1, y1, x2, y2, count);
    }
}
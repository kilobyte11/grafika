package drawables;

import utils.Renderer;

import java.awt.*;

public interface Drawable {

    void draw(Renderer renderer);

    void modifyLastPoint(int x, int y);

    int getColor();

    default void setColor(int color) {
    }

    default int getFillColor() {
        return Color.BLACK.getRGB();
    }
}
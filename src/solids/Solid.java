package solids;

import transforms.Point3D;

import java.util.List;

public interface Solid {

    // seznam bodů objektu
    List<Point3D> getVerticies();
    // indexy bodů společně propojených
    List<Integer> getIndicies();
}

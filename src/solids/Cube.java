package solids;

import transforms.Point3D;

public class Cube extends SolidData {
    public Cube(double size) {
        verticies.add(new Point3D(0, 0, 0));   // 0.
        verticies.add(new Point3D(0, size, 0));   // 1.
        verticies.add(new Point3D(size, size, 0));   // 2.
        verticies.add(new Point3D(size, 0, 0));   // 3.
        verticies.add(new Point3D(0, 0, size));   // 4.
        verticies.add(new Point3D(0, size, size));   // 5.
        verticies.add(new Point3D(size, size, size));   // 6.
        verticies.add(new Point3D(size, 0, size));   // 7.
        // todo zbylých 6 dokončit

        indicies.add(0);
        indicies.add(1); // 1. úsečka
        indicies.add(1);
        indicies.add(2); // 2. úsečka
        indicies.add(2);
        indicies.add(3); // 3. úsečka
        indicies.add(3);
        indicies.add(0); // 4. úsečka

        indicies.add(0);
        indicies.add(4); // 5. úsečka
        indicies.add(1);
        indicies.add(5); // 6. úsečka
        indicies.add(2);
        indicies.add(6); // 7. úsečka
        indicies.add(3);
        indicies.add(7); // 8. úsečka

        indicies.add(4);
        indicies.add(5); // 9. úsečka
        indicies.add(5);
        indicies.add(6); // 10. úsečka
        indicies.add(6);
        indicies.add(7); // 11. úsečka
        indicies.add(7);
        indicies.add(0); // 12. úsečka
        // todo 11 dalších úseček (a.k.a. 2*11)
    }
}

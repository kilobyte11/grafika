package drawables;

public class Edge {

    int x1, y1, x2, y2;
    float k, q;

    public Edge(Point p1, Point p2){
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
        compute();
    }

    public boolean isHorizontal(){
        // zjistí zda je úsečká horizontální
        if (y1 == y2){
            return true;
        }
        else {
            return false;
        }
    }

    public void order(){
        //seřadí dle y1 < y2
        if (y1 > y2){
            int pom = y2;
            y2 = y1;
            y1 = pom;
            pom = x2;
            x2 = x1;
            x1 = pom;
        }

    }

    public void cut(){
        // ořízne poslední pixel
        y2 =-1;
        x2 =-1;
    }

    public void compute(){
        // vypočítá k a q
        int dy = y2 - y1;
        int dx = x2 - x1;
        k = dy / (float) dx;
        q = y1 - k*x1;

    }

    public int findX(int y){
        // vypočítá X dle y,k,q
        // y = kx + q
        int x = (int) ((y - q)/k);
        return x;
    }

    public boolean isIntersection(int y){
        // - true když y > y1 && y < y2
        if (y >= y1 && y < y2){
            return true;
        } else{
            return false;
        }

    }

    public int yMin(int yMin){
        //vrátí nejmenší y
        if (y1 < yMin){
            yMin = y1;
        }
        if (y2 < yMin){
            yMin = y2;
        }
        return yMin;
    }
    public int yMax(int yMax){
        //vrátí největší y
        if (y1 > yMax){
            yMax = y1;
        }
        if (y2 > yMax){
            yMax = y2;
        }
        return yMax;
    }

    public boolean inside(Point p){
        // pozor na orientaci!! kdyžtak otočit
        Point v1 = new Point(x2 - x1, y2 - y1);
        Point n1 = new Point(v1.getY(), -v1.getX());
        Point v2 = new Point(p.getX() - x1, p.getY() - y1);
        return (n1.getX() * v2.getX() + n1.getY() * v2.getY()) < 0;
    }

    public Point intersection(Point v1, Point v2) {
        float px = ((v1.getX() * v2.getY() - v1.getY() * v2.getX()) * (x1 - x2) - (x1 * y2 - y1 * x2) * (v1.getX() - v2.getX())) / (float) ((v1.getX() - v2.getX()) * (y1 - y2) - (x1 - x2) * (v1.getY() - v2.getY()));
        float py = ((v1.getX() * v2.getY() - v1.getY() * v2.getX()) * (y1 - y2) - (x1 * y2 - y1 * x2) * (v1.getY() - v2.getY())) / (float) ((v1.getX() - v2.getX()) * (y1 - y2) - (x1 - x2) * (v1.getY() - v2.getY()));
        return new Point(Math.round(px), Math.round(py));
    }

}
package drawables;

public class Edge {

    int x1, y1, x2, y2;
    float k, q;

    public Edge(Point p1, Point p2){
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
    }

    public boolean isHorizontal(){
        return false; //TODO
    }

    public void order(){
        //seřadit dle y1 < y2
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
        // oříznout poslední pixel
        y2 =-1;
        x2 =-1;
    }

    public void compute(){
        // vypočítat k a q
        int dy = y2 - y1;
        int dx = x2 - x1;
        k = dy / (float) dx;
        q = dy - k*dx;

    }

    public int findX(int y){
        // vypočítat X dle y,k,q
        // y = kx + q
        int x = (int) ((y - q)/k);
        return x;
    }

    public boolean isIntersection(int y){
        // - true když y > y1 && y < y2
        if (y > y1 && y < y2){
            return true;
        } else{
            return false;
        }

    }

    public int yMin(int yMin){
        //dle y1, y2 a yMin rozhodnout které vracíme
        if (y1 < yMin){
            yMin = y1;
        }
        if (y2 < yMin){
            yMin = y2;
        }
        return yMin;
    }
    public int yMax(int yMax){
        //dle y1,y2,yMax navrátit maxHodnotu
        if (y1 > yMax){
            yMax = y1;
        }
        if (y2 > yMax){
            yMax = y2;
        }
        return yMax;
    }
}
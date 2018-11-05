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
        // TODO seřadit dle y1 < y2
    }

    public void cut(){
        // todo oříznout poslední pixel
    }

    public void compute(){
        // todo vypočítat k a q
    }

    public int findX(int y){
        return 0; // TODO vypočítat X dle y,k,q
    }

    public boolean isIntersection(int y){
        return false; //TODO - true když y > y1 && y < y2
    }

    public int yMin(int yMin){
        return 0; //todo dle y1, y2 a yMin rozhodnout které vracíme
    }
    public int yMax(int yMax){
        return 0; //todo dle y1,y2,yMax navrátit maxHodnotu
    }
}
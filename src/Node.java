public class Node {
    private Point pos;
    private int g;
    private int h;
    private int f;
    private Node prev;
    public Node(Point pos, int g, int h, Node prev){
        this.pos = pos;
        this.g = g;
        this.h = h;
        this.prev = prev;
    }
    public int getF(){
        return g+h;
    }
    public Point getPos(){
        return pos;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public Node getPrev() {
        return prev;
    }

    public boolean equals(Object other){
        return !(other == null) && other.getClass().equals(this.getClass())
                && ((Node)other).g == this.g && ((Node)other).h == this.h && ((Node)other).h == this.h
                && ((Node)other).prev == this.prev && ((Node)other).pos.x == this.pos.x
                && ((Node)other).pos.y == this.pos.y;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int hashCode(){
        int code = 0;
        code += pos.hashCode();
        code += g;
        code += h;
        code += f;
        code += prev.hashCode();
        return code;
    }
}

import processing.core.PImage;
import java.util.List;

abstract class Entity {
    protected String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Entity(String id, Point position, List<PImage> images, int imageIndex){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = imageIndex;
    }

    Point getPosition(){
        return this.position;
    }

    void setPosition(Point p){
        this.position = p;
    }

    PImage getCurrentImage(){
        return images.get(imageIndex);
    }

    List<PImage> getImages(){
        return this.images;
    }

    int getImageIndex(){return this.imageIndex;}

    void setImageIndex(int i){this.imageIndex = i;}
}

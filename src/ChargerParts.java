import processing.core.PImage;

import java.util.List;
public abstract class ChargerParts extends Entity {

    private int totalParts = 0;
    public ChargerParts(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }
    public int getTotalParts(){return totalParts;}
    public void addPart(WorldModel world){
        this.totalParts +=1;
    world.removeEntity(this);}


}

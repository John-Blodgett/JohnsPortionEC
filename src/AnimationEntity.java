import processing.core.PImage;

import java.util.List;

abstract class AnimationEntity extends ActiveEntity {
    private int animationPeriod;

    public AnimationEntity(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,int animationPeriod){
        super(id,position,images,imageIndex,actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    void executeActivity(WorldModel world,
                         ImageStore imageStore, EventScheduler scheduler){
        scheduler.unscheduleAllEvents(this);
        world.removeEntity( this);
    }

    //additional schedule actions is in the abstract class MoveEntity

    public void nextImage()
    {
        setImageIndex((getImageIndex()+1)%getImages().size());
    }

    int getAnimationPeriod(){return this.animationPeriod;}

}

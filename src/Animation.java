public class Animation extends Action {

    private int repeatCount;

    public Animation (ActiveEntity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler,int repeatCount){
        super(world, imageStore, scheduler, entity);
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler)
    {
        AnimationEntity e = (AnimationEntity)entity;
        e.nextImage();

        if (repeatCount != 1)
        {
            scheduler.scheduleEvent(e,
                    new Animation(e,
                            world,imageStore, scheduler, Math.max(repeatCount - 1, 0)),
                    e.getAnimationPeriod());

        }
    }
}

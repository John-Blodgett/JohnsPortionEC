public class Activity extends Action {

    public Activity(ActiveEntity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler){

        super(world, imageStore, scheduler, entity);
    }

    public void executeAction(EventScheduler scheduler){
        entity.executeActivity(world, imageStore, scheduler);
    }
}

abstract class Action {
    WorldModel world;
    ImageStore imageStore;
    EventScheduler scheduler;
    ActiveEntity entity;

    public Action(WorldModel world, ImageStore imageStore, EventScheduler scheduler, ActiveEntity entity){
        this.world = world;
        this.imageStore = imageStore;
        this.scheduler = scheduler;
        this.entity = entity;
    }

    abstract void executeAction( EventScheduler scheduler);
}

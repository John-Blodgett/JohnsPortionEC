import java.util.Random;

public class StudentFactory {
    private static Random rand = new Random();
//    currently setup to select a random student
    private static final String MAD_STUDENT_ID = "madStudent";
    private static final String INQ_STUDENT_ID = "inqStudent";
    private static final String ANNOY_STUDENT_ID = "annoyStudent";
    private EventScheduler scheduler;
    public static Student addStudent(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        int studentNum = rand.nextInt(3);
        Point start = new Point(10,7);
        if (studentNum == 0){
            MadStudent student = new MadStudent(MAD_STUDENT_ID, start, imageStore.getImageList(imageStore, MAD_STUDENT_ID),0,0,0);
            world.tryAddEntity(student);
            scheduler.scheduleEvent(student, new Activity(student, world, imageStore, scheduler), 0);
            return student;
        }
        else if (studentNum == 1){
            InquisiteStudent student = new InquisiteStudent(INQ_STUDENT_ID,  start, imageStore.getImageList(imageStore, INQ_STUDENT_ID),0,0,0);
            world.tryAddEntity(student);
            scheduler.scheduleEvent(student, new Activity(student, world, imageStore, scheduler), 0);
            return student;
        }
        else{
            AnnoyingStudent student = new AnnoyingStudent(ANNOY_STUDENT_ID,  start, imageStore.getImageList(imageStore, ANNOY_STUDENT_ID),0,0,0);
            world.tryAddEntity(student);
            scheduler.scheduleEvent(student, new Activity(student, world, imageStore, scheduler), 0);

            return student;
        }
    }
}

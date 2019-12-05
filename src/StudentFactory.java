import java.util.ArrayList;
import java.util.List;
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
        List<Point> alts = new ArrayList<>();
        alts.add(0, new Point(11, 7));
        alts.add(0, new Point(10, 8));
        alts.add(0, new Point(11, 8));
        alts.add(0, new Point(10, 9));
        alts.add(0, new Point(11, 9));
        alts.add(0, new Point(10, 10));
        alts.add(0, new Point(11, 10));
        alts.add(0, new Point(12, 10));
        alts.add(0, new Point(13, 10));
        alts.add(0, new Point(14, 10));
        alts.add(0, new Point(9, 10));
        alts.add(0, new Point(8, 10));

        Point start = new Point(10,7);
        int i = 0;
            while (world.isOccupied(start))
            {
                start = alts.get(i);
                        i++;
            }

            if (studentNum == 0) {
                MadStudent student = new MadStudent(MAD_STUDENT_ID, start, imageStore.getImageList(imageStore, MAD_STUDENT_ID), 0, 550, 0);
                world.tryAddEntity(student);
                scheduler.scheduleEvent(student, new Activity(student, world, imageStore, scheduler), 550);
                return student;
            } else if (studentNum == 1) {
                InquisiteStudent student = new InquisiteStudent(INQ_STUDENT_ID, start, imageStore.getImageList(imageStore, INQ_STUDENT_ID), 0, 550, 0);
                world.tryAddEntity(student);
                scheduler.scheduleEvent(student, new Activity(student, world, imageStore, scheduler), 550);
                return student;
            } else {
                AnnoyingStudent student = new AnnoyingStudent(ANNOY_STUDENT_ID, start, imageStore.getImageList(imageStore, ANNOY_STUDENT_ID), 0, 550, 0);
                world.tryAddEntity(student);

                scheduler.scheduleEvent(student, new Activity(student, world, imageStore, scheduler), 550);

                return student;
            }

    }
}

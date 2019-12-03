import java.util.List;
import java.util.Optional;

import processing.core.PImage;

final class Background
{
   public String id;
   public List<PImage> images;
   public int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }
   public static Optional<PImage> getBackgroundImage(WorldModel world,
                                                     Point pos)
   {
      if (world.withinBounds(pos))
      {
         return Optional.of(getCurrentImage(WorldModel.getBackgroundCell(world, pos)));
      }
      else
      {
         return Optional.empty();
      }
   }
   public static PImage getCurrentImage(Object entity)
   {
      if (entity instanceof Background)
      {
         return ((Background)entity).images
                 .get(((Background)entity).imageIndex);
      }
      else if (entity instanceof Entity)
      {
         return (((Entity) entity).getImages().get(((Entity) entity).getImageIndex()));
         //return ((Entity)entity).images.get(((Entity)entity).imageIndex);
      }
      else
      {
         throw new UnsupportedOperationException(
                 String.format("getCurrentImage not supported for %s",
                         entity));
      }
   }
}

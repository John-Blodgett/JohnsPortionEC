import processing.core.PImage;

import java.util.*;

/*
WorldModel ideally keeps track of the actual size of our grid world and what is in that world
in terms of entities and background elements
 */

final class WorldModel
{
   public int numRows;
   public int numCols;
   public Background background[][];
   public Entity occupancy[][];
   public Set<Entity> entities;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new Entity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }

   public void tryAddEntity(Entity entity)
   {
      if (isOccupied(entity.getPosition()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      addEntity(entity);
   }
   public boolean withinBounds(Point pos)
   {
      return pos.y >= 0 && pos.y < numRows &&
              pos.x >= 0 && pos.x < numCols;
   }
   public Optional<PImage> getBackgroundImage(WorldModel world,
                                                     Point pos)
   {
      if (world.withinBounds(pos))
      {
         return Optional.of(Background.getCurrentImage(getBackgroundCell(world, pos)));
      }
      else
      {
         return Optional.empty();
      }
   }
   public void setBackground(Point pos,
                                    Background background)
   {
      if (withinBounds(pos))
      {
         setBackgroundCell( pos, background);
      }
   }
   public static Background getBackgroundCell(WorldModel world, Point pos)
   {
      return world.background[pos.y][pos.x];
   }

   public void setBackgroundCell(Point pos,
                                        Background background)
   {
      this.background[pos.y][pos.x] = background;
   }
   public  Optional<Entity> getOccupant( Point pos)
   {
      if (isOccupied( pos))
      {
         return Optional.of(getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }

   public  Entity getOccupancyCell( Point pos)
   {
      return occupancy[pos.y][pos.x];
   }

   public void setOccupancyCell( Point pos,
                                       Entity entity)
   {
      this.occupancy[pos.y][pos.x] = entity;
   }
   public boolean isOccupied( Point pos)
   {
      return withinBounds(pos) &&
              getOccupancyCell( pos) != null;
   }
   public void addEntity(Entity entity)
   {
      if (withinBounds( entity.getPosition()))
      {
         setOccupancyCell(entity.getPosition(), entity);
         entities.add(entity);
      }
   }

   public void moveEntity( Entity entity, Point pos)
   {
      Point oldPos = entity.getPosition();
      if (withinBounds(pos) && !pos.equals(oldPos))
      {
         setOccupancyCell( oldPos, null);
         removeEntityAt(pos);
         setOccupancyCell( pos, entity);
         entity.setPosition(pos);
      }
   }

   public void removeEntity( Entity entity)
   {
      removeEntityAt( entity.getPosition());
   }


   public void removeEntityAt( Point pos)
   {
      if (withinBounds( pos)
              && getOccupancyCell(pos) != null)
      {
         Entity entity = getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPosition(new Point(-1, -1));
         entities.remove(entity);
         setOccupancyCell( pos, null);
      }
   }
   public Optional<Entity> findNearest(WorldModel world, Point pos,
                                              Class kind)
   {
      List<Entity> ofType = new LinkedList<>();
      for (Entity entity : world.entities)
      {
         if (kind== entity.getClass())
         {
            ofType.add(entity);
         }
      }

      return nearestEntity(ofType, pos);
   }

   public Optional<Point> findOpenAround( Point pos)
   {
      for (int dy = -Fish.FISH_REACH; dy <= Fish.FISH_REACH; dy++)
      {
         for (int dx = -Fish.FISH_REACH; dx <= Fish.FISH_REACH; dx++)
         {
            Point newPt = new Point(pos.x + dx, pos.y + dy);
            if (withinBounds( newPt) &&
                    !isOccupied(newPt))
            {
               return Optional.of(newPt);
            }
         }
      }

      return Optional.empty();
   }


   public static Optional<Entity> nearestEntity(List<Entity> entities,
                                                Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         Entity nearest = entities.get(0);
         int nearestDistance = Point.distanceSquared(nearest.getPosition(), pos);

         for (Entity other : entities)
         {
            int otherDistance = Point.distanceSquared(other.getPosition(), pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
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

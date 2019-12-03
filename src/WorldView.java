import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

/*
WorldView ideally mostly controls drawing the current part of the whole world
that we can see based on the viewport
*/

final class WorldView
{
   public PApplet screen;
   public WorldModel world;
   public int tileWidth;
   public int tileHeight;
   public Viewport viewport;

   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
      int tileWidth, int tileHeight)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.viewport = new Viewport(numRows, numCols);
   }
   public static void shiftView(WorldView view, int colDelta, int rowDelta)
   {
      int newCol = clamp(view.viewport.col + colDelta, 0,
              view.world.numCols - view.viewport.numCols);
      int newRow = clamp(view.viewport.row + rowDelta, 0,
              view.world.numRows - view.viewport.numRows);

      Viewport.shift(view.viewport, newCol, newRow);
   }
   public static int clamp(int value, int low, int high)
   {
      return Math.min(high, Math.max(value, low));
   }

   public void drawBackground()
   {
      for (int row = 0; row < this.viewport.numRows; row++)
      {
         for (int col = 0; col < this.viewport.numCols; col++)
         {
            Point worldPoint = this.viewport.viewportToWorld(this.viewport, col, row);
            Optional<PImage> image = world.getBackgroundImage(this.world,
                    worldPoint);
            if (image.isPresent())
            {
               this.screen.image(image.get(), col * this.tileWidth,
                       row * this.tileHeight);
            }
         }
      }
   }
   public void drawViewport()
   {
      drawBackground();
      drawEntities();
   }
   public void drawEntities()
   {
      for (Entity entity : this.world.entities)
      {
         Point pos = entity.getPosition();

         if (Viewport.contains(this.viewport, pos))
         {
            Point viewPoint = Viewport.worldToViewport(this.viewport, pos.x, pos.y);
            this.screen.image(Background.getCurrentImage(entity),
                    viewPoint.x * this.tileWidth, viewPoint.y * this.tileHeight);
         }
      }
   }
}

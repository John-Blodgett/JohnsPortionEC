/*
Viewport ideally helps control what part of the world we are looking at for drawing only what we see
Includes helpful helper functions to map between the viewport and the real world
 */


final class Viewport
{
   public int row;
   public int col;
   public int numRows;
   public int numCols;

   public Viewport(int numRows, int numCols)
   {
      this.numRows = numRows;
      this.numCols = numCols;
   }
   public Point viewportToWorld(Viewport viewport, int col, int row)
   {
      return new Point(col + viewport.col, row + viewport.row);
   }
   public static void shift(Viewport viewport, int col, int row)
   {
      viewport.col = col;
      viewport.row = row;
   }
   public static Point worldToViewport(Viewport viewport, int col, int row)
   {
      return new Point(col - viewport.col, row - viewport.row);
   }
   public static boolean contains(Viewport viewport, Point p)
   {
      return p.y >= viewport.row && p.y < viewport.row + viewport.numRows &&
              p.x >= viewport.col && p.x < viewport.col + viewport.numCols;
   }
}

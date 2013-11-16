package sheepdog.g3;

import sheepdog.sim.Point;

public abstract class DogBrain {
  private int mId;
  private boolean mAdvanced;
  private int mNblacks;
  
  public DogBrain(int id, boolean advanced, int nblacks) {
    mId = id - 1;
    mAdvanced = advanced;
    mNblacks = nblacks;
  }
  
  public abstract Point getMove(Point[] dogs, Point[] whiteSheep, Point[] blackSheep);
  
}

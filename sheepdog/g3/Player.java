package sheepdog.g3;

import java.util.Arrays;
import sheepdog.sim.Point;

public class Player extends sheepdog.sim.Player  {
  private int mNblacks;
  private boolean mMode;
  private DogBrain mBrain;
  
  @Override
  public void init(int nblacks, boolean mode) {
    // TODO Auto-generated method stub
    mNblacks = nblacks;
    mMode = mode;
    // Initialize new Dog Brain
    mBrain = new StraightLineBrain(id, mode, nblacks);
  }

  @Override
  public Point move(Point[] dogs, Point[] sheeps) {
    // TODO Auto-generated method stub
    Point[] blackSheep = Arrays.copyOfRange(sheeps, 0, mNblacks);
    Point[] whiteSheep = Arrays.copyOfRange(sheeps, mNblacks, sheeps.length);
    return mBrain.getMove(dogs, whiteSheep, blackSheep);
  }

}

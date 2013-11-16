package g3;

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
  }

  @Override
  public Point move(Point[] dogs, Point[] sheeps) {
    // TODO Auto-generated method stub
    Point[] whiteSheep = Arrays.copyOfRange(sheeps, 0, sheeps.length - mNblacks);
    Point[] blackSheep = Arrays.copyOfRange(sheeps, sheeps.length - mNblacks, sheeps.length);
    return mBrain.getMove(dogs, whiteSheep, blackSheep);
  }

}

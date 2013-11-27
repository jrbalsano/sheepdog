package sheepdog.g3;

import java.util.Arrays;
import sheepdog.sim.Point;

public class Player extends sheepdog.sim.Player  {
  private int mNblacks;
  private boolean mMode;
  private DogBrain mBrain;
  
  @Override
  public void init(int nblacks, boolean mode) {
    mNblacks = nblacks;
    mMode = mode;
//    mBrain = new StraightLineBrainGap(id, mode, nblacks);
//    mBrain = new StraightLineBrainFar(id, mode, nblacks);
//    mBrain = new StraightLineBrainMe(id, mode, nblacks);
//    mBrain = new SteinerBrain(id, mode, nblacks);
    mBrain = new ConvexHullBrain(id, mode, nblacks);
  }

  @Override
  public Point move(Point[] dogs, Point[] sheeps) {
	  
	  if(SteinerBrain.removal==1)
		  mBrain = new StraightLineBrainMe(id, mMode, mNblacks);
		  
	  
    return mBrain.getMove(dogs, sheeps);
  }

}

package sheepdog.g3;

import java.util.ArrayList;
import java.util.Arrays;

import sheepdog.sim.Point;

public class Player extends sheepdog.sim.Player  {
  private int mNblacks;
  private boolean mMode;
  private DogBrain mBrain;
  static boolean sweeperComplete = false;
  
  @Override
  public void init(int nblacks, boolean mode) {
    mNblacks = nblacks;
    mMode = mode;
//    mBrain = new StraightLineBrainGap(id, mode, nblacks);
//    mBrain = new StraightLineBrainFar(id, mode, nblacks);
//    mBrain = new StraightLineBrainMe(id, mode, nblacks);
//    mBrain = new SteinerBrain(id, mode, nblacks);
//    mBrain = new ConvexHullBrain(id, mode, nblacks);
  }

  @Override
  public Point move(Point[] dogs, Point[] sheeps) {
	  
	  ArrayList<Integer> undeliveredIndices = Calculator.undeliveredBlackSheep(sheeps);
	  System.out.println("Number of Undelivered Sheep: "+undeliveredIndices.size());
	  if(mMode || sweeperComplete || dogs.length < 26)
//		  if(dogs.length>=10 && !mMode)
//			  mBrain = new ConvexHullBrain(id, mMode, mNblacks);
//		  else
			  mBrain = new StraightLineBrainMe(id, mMode, mNblacks);

	  else
		  mBrain = new SweeperBrain(id, mMode, mNblacks);
	  
//	  if(SteinerBrain.removal==1)
//		  mBrain = new StraightLineBrainMe(id, mMode, mNblacks);
		  
	  
    return mBrain.getMove(dogs, sheeps);
  }

}

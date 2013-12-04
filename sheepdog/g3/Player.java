package sheepdog.g3;

import java.util.ArrayList;
import java.util.Arrays;

import sheepdog.g3.Calculator.SIDE;
import sheepdog.sim.Point;

public class Player extends sheepdog.sim.Player  {
    private int mNblacks;
    private boolean mMode;
    private DogBrain mBrain = null;
    boolean sweeperComplete = false;
    boolean hullComplete = false;

    @Override
    public void init(int nblacks, boolean mode) {
        mNblacks = nblacks;
        mMode = mode;
        sweeperComplete = false;
        //    mBrain = new StraightLineBrainGap(id, mode, nblacks);
        //    mBrain = new StraightLineBrainFar(id, mode, nblacks);
        //    mBrain = new StraightLineBrainMe(id, mode, nblacks);
        //    mBrain = new SteinerBrain(id, mode, nblacks);
        //    mBrain = new ConvexHullBrain(id, mode, nblacks); 

    }

    @Override
    public Point move(Point[] dogs, Point[] sheeps) {
        if(!sweeperComplete) {
            sweeperComplete = isSweeperComplete(dogs,sheeps);
        }

        if(mMode || sweeperComplete || dogs.length < 26) {
            boolean undeliveredBlackSheepExist = Calculator.undeliveredBlackSheep(Arrays.copyOfRange(sheeps, 0, mNblacks)).size() > 0;
            if(!hullComplete && dogs.length>=2 && undeliveredBlackSheepExist) {
                mBrain = new ConvexHullBrain(id, mMode, mNblacks);
            }
            else {
                hullComplete = true;
                mBrain = new StraightLineBrainMe(id, mMode, mNblacks);
            }
        }
        else {
            mBrain = new SweeperBrain(id, mMode, mNblacks);
        }

        return mBrain.getMove(dogs, sheeps);
    }

    boolean isSweeperComplete(Point [] dogs, Point[] sheeps) {

        ArrayList<Integer> undeliveredIndices = new ArrayList<Integer>();

        if(mMode)
        {
            Point[] blackSheep = Arrays.copyOfRange(sheeps, 0, mNblacks);
            for(int i=0;i<blackSheep.length;i++)
            {
                if(blackSheep[i].x > 50)
                    undeliveredIndices.add(i);
            }
        }
        else
        {
            for(int i=0;i<sheeps.length;i++)
            {
                if(sheeps[i].x > 50)
                    undeliveredIndices.add(i);
            }
        }

        if((float)undeliveredIndices.size()/sheeps.length <= 0.15)
            return true;
        else
            return false;
    }

}


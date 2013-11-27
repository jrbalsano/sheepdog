package sheepdog.g3;

import java.util.ArrayList;

import sheepdog.sim.Point;

public class ConvexHullBrain extends DogBrain {

    @Override
    public Point getAdvancedMove(Point[] dogs, Point[] whiteSheep,
            Point[] blackSheep) {
        // TODO Expand strategy to advanced game
        return null;
    }

    @Override
    public Point getBasicMove(Point[] dogs, Point[] sheep) {
        // TODO Implement convex hull strategy
        return null;
    }
    
    /**
     * Generates a convex hull for a given array of sheep
     * @param sheep
     * @return An array list of the indices of the points making up the convex hull
     */
    private ArrayList<Integer> getConvexHull(Point[] sheep) {
        
    }
    
    /**
     * Chooses the next sheep this dog should move toward
     * @param hull
     * @param sheep
     * @return
     */
    private Point getSheepToTravelTo(ArrayList<Integer> hull, Point[] sheep) {
        
    }
    
    /**
     * Chooses the best location to stand within Calculator.DOG_MAX_SPEED of a while
     * moving along a line toward b
     */
    private Point getStepAlongPathTo(Point a, Point b) {
        
    }

}

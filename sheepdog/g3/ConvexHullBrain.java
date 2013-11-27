package sheepdog.g3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

import sheepdog.g3.Calculator.SIDE;
import sheepdog.sim.Point;

public class ConvexHullBrain extends DogBrain {

    public ConvexHullBrain(int id, boolean advanced, int nblacks) {
        super(id, advanced, nblacks);
    }

    @Override
    public Point getAdvancedMove(Point[] dogs, Point[] whiteSheep,
            Point[] blackSheep) {
        // TODO Expand strategy to advanced game
        return null;
    }

    @Override
    public Point getBasicMove(Point[] dogs, Point[] sheep) {
        if (Calculator.getSide(dogs[mId].x) == SIDE.BLACK_GOAL_SIDE) {
            return Calculator.getMoveTowardPoint(dogs[mId], new Point(50.0, 50.0));
        }
        ArrayList<Integer> undeliveredIndices = Calculator.undeliveredBlackSheep(sheep);
        ArrayList<Point> hull = getConvexHull(sheep, undeliveredIndices);
        int target = mId % hull.size();
        return Calculator.getMoveTowardPoint(dogs[mId], hull.get(target));
    }
    
    /**
     * Generates a convex hull for a given array of sheep
     * @param sheep
     * @return An array list of the points making up the convex hull
     */
    private ArrayList<Point> getConvexHull(Point[] sheep, ArrayList<Integer> undeliveredIndices) {
        ArrayList<Point> hull = new ArrayList<Point>();
        Point max = sheep[undeliveredIndices.get(0)], min = sheep[undeliveredIndices.get(0)];
        
        for(Integer i : undeliveredIndices) {
            Point p = sheep[i];
            if (p.y > max.y) { max = p; }
            if (p.y < min.y) { min = p; }
        }
        Point wallMax = new Point(Calculator.FIELD_SIZE*0.5, max.y);
        Point wallMin = new Point(Calculator.FIELD_SIZE*0.5, min.y);
        hull.add(wallMax);
        hull.add(wallMin);
        
        for(;;) {
            Point p1 = hull.get(hull.size()-1);
            Point p2 = null;
            double minAngle = Math.PI+1;
            int minIndex = -1;
            for (Integer i : undeliveredIndices) {
                Point candidate = sheep[i];
                double angle = Calculator.getAngleOfTrajectory(p1, candidate);
                if (angle < minAngle) {
//                    System.out.println("Comparing points " + p1 + ", " + candidate);
                    minAngle = angle;
                    p2 = candidate;
                    minIndex = i;
//                    System.out.println("min angle " + minAngle );
                }
            }
            hull.add(p2);
//            System.out.println("Choosing point " + p2 + "index" + minIndex);
//            System.out.println(undeliveredIndices.toString());
            if (undeliveredIndices.size() > 0) {
                undeliveredIndices.remove(new Integer(minIndex));
            }
            if (p2 == max) {
                break;
            }
        }
        
        return hull;
        
    }
    
    /**
     * Chooses the next sheep this dog should move toward
     * @param hull
     * @param sheep
     * @return
     */
    private Point getSheepToTravelTo(ArrayList<Integer> hull, Point[] sheep) {
        return null;
    }
    
    /**
     * Chooses the best location to stand within Calculator.DOG_MAX_SPEED of a while
     * moving along a line toward b
     */
    private Point getStepAlongPathTo(Point a, Point b) {
        return null;
    }

}

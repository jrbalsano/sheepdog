package sheepdog.g3;

import java.util.ArrayList;

import sheepdog.g3.Calculator.SIDE;
import sheepdog.sim.Point;

public class StraightLineBrain extends sheepdog.g3.DogBrain{
	
	private int MAX_DOG_MOVEMENT = 2; //20m/s * 0.1s
	private double DOG_SHEEP_MIN_DIST = .001; //TODO: tune parameter
	
	public StraightLineBrain(int id, boolean advanced, int nblacks) {
		super(id,advanced,nblacks);
	}
	
	// Returns the center of the gap in the fence
	public Point getGapCoordinates()
	{
		Point gap = new Point(50,50);
		return gap;
	}
	
	// Returns a new position of dog
	public Point getMove(Point[] dogs, Point[] whiteSheep, Point[] blackSheep)
	{
		//If dog on the left side of fence, move dog towards the gap
		if(Calculator.getSide(dogs[mId].x) == SIDE.WHITE_GOAL_SIDE)
		{
			double angle = Math.atan(dogs[mId].y/dogs[mId].x);
			double new_x = dogs[mId].x + Math.cos(angle) * MAX_DOG_MOVEMENT;
			double new_y = dogs[mId].y + Math.sin(angle) * MAX_DOG_MOVEMENT;
			
			if(new_x >50)
			{
				new_x = 50;
				new_y = 50;
			}
			
			Point newPosition = new Point(new_x, new_y);
			return newPosition;
		}
		
		//If dog already on right side
		//Find closest sheep
		//TODO: optimize code; redundancy for white and black sheep
		Point gap = getGapCoordinates();
		int closestWhiteSheepToGap = getClosestWhiteSheeptoGap(dogs[mId], whiteSheep);
		int closestBlackSheepToGap = getClosestBlackSheeptoGap(dogs[mId], blackSheep);
		
		double closestWhiteSheepDistanceToGap = Calculator.dist(gap, whiteSheep[closestWhiteSheepToGap]);
		double closestBlackSheepDistanceToGap = Calculator.dist(gap, blackSheep[closestBlackSheepToGap]);
		
		boolean whichSheep = true; //true: black, false:white
		int closestSheep = 0;
		if(closestBlackSheepDistanceToGap < closestWhiteSheepDistanceToGap)
		{
			closestSheep = closestBlackSheepToGap;
			whichSheep = true;
		}
		else {
			closestSheep = closestWhiteSheepToGap;
			whichSheep = false;
		}
		
		//black sheep closest to gap
		if (whichSheep) {
			double sheepAngle = Math.atan(blackSheep[closestSheep].y/blackSheep[closestSheep].x);
			double new_x, new_y;
			
			//Dog not behind sheep, move dog towards sheep
			if (Calculator.dist(gap, dogs[mId]) < closestBlackSheepDistanceToGap) {
				new_x = dogs[mId].x + Math.cos(sheepAngle) * MAX_DOG_MOVEMENT;
				new_y = dogs[mId].y + Math.sin(sheepAngle) * MAX_DOG_MOVEMENT;
				//TODO: optimize if dog crosses sheep
			}
			//Dog already behind sheep, push sheep closer to gap
			else {
				new_x = blackSheep[closestSheep].x + Math.cos(sheepAngle) * DOG_SHEEP_MIN_DIST;
				new_y = blackSheep[closestSheep].y + Math.sin(sheepAngle) * DOG_SHEEP_MIN_DIST;
			}
			Point newPoint = new Point(new_x,new_y);
			return newPoint;
		}
		//white sheep closest to gap. Redundant code.
		else {
			double sheepAngle = Math.atan(whiteSheep[closestSheep].y/whiteSheep[closestSheep].x);
			double new_x, new_y;
			
			//Dog not behind sheep, move dog 
			if (Calculator.dist(gap, dogs[mId]) < closestWhiteSheepDistanceToGap) {
				new_x = dogs[mId].x + Math.cos(sheepAngle) * MAX_DOG_MOVEMENT;
				new_y = dogs[mId].y + Math.sin(sheepAngle) * MAX_DOG_MOVEMENT;
				//TODO: optimize if dog crosses sheep
			}
			//Dog already behind sheep
			else {
				new_x = whiteSheep[closestSheep].x + Math.cos(sheepAngle) * DOG_SHEEP_MIN_DIST;
				new_y = whiteSheep[closestSheep].y + Math.sin(sheepAngle) * DOG_SHEEP_MIN_DIST;
			}
			Point newPoint = new Point(new_x,new_y);
			return newPoint;
			
		}
		
	}

	public int getClosestWhiteSheeptoGap(Point gap, Point[] whiteSheep)
	{
		double min_dist = Double.MAX_VALUE;
		int min_id=0;
		ArrayList<Integer> undeliveredIndices = Calculator.undeliveredWhiteSheep(whiteSheep);
		for(Integer index : undeliveredIndices)
		{
		    Point sheep = whiteSheep[index];
			double temp_dist = Calculator.dist(gap, sheep);
			if(temp_dist<min_dist)
			{
				min_dist = temp_dist;
				min_id=index;
			}
		}
		return min_id;
	}
	public int getClosestBlackSheeptoGap(Point gap, Point[] blackSheep)
	{
		double min_dist = Double.MAX_VALUE;
		int min_id=0;
		ArrayList<Integer> undeliveredIndices = Calculator.undeliveredBlackSheep(blackSheep);
		for(Integer index : undeliveredIndices)
		{
		    Point sheep = blackSheep[index];
			double temp_dist = Calculator.dist(gap, sheep);
			if(temp_dist<min_dist)
			{
				min_dist = temp_dist;
				min_id=index;
			}
		}
		return min_id;
	}
}

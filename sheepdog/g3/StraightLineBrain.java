package sheepdog.g3;

import java.util.ArrayList;

import sheepdog.g3.Calculator.SIDE;
import sheepdog.sim.Point;

public class StraightLineBrain extends sheepdog.g3.DogBrain{
	
	private double MAX_DOG_MOVEMENT = 1.95; //20m/s * 0.1s
	private double DOG_SHEEP_MIN_DIST = .001; //TODO: tune parameter
	int prevClosestSheep = -1;
	boolean prevWhichSheep = true;
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
		    Point gap = getGapCoordinates();
			double angle = Math.atan((gap.y - dogs[mId].y)/(gap.x - dogs[mId].x));
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
			prevClosestSheep = closestSheep;
			prevWhichSheep = whichSheep;
			closestSheep = closestBlackSheepToGap;
			whichSheep = true;
		}
		else {
			prevClosestSheep = closestSheep;
			prevWhichSheep = whichSheep;
			closestSheep = closestWhiteSheepToGap;
			whichSheep = false;
		}
		
		boolean changed =false;
		if(prevWhichSheep!=whichSheep && prevClosestSheep!=closestSheep)
			changed = true;
		
//		if(changed)
//		{
//			return new Point(50.0,50.0);
//		}
		
		System.out.println("Sheep Id: "+closestSheep+" Type: "+whichSheep);
		
		//black sheep closest to gap
		if (whichSheep) {
			double sheepAngleToGap = Math.atan((gap.y-blackSheep[closestSheep].y)/(gap.x-blackSheep[closestSheep].x));
			double new_x, new_y;
			System.out.println("Processing black sheep");
			
			//Dog not behind sheep, move dog towards sheep
			if (Calculator.dist(gap, dogs[mId]) < closestBlackSheepDistanceToGap) {
				System.out.println("move dog to sheep");
				new_x = dogs[mId].x + Math.cos(sheepAngleToGap) * MAX_DOG_MOVEMENT;
				new_y = dogs[mId].y + Math.sin(sheepAngleToGap) * MAX_DOG_MOVEMENT;
				//TODO: optimize if dog crosses sheep
			}
			else if(Calculator.dist(gap, dogs[mId]) > closestBlackSheepDistanceToGap && Calculator.dist(blackSheep[closestSheep], dogs[mId]) > 2)
//			if(Calculator.dist(blackSheep[closestSheep], dogs[mId]) > 2)
			{
				System.out.println("Inside new fix code");
				Point tempDogPoint = new Point();
				tempDogPoint.x = blackSheep[closestSheep].x + Math.cos(sheepAngleToGap) * DOG_SHEEP_MIN_DIST;
				tempDogPoint.y = blackSheep[closestSheep].y + Math.cos(sheepAngleToGap) * DOG_SHEEP_MIN_DIST;
				
				double dogMovementAngle = Math.atan((dogs[mId].y-tempDogPoint.y)/(dogs[mId].x-tempDogPoint.x));
				new_x = dogs[mId].x + Math.cos(dogMovementAngle) * MAX_DOG_MOVEMENT;
				new_y = dogs[mId].y + Math.sin(dogMovementAngle) * MAX_DOG_MOVEMENT;
			}
			//Dog already behind sheep, push sheep closer to gap
			else {
				System.out.println("inside else...maybe an error");
				new_x = blackSheep[closestSheep].x + Math.cos(sheepAngleToGap) * DOG_SHEEP_MIN_DIST;
				new_y = blackSheep[closestSheep].y + Math.sin(sheepAngleToGap) * DOG_SHEEP_MIN_DIST;
			}
			Point newPoint = new Point(new_x,new_y);
			return newPoint;
		}
		//white sheep closest to gap. Redundant code.
		else {
			double sheepAngleToGap = Math.atan((gap.y-whiteSheep[closestSheep].y)/(gap.x-whiteSheep[closestSheep].x));
			double new_x, new_y;
			System.out.println("Processing white sheep");
			
			//Dog not behind sheep, move dog 
			if (Calculator.dist(gap, dogs[mId]) < closestWhiteSheepDistanceToGap) {
				System.out.println("move dog to sheep");
				new_x = dogs[mId].x + Math.cos(sheepAngleToGap) * MAX_DOG_MOVEMENT;
				new_y = dogs[mId].y + Math.sin(sheepAngleToGap) * MAX_DOG_MOVEMENT;
				//TODO: optimize if dog crosses sheep
			}
			else if(Calculator.dist(gap, dogs[mId]) > closestWhiteSheepDistanceToGap && Calculator.dist(whiteSheep[closestSheep], dogs[mId]) > 2)
//			if(Calculator.dist(whiteSheep[closestSheep], dogs[mId]) > 2)
			{
				System.out.println("Inside new fix code");
				Point tempDogPoint = new Point();
				tempDogPoint.x = whiteSheep[closestSheep].x + Math.cos(sheepAngleToGap) * DOG_SHEEP_MIN_DIST;
				tempDogPoint.y = whiteSheep[closestSheep].y + Math.cos(sheepAngleToGap) * DOG_SHEEP_MIN_DIST;
				
				double dogMovementAngle = Math.atan((dogs[mId].y-tempDogPoint.y)/(dogs[mId].x-tempDogPoint.x));
				new_x = dogs[mId].x + Math.cos(dogMovementAngle) * MAX_DOG_MOVEMENT;
				new_y = dogs[mId].y + Math.sin(dogMovementAngle) * MAX_DOG_MOVEMENT;
			}
			//Dog already behind sheep
			else {
				System.out.println("inside else...maybe an error");
				new_x = whiteSheep[closestSheep].x + Math.cos(sheepAngleToGap) * DOG_SHEEP_MIN_DIST;
				new_y = whiteSheep[closestSheep].y + Math.sin(sheepAngleToGap) * DOG_SHEEP_MIN_DIST;
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

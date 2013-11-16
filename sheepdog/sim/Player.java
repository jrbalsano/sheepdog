package sheepdog.sim;

public abstract class Player {
    public int id; // id of the dog, 1,2,3...ndog

    public Player() {}
    
    public abstract void init(int nblacks, boolean mode);
    
    // Return: the next position
    // my position: dogs[id-1]
    // White sheep are always first in the list
    public abstract Point move(Point[] dogs, // positions of dogs
                               Point[] sheeps); // positions of the sheeps

}

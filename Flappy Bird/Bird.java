import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bird here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bird extends Actor
{
    private static double GRAVITY = 1.5;
    private static double JUMP_BOOST = -10;
    private int WORLD_HEIGHT, WORLD_WIDTH;
    private double velocity = 0;
    private int counter = 10;
    public Bird (int WORLD_WIDTH, int WORLD_HEIGHT)
    {
        this.WORLD_HEIGHT = WORLD_HEIGHT;
        this.WORLD_WIDTH = WORLD_WIDTH;
    }
    /**
     * Act - do whatever the Bird wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public void act() 
    {
        if(getOneIntersectingObject(TopPipe.class) != null || getOneIntersectingObject(BotPipe.class) != null || getOneIntersectingObject(Ground.class) != null)
        {
            Greenfoot.playSound("sfx_hit.wav");
            displayGameOver();
        }
        setLocation(getX(), getY() + (int)velocity);
        velocity = velocity + GRAVITY;
        if(Greenfoot.isKeyDown("space") == true && counter >= 5) 
        {
            counter = 0;
            velocity = JUMP_BOOST;
            Greenfoot.playSound("sfx_wing.wav");
        }
        counter ++;
        if(velocity >= 5 && velocity < 10) setRotation(20);
        if(velocity >= 10) setRotation(30);
        if(velocity == 0) setRotation(0);
        if(velocity <= -5 && velocity > -10) setRotation(-20);
        if(velocity <= -10) setRotation(-30);
        if(getY() < -50) displayGameOver();
    }
    public void displayGameOver()
    {
        GameOver display = new GameOver();
        getWorld().addObject(display, WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
        Greenfoot.stop();
    }

}

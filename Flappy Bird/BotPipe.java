import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BotPipe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BotPipe extends Actor
{
    int speed;
    public BotPipe(int speed)
    {
       this.speed = speed;
    }
    /**
     * Act - do whatever the BotPipe wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        setLocation(getX() - speed, getY());
    }    
}

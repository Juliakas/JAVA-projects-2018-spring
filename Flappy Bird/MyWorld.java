import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    private static int WORLD_WIDTH = 600;
    private static int WORLD_HEIGHT = 400;
    private static int PIPE_DISTANCE = 100;         //Optimalu: 100 - 150
    private static int PIPE_RNG = 200;              //Optimalu: 0 - 200
    private static int PIPE_SPEED = 4;              //Optimalu: 3 - 9
    private static int PIPE_FREQUENCY = 50;         //Mažiau - dažniau. Optimalu: 25 - 75
    private int counter = 0;
    private int score = 0;
    private Bird bird;
    private int firstPipeCounter = (WORLD_WIDTH * 5 / 6) / PIPE_SPEED;

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(WORLD_WIDTH, WORLD_HEIGHT, 1, false);
        Greenfoot.setSpeed(45);
        setPaintOrder(GameOver.class, TopPipe.class, BotPipe.class, Bird.class, Ground.class);
        prepare();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    public void stopped()
    {
        Greenfoot.delay(80);
        removeObjects(getObjects(null));
        prepare();
        Greenfoot.start();
        counter = 0;
        score = 0;
    }
    public void started()
    {
        counter = 0;
        score = 0;
    }
    private void prepare()
    {
        bird = new Bird(WORLD_WIDTH, WORLD_HEIGHT);
        addObject(bird, WORLD_WIDTH / 6, WORLD_HEIGHT / 2);
        Ground ground = new Ground();
        addObject(ground,292,394);
    }
    
    public void act()
    {
        while(Greenfoot.isKeyDown("space") != true && counter == 0)
            if(Greenfoot.isKeyDown("space") == true) break;
        showText("Score: " + score, WORLD_WIDTH / 10, WORLD_HEIGHT / 10);
        if(counter % PIPE_FREQUENCY == 0)
        {
            int randomNum = WORLD_HEIGHT / 2 + (PIPE_RNG / 2 - Greenfoot.getRandomNumber(PIPE_RNG));
            TopPipe pipe1 = new TopPipe(PIPE_SPEED);
            BotPipe pipe2 = new BotPipe(PIPE_SPEED);
            addObject(pipe1, WORLD_WIDTH, randomNum - (PIPE_DISTANCE + 265) / 2);
            addObject(pipe2, WORLD_WIDTH, randomNum + (PIPE_DISTANCE + 265) / 2);
        }
        counter++;
        if(counter >= firstPipeCounter)
        {
            if(counter == firstPipeCounter || (counter - firstPipeCounter) % PIPE_FREQUENCY == 0)
            {
                score++;
                Greenfoot.playSound("sfx_point.wav");
            }
        }
    }
}

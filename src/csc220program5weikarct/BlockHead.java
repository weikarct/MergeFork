/*
 * This BlockHead class is from PA3
 * Cole Weikart CSC220 
 */
package csc220program5weikarct;


import animation.Animatable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author weikarct
 */
public class BlockHead implements Animatable{
     private int over, down, width, height;
     private Thread thread = null;
     private Iterable<Point> animationPath;
     private Iterator<Point> animationIterator;
    
    public BlockHead(int o, int d, int w, int h, Rectangle display) 
    {
        over = o;
        down = d;
        width = w;
        height = h;
        
        setDefaultPath(display);
    }
    public BlockHead()
    {
        
    }

    public void drawMe(Graphics g) 
    {
       g.setColor(Color.YELLOW);
            g.fillRect(over, down, width+15, height);
            g.setColor(Color.BLACK);
            g.fillRect(over+15, down +5, 5, 5);
            g.fillRect(over+25,down+5,5,5);
            g.drawArc(over+10,down+25,20,10,180,180);
        
    }

    private void setDefaultPath(Rectangle displayBounds)
    {
        Random rand = new Random();
        int numberOfPoints = 5;
        SimpleLinkedList path = new SimpleLinkedList<>();
        for(int t = 0; t < numberOfPoints; t++)
        {
            int x = rand.nextInt(displayBounds.width);
            int y = rand.nextInt(displayBounds.height);
            path.add(new Point(x,y));
        }
        setPath(path);
    }
    
    public void moveTo(Point p)
    {
        over = p.x;
        down = p.y;
    }
    
    
    @Override
    public void startAnimation() {
        this.thread = new Thread(new Runner());
        thread.start();
    }
    
    private class Runner implements Runnable
    {
        public Runner()
        {
            
        }
        public void run()
        {
            try
            {
            while(true)
            {
            if(!BlockHead.this.animationIterator.hasNext())
            {
                BlockHead.this.animationIterator = animationPath.iterator();
            }
            if(BlockHead.this.animationIterator.hasNext())
            {
                final Point p = (Point) BlockHead.this.animationIterator.next();
                java.awt.EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() 
                    {
                        BlockHead.this.moveTo(p);
                    }
                });
                thread.sleep(1000);
            }
            }
            }
            catch(Exception e)
            {
                
            }
        }
    }

    @Override
    public Iterator<Point> stopAnimation() 
    {
        this.thread.interrupt();
        this.thread = null;
        return this.animationIterator;
    }

    @Override
    public void setPath(Iterable<Point> animationPath) 
    {
        this.animationPath = animationPath;
        this.animationIterator = animationPath.iterator();
        if(this.animationIterator.hasNext())
        {
            moveTo((Point)this.animationIterator.next());
        }
    }

    @Override
    public Iterable<Point> getPath() 
    {
        return this.animationPath;
    }

    @Override
    public boolean contains(Point p) 
    {
        return new Rectangle(over, down, width, height).contains(p);
    }

    @Override
    public boolean isAnimated() 
    {
        return this.thread != null;
    }
    
}
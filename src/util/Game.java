package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{
	
	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	private Graphics h;
	
	
	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		
	}
	
	private void init(){
		display = new Display(title, width, height);
	}
	
	public void run(){
		
		init();
		// Verry popular game loop
		//https://www.youtube.com/watch?v=1gir2R7G9ws
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >=1)
			{
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
	                            
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println("FPS: "+ frames);
				frames = 0;
			}
		}
		stop();
		//tick();
		//render();
	}
	
	private void tick(){
		
	}
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Scren
		g.clearRect(0, 0, width, height);
		// Draws Here
		//Background
		Color sky = Color.decode("#66B2FF");
		
		
		//End Background
		
		//Mario Modle starts here
		g.setColor(Color.red);
		g.fillRect(300, 120, 85, 10);	//Base Hat
		g.fillRect(310, 110, 40, 50);	//top half of hat
		//Skin
		Color skin = Color.decode("#FFB266");
		g.setColor(skin);
		g.fillRect(300, 130, 60, 50);	//Head
		g.fillRect(360, 150, 25, 10);	//Nose base
		g.fillRect(360, 140, 15, 10);	//Nose bridge
		
		//Hair
		Color hair = Color.decode("#994C00");
		g.setColor(hair);
		g.fillRect(300,130, 20, 35);
		g.fillRect(340, 160, 35, 10);
		g.fillRect(290, 140, 10, 35);
		//Eye
		g.setColor(Color.black);
		g.fillRect(340, 130, 10, 20);
		
		//Mario's Body
		g.setColor(Color.red);
		g.fillRect(290, 180, 60, 60);
		g.fillRect(280, 190, 95, 40);
		//Hand
		g.setColor(Color.white);
		g.fillRect(270, 210, 20, 30);
		g.fillRect(360, 210, 20, 30);
		//Suspenders
		g.setColor(Color.blue);
		g.fillRect(290, 210, 70, 30);//Pants base
		g.fillRect(310, 180, 10, 50);//Suspender Left
		g.fillRect(340, 190, 10, 40);//Suspender Right
		g.fillRect(280, 230, 40, 20);//left Leg
		g.fillRect(330, 230, 40, 20);//right leg
		
		//Feet
		g.setColor(hair);
		g.fillRect(270, 250, 40, 20);
		g.fillRect(340, 250, 40, 20);
		//Mario modle ends here
		
		//End
		bs.show();
		g.dispose();
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try{
			thread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	
}

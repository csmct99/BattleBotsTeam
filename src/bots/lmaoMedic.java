package bots;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;

import roles.Role;
import roles.RoleType;
import roles.Roles;
import arena.BattleBotArena;
import arena.BotInfo;
import arena.Bullet;
import arena.HelperMethods;
import arena.Vector2;

public class lmaoMedic extends Bot {

	private final String TEAMNAME, NAME;
	
	private final double BOTSPEED;
	
	private final int UP, DOWN, LEFT, RIGHT, FIREUP, FIREDOWN, FIRELEFT, FIRERIGHT, WIDTH;
	private int move;
	
	private BotInfo me;
	private BotInfo[] liveBots, deadBots;
	
	private boolean shotOK, moving, movingx, first;

	private Bullet[] bullets;
	
	private Vector2 pos;
	
	private final Role BOTROLE;
	
	private Image current, up, down, left, right;
	
	
	
	public lmaoMedic() {
		//String
		TEAMNAME = "AyyLamo"; //Team name
		NAME = "MEDIC"; //Bot name
		
		//Int
		move = 0; //Current move
		
		moving = true;
		movingx = true;
		
		UP = BattleBotArena.UP;
		DOWN = BattleBotArena.DOWN;
		LEFT = BattleBotArena.LEFT;
		RIGHT = BattleBotArena.RIGHT;
		FIREUP = BattleBotArena.FIREUP;
		FIREDOWN = BattleBotArena.FIREDOWN;
		FIRELEFT = BattleBotArena.FIRELEFT;
		FIRERIGHT = BattleBotArena.FIRERIGHT;
		BOTSPEED = BattleBotArena.BOT_SPEED;
		
		BOTROLE = new Role(RoleType.TANK);
		
		
		WIDTH = RADIUS*2; //Width of the bot
		
		
		
	}

	public void newRound() {
		moving = true;
		movingx = true;
		first = true;
	}

	
	public int getMove(BotInfo me, boolean shotOK, BotInfo[] liveBots, BotInfo[] deadBots, Bullet[] bullets) {
		setPublics(me,shotOK,liveBots,deadBots,bullets);
		move = 0;
		
		
		if(moving){
			if(moveTo(600,400)){
				HelperMethods.say("Done moving");
				//moving = false;
			}
		}
		
//		BotInfo[] bots = CheckTeamAmmo();
		
		
		
		setImage();
		return move;
	}
	
	//************************************************************************************************************\\

	
	public BotInfo[] CheckTeamAmmo(){
		
		BotInfo botMedic = null; //Dead if null
		BotInfo botAttack = null;
		BotInfo botTank = null;
		
		for (BotInfo bot: liveBots){
			if (bot.getTeamName() == me.getTeamName()){
				if (bot.getRole() == RoleType.ATTACK){
					botAttack = bot;
				}else if (bot.getRole() == RoleType.TANK){
					botTank = bot;
				}else if (bot.getRole() == RoleType.MEDIC){
					botMedic = bot;
				}
			}
		}
		
		BotInfo[] botList = new BotInfo[2];
		
		//Gets ammo as percentage
		double medicAmmoPer = ((botMedic.getBulletsLeft()/Role.MEDIC_MAX_AMMO)*100);
		double attackAmmoPer = ((botAttack.getBulletsLeft()/Role.TANK_MAX_AMMO)*100);
		double tankAmmoPer = ((botTank.getBulletsLeft()/Role.ATTACK_MAX_AMMO)*100);
		
		System.out.println(botMedic.getTeamName() + "   " + botTank.getTeamName() + "   " + botAttack.getTeamName());
		
		return null;
	}
	
	private void setPublics(BotInfo me, boolean shotOK, BotInfo[] liveBots, BotInfo[] deadBots, Bullet[] bullets){
		this.me = me;
		this.shotOK = shotOK;
		this.liveBots = liveBots;						
		this.deadBots = deadBots;
		this.bullets = bullets;
		this.pos = new Vector2(me.getX(),me.getY());
		
	}
	
	public Role getRole() {
		
		return BOTROLE;
	}
	
	private double manDist(Vector2 x1, Vector2 x2){
		return (Math.abs(x1.x-x2.x) + Math.abs(x1.y-x2.y));
	}
	
	/**
	 * Starts moving to the given coord avoiding ONLY AVOIDS Bots, Graves and walls though
	 * @param x DOUBLE x pos
	 * @param y DOUBLE y pos
	 */
	private boolean moveTo(double x, double y){
		//In order for this to work in your code, Tyler you need to change pos.x to me.getX() and pos.y to me.getY()
		
		double Distance = 40;
		System.out.println(pos.x + ","+ pos.y);
		
		if(!valueWithin(x-(BOTSPEED/2), pos.x, x+(BOTSPEED/2)) && (movingx)){//If not at the x location
			switch(onSide("left",new Vector2(x,y))){
				case 3: //Left
					//Try to move left
					if(isPathClear(LEFT, Distance) && !willHitEdge(LEFT,Distance/4)){
						move = LEFT;
						return false;
					}else if(isPathClear(UP, Distance) && !willHitEdge(UP,Distance/4)){//Try to move up
						move = UP; // connor copy and pasted this code
						return false;
					}else if(isPathClear(DOWN, Distance) && !willHitEdge(DOWN,Distance/4)){//Try to move down
						move = DOWN;
						return false;
					}
					
					
				case 4: //Right
					//Try to move right
					if(isPathClear(RIGHT, Distance) && !willHitEdge(RIGHT,Distance)){//Try to move right
						move = RIGHT;
						return false;
					}else if(isPathClear(UP, Distance) && !willHitEdge(UP,Distance)){//Try to move up
						move = UP;
						return false;
					}else if(isPathClear(DOWN, Distance) && !willHitEdge(DOWN,Distance)){//Try to move down
						move = DOWN;
						return false;
					}
			
			}
		}else if(!valueWithin(y-(BOTSPEED/2), pos.y, y+(BOTSPEED/2))){//If not at the y location
			movingx = false;
			switch(onSide("up",new Vector2(x,y))){
				case 1: //Up
					
					if(isPathClear(UP, Distance) && !willHitEdge(UP,Distance)){//Try to move up
						move = UP;
						return false;
					}else if(isPathClear(LEFT, Distance) && !willHitEdge(LEFT,Distance)){//Try to move left
						move = LEFT;
						return false;
					}else if(isPathClear(RIGHT, Distance) && !willHitEdge(RIGHT,Distance)){//Try to move right
						move = RIGHT;
						return false;
					}
				
				case 2: //Down
					if(isPathClear(DOWN, Distance) && !willHitEdge(DOWN,Distance)){//Try to move down
						move = DOWN;
						return false;
					}else if(isPathClear(LEFT, Distance) && !willHitEdge(LEFT,Distance)){//Try to move left
						move = LEFT;
						return false;
					}else if(isPathClear(RIGHT, Distance) && !willHitEdge(RIGHT,Distance)){//Try to move right
						move = RIGHT;
						return false;
					}
				
			}//End of switch
		}else if(valueWithin(y-(BOTSPEED/2), pos.y, y+(BOTSPEED/2)) && !valueWithin(x-(BOTSPEED/2), pos.x, x+(BOTSPEED/2))){//There is probably a better way to do this
			movingx = true;
		}else{//At pos
			return true;
		}
	
		return false;//Error
		
	}//End of moveTo method
	
	/**
	 * Checks if there will be bots, bullets or graves in your path
	 * @return BOOLEAN If the path is clear
	 */
	private boolean isPathClear(int direction, double amount){

		for(BotInfo bot : liveBots){
			Vector2 botPos = new Vector2(bot.getX(),bot.getY());
			
			switch(direction){
			//1 up, 2 down, 3 left, 4 right
				case 1://up
					if(onSide("up",bot) == UP){//If the bot is above me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance
							if(valueWithin(botPos.x,pos.x,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.x,pos.x+WIDTH,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 2://down
					if(onSide("up",bot) == DOWN){//If the bot is below me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance
							if(valueWithin(botPos.x,pos.x,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.x,pos.x+WIDTH,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 3://left
					if(onSide("left",bot) == LEFT){//If the bot is left of me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance 
							if(valueWithin(botPos.y,pos.y,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.y,pos.y+WIDTH,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 4://right
					if(onSide("left",bot) == RIGHT){//If the bot is right of me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance 
							if(valueWithin(botPos.y,pos.y,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.y,pos.y+WIDTH,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
			}
		}
		
		for(BotInfo bot: deadBots){ //TODO TEST THIS
			Vector2 botPos = new Vector2(bot.getX(),bot.getY());
			
			switch(direction){
			//1 up, 2 down, 3 left, 4 right
				case 1://up
					if(onSide("up",bot) == UP){//If the bot is above me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance
							if(valueWithin(botPos.x,pos.x,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.x,pos.x+WIDTH,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 2://down
					if(onSide("up",bot) == DOWN){//If the bot is below me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance
							if(valueWithin(botPos.x,pos.x,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.x,pos.x+WIDTH,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 3://left
					if(onSide("left",bot) == LEFT){//If the bot is left of me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance 
							if(valueWithin(botPos.y,pos.y,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.y,pos.y+WIDTH,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 4://right
					if(onSide("left",bot) == RIGHT){//If the bot is right of me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance 
							if(valueWithin(botPos.y,pos.y,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.y,pos.y+WIDTH,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
			}
		}
	
	
		return true;//It is safe
	}

	private boolean isPathClear(int direction, double amount, Vector2 pos){
		
		for(BotInfo bot : liveBots){
			Vector2 botPos = new Vector2(bot.getX(),bot.getY());
			
			switch(direction){
			//1 up, 2 down, 3 left, 4 right
				case 1://up
					if(onSide("up",bot) == UP){//If the bot is above me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance
							if(valueWithin(botPos.x,pos.x,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.x,pos.x+WIDTH,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 2://down
					if(onSide("up",bot) == DOWN){//If the bot is below me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance
							if(valueWithin(botPos.x,pos.x,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.x,pos.x+WIDTH,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 3://left
					if(onSide("left",bot) == LEFT){//If the bot is left of me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance 
							if(valueWithin(botPos.y,pos.y,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.y,pos.y+WIDTH,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 4://right
					if(onSide("left",bot) == RIGHT){//If the bot is right of me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance 
							if(valueWithin(botPos.y,pos.y,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.y,pos.y+WIDTH,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
			}
		}
		
		for(BotInfo bot: deadBots){ //TODO TEST THIS
			Vector2 botPos = new Vector2(bot.getX(),bot.getY());
			
			switch(direction){
			//1 up, 2 down, 3 left, 4 right
				case 1://up
					if(onSide("up",bot) == UP){//If the bot is above me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance
							if(valueWithin(botPos.x,pos.x,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.x,pos.x+WIDTH,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 2://down
					if(onSide("up",bot) == DOWN){//If the bot is below me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance
							if(valueWithin(botPos.x,pos.x,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.x,pos.x+WIDTH,botPos.x+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 3://left
					if(onSide("left",bot) == LEFT){//If the bot is left of me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance 
							if(valueWithin(botPos.y,pos.y,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.y,pos.y+WIDTH,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
					
				case 4://right
					if(onSide("left",bot) == RIGHT){//If the bot is right of me
						if(distanceMid(pos,botPos) <= amount){//If in the check distance 
							if(valueWithin(botPos.y,pos.y,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}else if(valueWithin(botPos.y,pos.y+WIDTH,botPos.y+WIDTH)){//If the bot is in my path
								return false;
							}
						}
					}
					break;
			}
		}
	
	
		return true;//It is safe
	}
	
	/**
	 * Returns what side the bot is on
	 * @param updown STRING "up","left" defines if oyu should be looking up and down or left and right
	 * @param bot BOTINFO The bot in question
	 * @return INT which direction the bot is
	 */
	private int onSide(String updown, BotInfo bot){
		double botX = bot.getX();
		double botY = bot.getY();
		
		if(updown == "up"||updown == "down"){//We are checking for up and down
			if(pos.y+RADIUS >= botY+RADIUS){
				return UP;//The bot is above me
			}else if(pos.y+RADIUS < botY+RADIUS){
				return DOWN;
			}
			
		}else if(updown == "left"||updown == "right"){
			if(pos.x+RADIUS >= botX+RADIUS){
				return LEFT;//The bot is above me
			}else if(pos.x+RADIUS < botX+RADIUS){
				return RIGHT;
			}
		}
		
		return 0;//Returns 0 if the was an error
	}
	
	/**
	 * Returns what side the xy coord is on
	 * @param updown STRING
	 * @param xy VECTOR2
	 * @return INT
	 */
	private int onSide(String updown, Vector2 xy){
		double botX = xy.x;
		double botY = xy.y;
		
		if(updown == "up"||updown == "down"){//We are checking for up and down
			if(pos.y >= botY){
				return UP;//The bot is above me
			}else if(pos.y < botY){
				return DOWN;
			}
			
		}else if(updown == "left"||updown == "right"){
			if(pos.x >= botX){
				return LEFT;//The bot is above me
			}else if(pos.x < botX){
				return RIGHT;
			}
		}
		
		return 0;//Returns 0 if the was an error
	}
		
	/**
	 * Returns if you will hit the wall if you go in the direction for x amount of pixels
	 * @param direction INT 
	 * @param amount DOUBLE
	 * @return BOOLEAN Will I hit the wall?
	 */
	private boolean willHitEdge(int direction, double amount){
		switch(direction){
			case 1://up
				if(pos.y-amount < BattleBotArena.TOP_EDGE)
				{
					return true;
				}
				break;
				
			case 2://down
				if(pos.y+WIDTH+amount > BattleBotArena.BOTTOM_EDGE)
				{
					return true;
				}
				break;
				
			case 3://left
				if(pos.x-amount < BattleBotArena.LEFT_EDGE)
				{
					return true;
				}
				break;
				
			case 4://right
				if(pos.x+WIDTH+amount > BattleBotArena.RIGHT_EDGE)
				{
					return true;
				}
				break;
		}
		
		
		return false;
	}
	
	private double distanceMid(Vector2 pos1, Vector2 pos2){
		return (Math.sqrt(Math.pow(((pos1.x+RADIUS)-(pos2.x+RADIUS)),2) + Math.pow(((pos1.y+RADIUS)-(pos2.y+RADIUS)), 2)));//MMMMMAAAAAAAAAAAAAAAATTTTTHHHHHH
	}
	
	private boolean valueWithin(double lowest, double value, double highest){
		if(value >= lowest && value <= highest){
			return true;
		}
		return false;
	}
	
	public void draw(Graphics g, int x, int y) {
		g.drawImage(current, x, y, Bot.RADIUS*2, Bot.RADIUS*2, null); //Draw the bot
	}


	private void drawPoints(ArrayList<Vector2> nodes, int radius, Graphics g){
		for(Vector2 node : nodes){
			if(node.color != null){
				g.setColor(node.color);
			}
			g.fillOval((int)node.x, (int)node.y, radius, radius);
		}
		nodes.clear();
	}
	
	public String getName() {
		
		return NAME;
	}

	
	public String getTeamName() {
		
		return TEAMNAME;
	}

	
	public String outgoingMessage() {
		
		return null;
	}

	
	public void incomingMessage(int botNum, String msg) {
		

	}
	
	public void setImage(){
		if(move == UP || move == FIREUP){
			current = up;
		}else if(move == LEFT || move == FIRELEFT){
			current = left;		
		}else if(move == DOWN || move == FIREDOWN){
			current = down;	
		}else if(move == RIGHT || move == FIREDOWN){
			current = right;		
		}
	}

	
	
	public String[] imageNames() {
		String[] paths = {"robotUp.png", "robotDown.png", "robotRight.png", "robotLeft.png"};
		return paths;
	}

	
	public void loadedImages(Image[] images) {
		if (images != null)
		{
			up = images[0];
			down = images[1];
			right = images[2];
			left = images[3];
			current = up;
		}
	}

}

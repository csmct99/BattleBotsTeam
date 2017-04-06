package bots;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

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
	
	private final int UP, DOWN, LEFT, RIGHT, FIREUP, FIREDOWN, FIRELEFT, FIRERIGHT, WIDTH;
	private int move;
	
	private BotInfo me;
	private BotInfo[] liveBots, deadBots;
	
	private boolean shotOK;

	private Bullet[] bullets;
	
	private Vector2 pos;
	
	private Image current, up, down, left, right;
	
	public lmaoMedic() {
		//String
		TEAMNAME = "AyyLamo"; //Team name
		NAME = "MEDIC"; //Bot name
		
		//Int
		move = 0; //Current move
		
		UP = BattleBotArena.UP;
		DOWN = BattleBotArena.DOWN;
		LEFT = BattleBotArena.LEFT;
		RIGHT = BattleBotArena.RIGHT;
		FIREUP = BattleBotArena.FIREUP;
		FIREDOWN = BattleBotArena.FIREDOWN;
		FIRELEFT = BattleBotArena.FIRELEFT;
		FIRERIGHT = BattleBotArena.FIRERIGHT;
		WIDTH = RADIUS*2; //Width of the bot
		
		
		
		
	}

	public void newRound() {
		
	}

	
	public int getMove(BotInfo me, boolean shotOK, BotInfo[] liveBots, BotInfo[] deadBots, Bullet[] bullets) {
		setPublics(me,shotOK,liveBots,deadBots,bullets);
		setImage();
		return move;
	}
	
	//************************************************************************************************************\\
	
	private void setPublics(BotInfo me, boolean shotOK, BotInfo[] liveBots, BotInfo[] deadBots, Bullet[] bullets){
		this.me = me;
		this.shotOK = shotOK;
		this.liveBots = liveBots;
		this.deadBots = deadBots;
		this.bullets = bullets;
		this.pos = new Vector2(me.getX(),me.getY());
		
	}
	
	public Role getRole() {
		
		return new Role(RoleType.MEDIC);
	}
	
	
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
		if (current != null)
			g.drawImage(current, x, y, Bot.RADIUS*2, Bot.RADIUS*2, null);
		else
		{
			g.setColor(Color.lightGray);
			g.fillOval(x, y, Bot.RADIUS*2, Bot.RADIUS*2);
		}
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

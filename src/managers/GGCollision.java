package managers;

import graphics.GGSprite;

/**
* Collision, static class with methods used to test collision between two sprites. 
* 
* 
* @author Gilvanei Gregorio
* @version 1.0
*/
public class GGCollision {

	/** 
	 * circular, test collision between two sprite using circles.
	 * 
	 * @param GGSprite s1
	 * @param GGSprite s2
	 * 
	 * @return boolean, result of test
	 */
	public static boolean circular(GGSprite s1, GGSprite s2){
		if(Math.sqrt(Math.pow(s1.position.x-s2.position.x, 2) + Math.pow(s1.position.y-s2.position.y, 2)) <= (s1.width/2 + s2.width/2))
			return true;
		
		return false;
	}
	
	/** 
	 * circular, test collision between two sprite using circles.
	 * 
	 * @param int x1
	 * @param int y1
	 * @param int r1, radius
	 * @param int x2
	 * @param int y2
	 * @param int r2, radius
	 * 
	 * @return boolean, result of test
	 */
	public static boolean circular(int x1, int y1, int r1, int x2, int y2, int r2){
		if(Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2)) <= (r1 + r2))
			return true;
		
		return false;
	}
	
	/** 
	 * boundingBox, test collision between two sprite using yours boundingBox.
	 * 
	 * @param GGSprite s1
	 * @param GGSprite s2
	 * 
	 * @return boolean, result of test
	 */
	public static boolean boundingBox(GGSprite s1, GGSprite s2){
         if(s1.position.x > s2.position.x+s2.width)
        	 return false;
         if(s1.position.x+s1.width < s2.position.x)
        	 return false;
         if(s1.position.y+s1.height < s2.position.y)
 			return false;
 		if(s1.position.y > s2.position.y + s2.height)
 			return false;

 		return true;
	}
	
	/** 
	 * boundingBox, test collision between two sprite using yours boundingBox.
	 * 
	 * @param int x1
	 * @param int y1
	 * @param int width1
	 * @param int height1
	 * @param int x2
	 * @param int y2
	 * @param int width2
	 * @param int height2
	 * 
	 * @return boolean, result of test
	 */
	public static boolean boundingBox(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2){
        if(x1 > x2+width2)
        	return false;
        if(x1+width1 < x2)
        	return false;
        if(y1+height1 < y2)
			return false;
		if(y1 > y2 + height2)
			return false;

		return true;
	}
	
	public static boolean tile(GGSprite sprite, GGTileMap map, int layer){
		int beginX = (int)(sprite.position.x / map.tileSizeX);
		int endX = (int)((sprite.position.x + sprite.width) / map.tileSizeX);
		
		int beginY = (int)(sprite.position.y / map.tileSizeY);
		int endY = (int)((sprite.position.y + sprite.height) / map.tileSizeY);
		
		try{
			for(int y=beginY; y<=endY; y++){
				for(int x=beginX; x<=endX; x++){
					if(map.layers.get(layer)[y][x] > 0){
						return true;
					}
				}
			}
		}catch(Exception e){
			return true;
		}
		
		return false;
	}
	
	public static boolean tile(int posX, int posY, int width, int height, GGTileMap map, int layer){
		int beginX = (int)(posX / map.tileSizeX);
		int endX = (int)((posX + width) / map.tileSizeX);
		
		int beginY = (int)(posY / map.tileSizeY);
		int endY = (int)((posY + height) / map.tileSizeY);
		
		try{
			for(int y=beginY; y<=endY; y++){
				for(int x=beginX; x<=endX; x++){
					if(map.layers.get(layer)[y][x] > 0){
						return true;
					}
				}
			}
		}catch(Exception e){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 0 - don't Collide
	 * 1 - left
	 * 2 - Right
	 * 3 - top
	 * 4 - bottom
	 * */
	public static int mapLimits(int posX, int posY, int width, int height, 
			int mapX, int mapY, int mapWidth, int mapHeight){
		if(posX < mapX)
			return 1;
		
		if(posX+width > mapX+mapWidth)
			return 2;
		
		if(posY < mapY)
			return 3;
		
		if(posY+height > mapY + mapHeight)
			return 4;
		
		return 0;
	}
	
	
}

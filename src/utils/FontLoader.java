package utils;

import java.awt.Font;
import java.io.InputStream;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

/**
* FontLoader, loads fonts.
* 
* @author Gustavo Gregorio
* 
* @version 1.0
*/
@SuppressWarnings("deprecation")
public class FontLoader {
    
    /**
     * loadFromFile, load font of your path file and return a TrueTypeFont.
     * 
     * @param String filePath
     * @param float size
     * 
     * @return TrueTypeFont
     */
    public static TrueTypeFont fromFile(String filePath, float size){
    	InputStream inputStream = ResourceLoader.getResourceAsStream(filePath);
    	Font awtFont = null;
    	
		try {
			awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			 /* set font size */
	    	awtFont = awtFont.deriveFont(size);
	    	
	    	return new TrueTypeFont(awtFont, true);
		}catch(Exception e){
            System.out.println("FontManager - loadFromFile - Error load font "+filePath);
            
			return null;
		}
    }
    
    /**
     * loadFromJava, load font from java using your fontName and return a TrueTypeFont.
     * 
     * @param String filePath
     * @param float size
     * 
     * @return TrueTypeFont
     */
    public static TrueTypeFont fromJava(String fontName, int size){
    	Font awtFont = new Font(fontName, Font.BOLD, size);
    	
    	return new TrueTypeFont(awtFont, true);
    }
}

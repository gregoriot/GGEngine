package openGL;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
* GTexture, Graphic texture.
* 
* @author Gilvanei Gregorio
* @version 1.0
*/
public class GGTexture {

    /** Texture openGL ID */
    public int id; 

    /** Image loaded width in pixels */
    public int width;
    /** Image loaded height in pixels */
    public int height;
    
    /** Proportion in axis X of image, image need not be square*/
    public float proportionX;
    /** Proportion in axis Y of image, image need not be square */
    public float proportionY;
    
    /**
     * load, load a texture of your path file and put in GTexture send to reference.
     * 
     * @param GGTexture dest, reference of GTexture
     * @param String extension, extension of file
     * @param String pathFile
     * 
     * @return boolean, result of operation
     */
    public static GGTexture load(String extension, String pathFile){
        InputStream inputStream = TextureLoader.class.getClass().getResourceAsStream(pathFile);
        
        try{
            Texture tempTexture = TextureLoader.getTexture(extension, inputStream);
            GGTexture texture = new GGTexture();
            
            texture.id = tempTexture.getTextureID();
            texture.width = tempTexture.getImageWidth();
            texture.height = tempTexture.getImageHeight();
            
            texture.proportionX = tempTexture.getWidth();
            texture.proportionY = tempTexture.getHeight();
            
            return texture;
        }catch(IOException ex){
            System.out.println("TextureManager - load - Error load texture "+pathFile);
            
            return null;
        }
    }
}
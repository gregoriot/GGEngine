package managers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import openGL.GGTexture;


public class GGTiledTXT {

    /** 
    * Constructor
    * <p>Initiate the attributes of class.</p> 
    * @param String filePath
    * @return TileMap
    */
    public static GGTileMap load(GGTexture tileSet, InputStream is) {
        GGTileMap tilemap = new GGTileMap(tileSet);
        
        try {
            /*Reading tileMap file. If exception a error message are write*/
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String line;
            while((line = br.readLine()) != null){
                /*Reading header of file*/
                if(line.equals("[header]"))
                    LoaderHeader(br, tilemap);
                
                /*Reading layers of file*/
                if(line.equals("[layer]")){
                    Integer[][] tempLayer = new Integer[tilemap.mapQtdY][tilemap.mapQtdX];
                    LoaderLayer(br, tempLayer);
                    tilemap.layers.add(tempLayer);
                }
            }
            br.close();
            
            tilemap.width = tilemap.mapQtdX * tilemap.tileSizeX;
            tilemap.height = tilemap.mapQtdY * tilemap.tileSizeY;
            tilemap.tileSetQtdX = tilemap.texture.width / tilemap.tileSizeX;
            tilemap.tileSetQtdY = tilemap.texture.height / tilemap.tileSizeY;
            tilemap.initBuffers();
            return tilemap;
        } catch (Exception e){
            System.out.println("Error load tiled csv, invalid file");
            return null;
        }
    }
    
    /** 
    * Loader Header
    * <p>Load the header of file of tileMap, and
    * assigns to variables.</p> 
    * @param BufferedReader bfr
    */
    private static void LoaderHeader(BufferedReader bufferedReader, GGTileMap tileMap) throws Exception{
        String line;
        String[] subLine;
        
        line = bufferedReader.readLine();
        subLine = line.split("=");
        tileMap.mapQtdX = Integer.parseInt(subLine[1]);
        
        line = bufferedReader.readLine();
        subLine = line.split("=");
        tileMap.mapQtdY = Integer.parseInt(subLine[1]);
        
        line = bufferedReader.readLine();
        subLine = line.split("=");
        tileMap.tileSizeX = Integer.parseInt(subLine[1]);
        
        line = bufferedReader.readLine();
        subLine = line.split("=");
        tileMap.tileSizeY = Integer.parseInt(subLine[1]);
    }
    
    /** 
    * Loader Layer
    * <p>Load the body of file of tileMap, and
    * assigns to variables.</p> 
    * @param BufferedReader bufferedReader
    */
    private static void LoaderLayer(BufferedReader bufferedReader, Integer[][] layer) throws Exception{
        String line;
        String[] subLine;
        
        /*Discard empty lines*/
        bufferedReader.readLine();
        bufferedReader.readLine();
        
        /*Read all lines and parse, while not over the values*/
        for(int i=0;((line =  bufferedReader.readLine()).contains(","));i++){
            subLine = line.split(",");
            for(int j=0; j<subLine.length; j++){
                /*In java all objects are reference, 
                 *so, this l is a object layer of 
                 *the main arrayList of the tileMap*/
                int value = Integer.parseInt(subLine[j]);
                layer[i][j] = value>0 ? value-1 : 0;
            }
        }
    }
    
}

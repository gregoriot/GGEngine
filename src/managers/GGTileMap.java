package managers;

import graphics.GGSprite;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.LinkedList;

import openGL.GGTexture;
import openGL.GGRenderGL;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.Color;

import utils.PVector;

public class GGTileMap extends GGSprite {
    
    public LinkedList<Integer[][]> layers;
    public int tileSetQtdX;
    public int tileSetQtdY;
    public int tileSizeX;
    public int tileSizeY;
    public int mapQtdX;
    public int mapQtdY;
    public int mapWidth;
    public int mapHeight;
    
    public GGTexture texture;
    
    private LinkedList<Integer> texCoordsBuffersId;
    private int verticesBufferId;
    private int indicesBufferRectId;
    private int indicesQtd;
    
    public GGTileMap(GGTexture tileSet) {
        super(0, 0, 0, 0);
        this.texture = tileSet;
        
        layers = new LinkedList<Integer[][]>();
        texCoordsBuffersId = new LinkedList<Integer>();
    }
    
	@Override
	protected void solid() {
		solid = false;
	}
    
    public void initBuffers() {
        int tilesQts = mapQtdX*mapQtdY;
        indicesQtd = tilesQts*6;
        
        int[] indices = new int[indicesQtd];
        float[] vertices = new float[tilesQts*4*3];
        float[] texCoords = new float[tilesQts*4*2];
        
        for(int i=0; i<mapQtdY; i++){
            for(int j=0; j<mapQtdX; j++){
                int firstIndice = (i*mapQtdX+j)*4;
                int posIndices = (i*mapQtdX+j)*6;
                int posVertices = (i*mapQtdX+j)*12;
                
                //First triangle
                indices[posIndices] = firstIndice;
                indices[posIndices+1] = firstIndice+1;
                indices[posIndices+2] = firstIndice+2;
                //First second
                indices[posIndices+3] = firstIndice;
                indices[posIndices+4] = firstIndice+2;
                indices[posIndices+5] = firstIndice+3;

                //First vertex
                vertices[posVertices] = j*tileSizeX;
                vertices[posVertices+1] = i*tileSizeY+tileSizeY;
                vertices[posVertices+2] = 0;
                //Second vertex
                vertices[posVertices+3] = j*tileSizeX+tileSizeX;
                vertices[posVertices+4] = i*tileSizeY+tileSizeY;
                vertices[posVertices+5] = 0;
                //Third vertex
                vertices[posVertices+6] = j*tileSizeX+tileSizeX;
                vertices[posVertices+7] = i*tileSizeY;
                vertices[posVertices+8] = 0;
                //Fourth vertex
                vertices[posVertices+9] = j*tileSizeX;
                vertices[posVertices+10] = i*tileSizeY;
                vertices[posVertices+11] = 0;
            }
        }
        
        float propX = texture.proportionX / tileSetQtdX;
        float propY = texture.proportionY / tileSetQtdY;
        for (Iterator<Integer[][]> iterator = layers.iterator(); iterator.hasNext();) {
            Integer[][] layer = iterator.next();
            for(int i=0; i<mapQtdY; i++){
                for(int j=0; j<mapQtdX; j++){
                    int posVertices = (i*mapQtdX+j)*8;
                    int x = layer[i][j] % tileSetQtdX;
                    int y = layer[i][j] / tileSetQtdX;
                    
                    //First vertex
                    texCoords[posVertices] = x*propX;
                    texCoords[posVertices+1] = y*propY+propY;
                    //Second vertex
                    texCoords[posVertices+2] = x*propX+propX;
                    texCoords[posVertices+3] = y*propY+propY;
                    //Third vertex
                    texCoords[posVertices+4] = x*propX+propX;
                    texCoords[posVertices+5] = y*propY;
                    //Fourth vertex
                    texCoords[posVertices+6] = x*propX;
                    texCoords[posVertices+7] = y*propY;
                }
            }
            
            FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
            texCoordsBuffer.put(texCoords);
            texCoordsBuffer.flip();
            
            texCoordsBuffersId.add(GL15.glGenBuffers());
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texCoordsBuffersId.getLast());
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoordsBuffer, GL15.GL_STATIC_DRAW);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
            
        }
        

        //Creating vbo of vertices
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();
        
        verticesBufferId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesBufferId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        //Creating vbo of indices
        IntBuffer indicesBufferRect = BufferUtils.createIntBuffer(indices.length);
        indicesBufferRect.put(indices);
        indicesBufferRect.flip();
        
        indicesBufferRectId = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferRectId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferRect, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void update(int difTime) {

    }
    
    @Override
    public void render() {
        GGRenderGL.pushMatrix();
        if(angleRadian == 0){
            GGRenderGL.translate(position.x, position.y);
            
            for (Iterator<Integer> iterator = texCoordsBuffersId.iterator(); iterator.hasNext();) {
                GGRenderGL.drawWithTexture(GL11.GL_TRIANGLES, texture, Color.white,
                        verticesBufferId, iterator.next(),
                        indicesBufferRectId, indicesQtd);
            }
        }else{
            GGRenderGL.translate(position.x+width/2f, position.y+height/2f);
            GGRenderGL.rotate(angleRadian);
            GGRenderGL.translate(-width/2f, -height/2f);
            
            for (Iterator<Integer> iterator = texCoordsBuffersId.iterator(); iterator.hasNext();) {
                GGRenderGL.drawWithTexture(GL11.GL_TRIANGLES, texture, Color.white,
                        verticesBufferId, iterator.next(),
                        indicesBufferRectId, indicesQtd);
            }
        }
        GGRenderGL.popMatrix();
    }
    
    public PVector toTileCoordenation(GGSprite sprite){
    	return new PVector(sprite.position.x/tileSizeX, sprite.position.y/tileSizeY);
    }
}
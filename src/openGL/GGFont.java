package openGL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import openGL.GGRenderGL;

public class GGFont {
    
    public static void render(GGTexture font, String text, float alpha, float posX, float posY, float size){
        byte[] characters = text.getBytes();
        
        float texProp = font.proportionX/16f;
        float fontSize = font.width/16f*size;
        
        int[] indices = new int[characters.length*3*2];
        float[] vertices = new float[characters.length*4*3];
        float[] texCoords = new float[characters.length*4*2];
        
        for(int i=0; i<characters.length; i++){
            float charX = i*fontSize;
            
            int firstIndice = i*4;
            int posIndices = i*6;
            //First triangle
            indices[posIndices] = firstIndice;
            indices[posIndices+1] = firstIndice+1;
            indices[posIndices+2] = firstIndice+2;
            //First second
            indices[posIndices+3] = firstIndice;
            indices[posIndices+4] = firstIndice+2;
            indices[posIndices+5] = firstIndice+3;

            int posVertices = i*12;
            //First vertex
            vertices[posVertices] = posX + charX;
            vertices[posVertices+1] = posY + fontSize;
            vertices[posVertices+2] = 0;
            //Second vertex
            vertices[posVertices+3] = posX + charX + fontSize;
            vertices[posVertices+4] = posY + fontSize;
            vertices[posVertices+5] = 0;
            //Third vertex
            vertices[posVertices+6] = posX + charX + fontSize;
            vertices[posVertices+7] = posY;
            vertices[posVertices+8] = 0;
            //Fourth vertex
            vertices[posVertices+9] = posX + charX;
            vertices[posVertices+10] = posY;
            vertices[posVertices+11] = 0;
            
            int posTexCoords = i*8;
            float texX = (characters[i]%16)*texProp;
            float texY = (characters[i]/16)*texProp;
            //First vertex
            texCoords[posTexCoords] = texX;
            texCoords[posTexCoords+1] = texY+texProp;
            //Second vertex
            texCoords[posTexCoords+2] = texX+texProp;
            texCoords[posTexCoords+3] = texY+texProp;
            //Third vertex
            texCoords[posTexCoords+4] = texX+texProp;
            texCoords[posTexCoords+5] = texY;
            //Fourth vertex
            texCoords[posTexCoords+6] = texX;
            texCoords[posTexCoords+7] = texY;
        }
        
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
        
        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();
        
        FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
        texCoordsBuffer.put(texCoords);
        texCoordsBuffer.flip();
        
        GGRenderGL.drawWithTexture(GL11.GL_TRIANGLES, font, alpha, posX, posY, verticesBuffer, texCoordsBuffer, indicesBuffer);
    }
    
}

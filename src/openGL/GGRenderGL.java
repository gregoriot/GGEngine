package openGL;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;

import openGL.shader.SimpleExempleShaderColor;
import openGL.shader.SimpleExempleShaderTexture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;

import utils.GGGamePanel;

/**
* RenderGL, class with static functions. openGL calls.
* 
* @author Gilvanei Gregorio
* @version 1.0
*/
public class GGRenderGL {
    
    private static LinkedList<Matrix4f> matrices;
    
	private static SimpleExempleShaderTexture shaderSimpleTexure;
	private static SimpleExempleShaderColor shaderSimpleColor;
	
	private static Matrix4f matrixModelView;
	private static Matrix4f matrixProjection;
	private static Matrix4f matrixModelViewProjection;
	
	private static FloatBuffer matrixBuffer;
	
	private static FloatBuffer verticesBuffer;
	private static FloatBuffer texCoordsBuffer;
	private static IntBuffer indicesBufferRect;
	private static IntBuffer indicesBufferRectWired;
	
	public static int verticesBufferId;
	public static int texCoordsBufferId;
	public static int indicesBufferRectId;
	public static int indicesBufferRectWiredId;
	
	/** 
	 * initOpenGL, init display and init funcitions that need game.
	 * 
	 * @param int width, width of viewport
	 * @param int height, height of viewport
	 */
	public static void initOpenGL(){
	    GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	    
        /* Enable alpha blending */
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        matrices = new LinkedList<Matrix4f>();
        
        initMatrices();
      	initShaders();
        initBuffers();
	}
	
	private static void initShaders(){
		shaderSimpleTexure = new SimpleExempleShaderTexture();
		shaderSimpleColor = new SimpleExempleShaderColor();
	}
	
	public static void initMatrices(){
	    float widthDiv2 = GGGamePanel.instance.width/2f;
        float heightDiv2 = GGGamePanel.instance.height/2f;
        
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(-widthDiv2, widthDiv2, heightDiv2, -heightDiv2, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    
		matrixBuffer = BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, matrixBuffer);
		
		matrixProjection = new Matrix4f();
		matrixProjection.load(matrixBuffer);
		
		matrixModelView = new Matrix4f();
		matrixModelViewProjection = new Matrix4f();
	}
	
	private static void initBuffers(){		
		float[] vertices = {
			 0.0f,  1.0f, 0,
			 1.0f,  1.0f, 0,
			 1.0f,  0.0f, 0,
			 0.0f,  0.0f, 0
        };
		
		verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();
		
		verticesBufferId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesBufferId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
		float[] texCoords = {
    		 0.0f,  1.0f,
    		 1.0f,  1.0f,
    		 1.0f,  0.0f,
    		 0.0f,  0.0f
        };
		
		texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
		texCoordsBuffer.put(texCoords);
		texCoordsBuffer.flip();
		
		texCoordsBufferId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texCoordsBufferId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, texCoordsBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		
		int indicesQuad[] = {
			0,1,2,
	  		0,2,3
	  	};
		
		indicesBufferRect = BufferUtils.createIntBuffer(indicesQuad.length);
		indicesBufferRect.put(indicesQuad);
		indicesBufferRect.flip();
		
		indicesBufferRectId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferRectId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferRect, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		
		int indicesQuadWired[] = {
			0,1,
			1,2,
	  		2,3,
	  		3,0
	  	};
		
		indicesBufferRectWired = BufferUtils.createIntBuffer(indicesQuadWired.length);
		indicesBufferRectWired.put(indicesQuadWired);
		indicesBufferRectWired.flip();
		
		indicesBufferRectWiredId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferRectWiredId);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferRectWired, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public static void loadIdentity(){
		matrixModelView.setIdentity();
	}
	
	public static void scale(float x, float y){
		matrixModelView.scale(new Vector3f(x, y, 1));
	}
	
	public static void translate(float x, float y){
		matrixModelView.translate(new Vector3f(x, y, 0));
	}
	
	public static void rotate(float angle){
		matrixModelView.rotate(angle, new Vector3f(0, 0, 1));
	}
	
	public static void pushMatrix(){
	    matrices.push(matrixModelView);
	    matrixModelView = new Matrix4f( matrices.getLast());
	}
	
	public static void popMatrix(){
        matrixModelView.load(matrices.pop());
    }
	
	public static void drawRectWithColor(Color color, float posX, float posY, float width, float height){
		drawRectWithColor(color, posX, posY, width, height, 0);
	}
	
	public static void drawRectWithColor(Color color, float posX, float posY, float width, float height, float angleRadian){
        Matrix4f custom = new Matrix4f(matrixModelView);
        if(angleRadian == 0){
            custom.translate(new Vector3f(posX, posY, 0f));
        }else{
            custom.translate(new Vector3f(posX+width/2f, posY+height/2f, 0f));
            custom.rotate(angleRadian, new Vector3f(0f, 0f ,1f));
            custom.translate(new Vector3f(-width/2f, -height/2f, 0));
        }
        custom.scale(new Vector3f(width, height, 1f));
        
        Matrix4f.mul(matrixProjection, custom, matrixModelViewProjection);
        matrixBuffer.position(0);
        matrixModelViewProjection.store(matrixBuffer);
        matrixBuffer.position(0);
        
        shaderSimpleColor.render(GL11.GL_TRIANGLES, color, verticesBufferId, indicesBufferRectId, 6, matrixBuffer);
    }
	
	public static void drawRectWithColorBorder(Color color, float posX, float posY, float width, float height){
	    drawRectWithColorBorder(color, posX, posY, width, height, 0);
	}
	
	public static void drawRectWithColorBorder(Color color, float posX, float posY, float width, float height, float angleRadian){
		Matrix4f custom = new Matrix4f(matrixModelView);
		if(angleRadian == 0){
            custom.translate(new Vector3f(posX, posY, 0f));
        }else{
            custom.translate(new Vector3f(posX+width/2f, posY+height/2f, 0f));
            custom.rotate(angleRadian, new Vector3f(0f, 0f ,1f));
            custom.translate(new Vector3f(-width/2f, -height/2f, 0));
        }
		custom.scale(new Vector3f(width, height, 1f));
		
		Matrix4f.mul(matrixProjection, custom, matrixModelViewProjection);
		matrixBuffer.position(0);
		matrixModelViewProjection.store(matrixBuffer);
		matrixBuffer.position(0);
		
		shaderSimpleColor.render(GL11.GL_LINES, color, verticesBufferId, indicesBufferRectWiredId, 8, matrixBuffer);
	}
	
	public static void drawRectWithTexture(GGTexture texture, Color color, float posX, float posY, float width, float height){
	    drawRectWithTexture(texture, color, posX, posY, width, height, 0, 0, texture.proportionX, texture.proportionY, 0);
	}
	
	public static void drawRectWithTexture(GGTexture texture, Color color, float posX, float posY, float width, float height, float angleRadian){
		drawRectWithTexture(texture, color, posX, posY, width, height, 0, 0, texture.proportionX, texture.proportionY, angleRadian);
	}
	
	public static void drawRectWithTexture(GGTexture texture, Color color, float posX, float posY, float posW, float posH, float texX, float texY, float texW, float texH){
	    drawRectWithTexture(texture, color, posX, posY, posW, posH, texX, texY, texW, texH, 0);
	}
	
	public static void drawRectWithTexture(GGTexture texture, Color color, float posX, float posY, float posW, float posH, float texX, float texY, float texW, float texH, float angleRadian){
		float width2 = posW/2f;
		float height2 = posH/2f;
	    
	    float[] vertices = {
			-width2,  height2, 0,
			 width2,  height2, 0,
			 width2, -height2, 0,
			-width2, -height2, 0
        };
		verticesBuffer.clear();
		verticesBuffer.put(vertices);
		verticesBuffer.flip();
		
		float[] texCoords = {
			texX,       texY+texH,
	    	texX+texW,  texY+texH,
			texX+texW,  texY,
			texX,       texY
	    };
		texCoordsBuffer.clear();
		texCoordsBuffer.put(texCoords);
		texCoordsBuffer.flip();
		
		Matrix4f custom = new Matrix4f(matrixModelView);
		custom.translate(new Vector3f(posX+width2, posY+height2, 0f));
        custom.rotate(angleRadian, new Vector3f(0f, 0f ,1f));
		
		Matrix4f.mul(matrixProjection, custom, matrixModelViewProjection);
		matrixBuffer.position(0);
		matrixModelViewProjection.store(matrixBuffer);
		matrixBuffer.position(0);
		
		shaderSimpleTexure.render(GL11.GL_TRIANGLES, texture.id, color, verticesBuffer, texCoordsBuffer, indicesBufferRect, matrixBuffer);
	}
	
	public static void drawWithTexture(int typeObj, GGTexture texture, Color color, int verticesId, int texCoordsId, int indicesId, int qtdIndices){
        drawWithTexture(typeObj, texture, color, 0, 0, verticesId, texCoordsId, indicesId, qtdIndices);
    }
	
	public static void drawWithTexture(int typeObj, GGTexture texture, Color color, float posX, float posY, int verticesId, int texCoordsId, int indicesId, int qtdIndices){
        Matrix4f custom = new Matrix4f(matrixModelView);
        custom.translate(new Vector3f(posX, posY, 0f));
        
        Matrix4f.mul(matrixProjection, custom, matrixModelViewProjection);
        matrixBuffer.position(0);
        matrixModelViewProjection.store(matrixBuffer);
        matrixBuffer.position(0);
        
        shaderSimpleTexure.render(typeObj, texture.id, color, verticesId, texCoordsId, indicesId, qtdIndices, matrixBuffer);
    }
	
	public static void drawWithTexture(int typeObj, GGTexture texture, Color color, FloatBuffer verticesBuffer, FloatBuffer texCoordsBuffer, IntBuffer indicesBuffer){
	    drawWithTexture(typeObj, texture, color, 0, 0, verticesBuffer, texCoordsBuffer, indicesBuffer);
    }
	
	public static void drawWithTexture(int typeObj, GGTexture texture, Color color, float posX, float posY, FloatBuffer verticesBuffer, FloatBuffer texCoordsBuffer, IntBuffer indicesBuffer){
        Matrix4f custom = new Matrix4f(matrixModelView);
        custom.translate(new Vector3f(posX, posY, 0f));
        
        Matrix4f.mul(matrixProjection, custom, matrixModelViewProjection);
        matrixBuffer.position(0);
        matrixModelViewProjection.store(matrixBuffer);
        matrixBuffer.position(0);
        
        shaderSimpleTexure.render(typeObj, texture.id, color, verticesBuffer, texCoordsBuffer, indicesBuffer, matrixBuffer);
    }
}

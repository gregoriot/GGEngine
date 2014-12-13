package openGL.shader;

import org.newdawn.slick.Color;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class SimpleExempleShaderColor {
	
	public String shaderVertex = "#version 120 \n"
			+ "uniform mat4 un_ModelViewProjection;"
			+ "attribute vec3 in_Position;"
			+ "void main(void) {"
			+ "		gl_Position =  un_ModelViewProjection * vec4(in_Position, 1.0);"
			+ "}";
	
	public String shaderFragment = "#version 120 \n"
			+ "uniform vec4 un_Color;"
			+ "void main(void) {"
			+ "		gl_FragColor = un_Color;"
			+ "}";

	private static int programShaderId;

	private static int atributePosition;
	
	private static int uniformModelViewProjection;
	private static int uniformColor;
	
	public SimpleExempleShaderColor(){
		int vertexShaderId = LoaderShader.loadShader(shaderVertex, GL20.GL_VERTEX_SHADER);
        int fragmentShaderId = LoaderShader.loadShader(shaderFragment, GL20.GL_FRAGMENT_SHADER);
        
        programShaderId = LoaderShader.createProgram(vertexShaderId, fragmentShaderId);
        
        GL20.glUseProgram(programShaderId);
        	atributePosition = GL20.glGetAttribLocation(programShaderId, "in_Position");

        	uniformModelViewProjection = GL20.glGetUniformLocation(programShaderId, "un_ModelViewProjection");
        	uniformColor = GL20.glGetUniformLocation(programShaderId, "un_Color");
        GL20.glUseProgram(0);
	}
	
	public void render(int typeObj, Color color, int verticesId, int indicesId, int qtdIndices, FloatBuffer matrixMVPBuffer){
		GL20.glUseProgram(programShaderId);
        
		GL20.glUniform4f(uniformColor,color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f);
		GL20.glUniformMatrix4(uniformModelViewProjection, false, matrixMVPBuffer);
        
        
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesId);
        GL20.glVertexAttribPointer(
    		atributePosition,
    		3,
    		GL11.GL_FLOAT,
    		false,
    		0,
    		0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        
        GL20.glEnableVertexAttribArray(atributePosition);
        
        	GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesId);
    		GL11.glDrawElements(typeObj, qtdIndices, GL11.GL_UNSIGNED_INT, 0);
    		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    		
    	GL20.glDisableVertexAttribArray(atributePosition);
        
        GL20.glUseProgram(0);
	}
	
	public void render(int typeObj, Color color, FloatBuffer verticesBuffer, IntBuffer indicesBuffer, FloatBuffer matrixMVPBuffer){
		GL20.glUseProgram(programShaderId);
        
		GL20.glUniform4f(uniformColor,color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f);
		GL20.glUniformMatrix4(uniformModelViewProjection, false, matrixMVPBuffer);
		
		
        GL20.glVertexAttribPointer(
    		atributePosition,
    		3,
    		false,
    		0,
    		verticesBuffer);
        
        
        GL20.glEnableVertexAttribArray(atributePosition);
        
        	GL11.glDrawElements(typeObj, indicesBuffer);
        
        GL20.glDisableVertexAttribArray(atributePosition);
	    
        GL20.glUseProgram(0);
	}
}

package openGL.shader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.Color;

public class SimpleExempleShaderTexture {
	
	public String shaderVertex = "#version 120 \n"
			+ "uniform mat4 un_ModelViewProjection;"
			+ "attribute vec3 in_Position;"
			+ "attribute vec2 in_TexCoord;"
			+ "varying vec2 pass_TexCoord;"
			
			+ "void main(void) {"
			+ "		gl_Position =  un_ModelViewProjection * vec4(in_Position, 1.0);"
			+ "		pass_TexCoord = in_TexCoord;"
			+ "}";
	
	public String shaderFragment = "#version 120 \n"
			+ "uniform vec4 un_Color;"
			+ "uniform sampler2D un_Texture0;"
			+ "varying vec2 pass_TexCoord;"
			
			+ "void main(void) {"
			+ "		gl_FragColor = texture2D(un_Texture0, pass_TexCoord) * un_Color;"
			+ "}";

	private static int programShaderId;

	private static int atributePosition;
	private static int atributeTexCoord;
	
	private static int uniformModelViewProjection;
	private static int uniformColor;
	private static int uniformTexture0;
	
	public SimpleExempleShaderTexture(){	
		int vertexShaderId = LoaderShader.loadShader(shaderVertex, GL20.GL_VERTEX_SHADER);
        int fragmentShaderId = LoaderShader.loadShader(shaderFragment, GL20.GL_FRAGMENT_SHADER);
        
        programShaderId = LoaderShader.createProgram(vertexShaderId, fragmentShaderId);
        
        GL20.glUseProgram(programShaderId);
        	atributePosition = GL20.glGetAttribLocation(programShaderId, "in_Position");
        	atributeTexCoord = GL20.glGetAttribLocation(programShaderId, "in_TexCoord");

        	uniformModelViewProjection = GL20.glGetUniformLocation(programShaderId, "un_ModelViewProjection");
        	uniformColor = GL20.glGetUniformLocation(programShaderId, "un_Color");
        	uniformTexture0 = GL20.glGetUniformLocation(programShaderId, "un_Texture0");
        	
        	GL20.glUniform1i(uniformTexture0, 0);
        GL20.glUseProgram(0);
	}
	
	public void render(int typeObj, int textureId, Color color, int verticesId, int texCoordsId, int indicesId, int qtdIndices, FloatBuffer matrixMVPBuffer){
		GL20.glUseProgram(programShaderId);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        
        GL20.glUniform4f(uniformColor, color.r, color.g, color.b, color.a);
		GL20.glUniformMatrix4(uniformModelViewProjection, false, matrixMVPBuffer);
        
        
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, verticesId);
        GL20.glVertexAttribPointer(
    		atributePosition,
    		3,
    		GL11.GL_FLOAT,
    		false,
    		0,
    		0);
        
    	GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, texCoordsId);
        GL20.glVertexAttribPointer(
    		atributeTexCoord,
    		2,
    		GL11.GL_FLOAT,
    		false,
    		0,
    		0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        
        
        GL20.glEnableVertexAttribArray(atributePosition);
        GL20.glEnableVertexAttribArray(atributeTexCoord);
	        
	        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesId);
	        GL11.glDrawElements(typeObj, qtdIndices, GL11.GL_UNSIGNED_INT, 0);
	        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	    
        GL20.glDisableVertexAttribArray(atributeTexCoord);
        GL20.glDisableVertexAttribArray(atributePosition);
        
        GL20.glUseProgram(0);
	}
	
	public void render(int typeObj, int textureId, Color color, FloatBuffer verticesBuffer, FloatBuffer texCoordsBuffer, IntBuffer indicesBuffer, FloatBuffer matrixMVPBuffer){
		GL20.glUseProgram(programShaderId);
        
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        
        GL20.glUniform4f(uniformColor, color.r, color.g, color.b, color.a);
		GL20.glUniformMatrix4(uniformModelViewProjection, false, matrixMVPBuffer);
		
        GL20.glVertexAttribPointer(
    		atributePosition,
    		3,
    		false,
    		0,
    		verticesBuffer);
        
        GL20.glVertexAttribPointer(
    		atributeTexCoord,
    		2,
    		false,
    		0,
    		texCoordsBuffer);
        
        
        GL20.glEnableVertexAttribArray(atributePosition);
        GL20.glEnableVertexAttribArray(atributeTexCoord);
        
        	GL11.glDrawElements(typeObj, indicesBuffer);
        
        GL20.glDisableVertexAttribArray(atributeTexCoord);
        GL20.glDisableVertexAttribArray(atributePosition);
	    
        GL20.glUseProgram(0);
	}
}

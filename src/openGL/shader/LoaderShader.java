package openGL.shader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class LoaderShader {
	
	public static int loadShader(String stringShader, int type){
		int shaderId;
		
		shaderId = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderId, stringShader);
		GL20.glCompileShader(shaderId);
		
		if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			System.err.println("Shader - load - Could not compile shader");
			
			String errors = GL20.glGetShaderInfoLog(shaderId, 1000).trim();
			if(errors.length() > 0)
				System.err.println("Shader errors: " + errors);
			
			return 0;
		}
		
		return shaderId;
	}

	public static int loadShader(InputStream inputStream, int type){
		int shaderId;
		StringBuilder shaderSource = new StringBuilder();
		
		String line;
		BufferedReader reader;
		
		try{
			reader = new BufferedReader(new InputStreamReader(inputStream));
			
			while((line = reader.readLine()) != null)
				shaderSource.append(line).append("\n");
			
			reader.close();
		}catch(Exception e){
			try{
				inputStream.close();
			}catch(Exception ex){}
			
			System.err.println("Shader - load - Error load shader");
			e.printStackTrace();
			return 0;
		}
		
		shaderId = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderId, shaderSource);
		GL20.glCompileShader(shaderId);
		
		if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			System.err.println("Shader - load - Could not compile shader");
			
			String errors = GL20.glGetShaderInfoLog(shaderId, 1000).trim();
			if(errors.length() > 0)
				System.err.println("Shader errors: " + errors);
			
			return 0;
		}
		
		return shaderId;
	}
	
	public static int createProgram(int shaderId){
		int programId = GL20.glCreateProgram();
		
		GL20.glAttachShader(programId, shaderId);

		GL20.glLinkProgram(programId);
		GL20.glValidateProgram(programId);
		
		return programId;
	}
	
	public static int createProgram(int vertexShaderId, int fragmentShaderId){
		int programId = GL20.glCreateProgram();
		
		GL20.glAttachShader(programId, vertexShaderId);
		GL20.glAttachShader(programId, fragmentShaderId);

		GL20.glLinkProgram(programId);
		GL20.glValidateProgram(programId);
		
		return programId;
	}

}

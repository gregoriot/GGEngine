package managers;

import java.io.File;

public class GGSaveLoad {
    
    protected String directoryPath;

    public GGSaveLoad(String folderName){
        if(System.getProperty("file.separator").equals("/"))
            directoryPath = System.getProperty("user.home")+"/."+folderName+"/";
        else
            directoryPath = System.getProperty("user.home")+"\\appdata\\"+folderName+"\\";
    }
    
    protected final boolean canSave(String fileName){
        File folder = new File(directoryPath);
        File file = new File(directoryPath+fileName);
        if(!folder.exists()){
            if(!folder.mkdirs()){
                System.out.println("Utils - SaveLoad - Cannot create folder to save file - "+file.getPath());
                return false;
            }
        }else{
            if(!folder.canWrite()){
                System.out.println("Utils - SaveLoad - Folder without permissions - "+file.getPath());
                return false;
            }else{
                if(file.exists() && !file.canWrite()){
                    System.out.println("Utils - SaveLoad - File without permissions - "+file.getPath());
                    return false;
                }
            }
        }
        return true;
    }
    
    protected final boolean canLoad(String fileName){
        File file = new File(directoryPath+fileName);
        if(!file.exists() || !file.canRead()){
            System.out.println("Utils - SaveLoad - Cannot exist save file - "+file.getPath());
            return false;
        }
        return true;
    }
    
}

package managers;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import utils.GGGamePanel;
import utils.PVector;

public class GGDisplay {

    private static LinkedList<DisplayMode> displays = new LinkedList<DisplayMode>();
    private static int currentDisplay = -1;
    @SuppressWarnings("unused")
    private PVector locationWindow;
    
    public static void loadDisplays(){
        try {
            DisplayMode [] modes = Display.getAvailableDisplayModes();
            for(DisplayMode currentMode: modes){
                displays.add(currentMode);
            }
            
            Collections.sort(displays, new Comparator<DisplayMode>() {

                @Override
                public int compare(DisplayMode o1, DisplayMode o2) {
                    if(o1.getWidth() == o2.getWidth()){
                        if(o1.getHeight() == o2.getHeight()){
                            if(o1.getFrequency() < o2.getFrequency()){
                                return -1;
                            }
                        }
                        
                    }else if(o1.getWidth() < o2.getWidth()){
                        return -1;
                    }
                    return 1;
                }
                
            });
            
            System.out.println("Main - main - Available displayModes");
            int count = 0;
            for(Iterator<DisplayMode> iterator = displays.iterator(); iterator.hasNext();){
                System.out.println(count+" : "+iterator.next().toString());
                count ++;
            }
            
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Set the display mode to Fullscreen or windows.
     * 
     * @param fullscreen True if we want fullscreen mode
     */
    public static boolean setFullscreen(boolean fullscreen) {
        return setDisplay(currentDisplay, fullscreen);
    }
    
    public static LinkedList<DisplayMode> getAllDisplays(){
        return displays;
    }
    
    public static DisplayMode getDisplay(){
        return displays.get(currentDisplay);
    }
    
    public static DisplayMode getDisplay(int index){
        return displays.get(index);
    }
    
    public static int getDisplay(String displayToString){
        int pos = 0;
        for(Iterator<DisplayMode> iterator = displays.iterator(); iterator.hasNext();){
            DisplayMode display = iterator.next();
            if(display.toString().equals(displayToString))
                return pos;
            pos++;
        }
        return -1;
    }
    
    /**
     * Set the display mode to be used. Fullscreen or windows.
     * 
     * @param index of available display modes
     * @param fullscreen True if we want fullscreen mode
     */
    public static boolean setDisplay(int index, boolean fullscreen) {

        /* return if requested DisplayMode is already set */
        if (index == currentDisplay &&
            Display.isFullscreen() == fullscreen) {
            return false;
        }

        try {
            if(index < displays.size()){
                currentDisplay = index;
                
                Display.setDisplayMode(displays.get(index));
                Display.setFullscreen(fullscreen);
                
                GGGamePanel.instance.width = displays.get(index).getWidth();
                GGGamePanel.instance.height = displays.get(index).getHeight();
                return true;
            }
            return false;
        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode."+ e);
            return false;
        }
    }
}

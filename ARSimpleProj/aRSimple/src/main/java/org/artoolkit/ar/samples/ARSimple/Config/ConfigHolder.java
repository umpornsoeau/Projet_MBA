package org.artoolkit.ar.samples.ARSimple.Config;

import org.artoolkit.ar.base.ARToolKit;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by norips on 20/10/16.
 */

public class ConfigHolder {
    private static ArrayList<Canvas> targets;
    private static ConfigHolder instance = null;
    public void init(ArrayList<Canvas> targets){
        this.targets = (ArrayList<Canvas>) targets.clone();
    }

    public static ConfigHolder getInstance(){
        if (instance == null) instance = new ConfigHolder();
        return instance;
    }

    /**
     * Draw all VISIBLE canvas
     * @param gl
     */
    public void draw(GL10 gl){
        for(Canvas t : targets){
            if(ARToolKit.getInstance().queryMarkerVisible(t.getMarkerUID())) {
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadMatrixf(ARToolKit.getInstance().queryMarkerTransformation(t.getMarkerUID()), 0);
                t.draw(gl);
            }
        }
    }


}

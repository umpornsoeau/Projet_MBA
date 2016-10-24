package org.artoolkit.ar.samples.ARSimple.Model;

import android.content.Context;
import android.util.Log;

import org.artoolkit.ar.base.rendering.RenderUtils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by norips on 24/10/16.
 */

public abstract class Rectangle {
    protected FloatBuffer mVertexBuffer;
    protected FloatBuffer mTexBuffer;
    protected ShortBuffer mIndexBuffer;
    protected ArrayList<String> pathToTextures;
    protected Context context;
    protected short[] indices = {0,1,2,2,3,0};          //      0***1
                                                        //      *   *
                                                        //      3***2

    protected float[] texCoords = {
            0,0, //Reverse axis Top left
            1,0, //Top right
            1,1, //Bottom right
            0,1};//Bottom left

    /**
     *
     * @param pos Array of 3D position, the four point of your rectangle
     *            pos[0] = Top Left corner
     *            pos[1] = Top Right corner
     *            pos[2] = Bottom Right corner
     *            pos[3] = Bottom Left corner
     * @param pathToTextures An ArrayList<String> containing paths to your texture
     *
     */
    public Rectangle(float pos[][],ArrayList<String> pathToTextures,Context context) {
        setArrays(pos);
        this.pathToTextures = (ArrayList<String>) pathToTextures.clone();
        this.context = context;
    }


    public FloatBuffer getmVertexBuffer() {
        return mVertexBuffer;
    }

    public ShortBuffer getmIndexBuffer() {
        return mIndexBuffer;
    }


    private void setArrays(float pos[][]) {


        float vertices[] = new float[12];
        for(int i = 0; i < 4;i++){
            vertices[(i*3)] = pos[i][0];
            vertices[(i*3)+1] = pos[i][1];
            vertices[(i*3)+2] = pos[i][2];
            Log.d("RectText","" + vertices[(i*3)] + "," + vertices[(i*3)+1] + "," + vertices[(i*3)+2]);
        }

        mVertexBuffer = RenderUtils.buildFloatBuffer(vertices);
        mIndexBuffer = RenderUtils.buildShortBuffer(indices);
        mTexBuffer = RenderUtils.buildFloatBuffer(texCoords);
    }

    public abstract void draw(GL10 gl);
}
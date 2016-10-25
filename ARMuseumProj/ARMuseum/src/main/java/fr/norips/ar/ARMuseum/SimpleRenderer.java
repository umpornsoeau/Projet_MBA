/*
 *  SimpleRenderer.java
 *  ARToolKit5
 *
 *  Disclaimer: IMPORTANT:  This Daqri software is supplied to you by Daqri
 *  LLC ("Daqri") in consideration of your agreement to the following
 *  terms, and your use, installation, modification or redistribution of
 *  this Daqri software constitutes acceptance of these terms.  If you do
 *  not agree with these terms, please do not use, install, modify or
 *  redistribute this Daqri software.
 *
 *  In consideration of your agreement to abide by the following terms, and
 *  subject to these terms, Daqri grants you a personal, non-exclusive
 *  license, under Daqri's copyrights in this original Daqri software (the
 *  "Daqri Software"), to use, reproduce, modify and redistribute the Daqri
 *  Software, with or without modifications, in source and/or binary forms;
 *  provided that if you redistribute the Daqri Software in its entirety and
 *  without modifications, you must retain this notice and the following
 *  text and disclaimers in all such redistributions of the Daqri Software.
 *  Neither the name, trademarks, service marks or logos of Daqri LLC may
 *  be used to endorse or promote products derived from the Daqri Software
 *  without specific prior written permission from Daqri.  Except as
 *  expressly stated in this notice, no other rights or licenses, express or
 *  implied, are granted by Daqri herein, including but not limited to any
 *  patent rights that may be infringed by your derivative works or by other
 *  works in which the Daqri Software may be incorporated.
 *
 *  The Daqri Software is provided by Daqri on an "AS IS" basis.  DAQRI
 *  MAKES NO WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 *  THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS
 *  FOR A PARTICULAR PURPOSE, REGARDING THE DAQRI SOFTWARE OR ITS USE AND
 *  OPERATION ALONE OR IN COMBINATION WITH YOUR PRODUCTS.
 *
 *  IN NO EVENT SHALL DAQRI BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL
 *  OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION,
 *  MODIFICATION AND/OR DISTRIBUTION OF THE DAQRI SOFTWARE, HOWEVER CAUSED
 *  AND WHETHER UNDER THEORY OF CONTRACT, TORT (INCLUDING NEGLIGENCE),
 *  STRICT LIABILITY OR OTHERWISE, EVEN IF DAQRI HAS BEEN ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 *
 *  Copyright 2015 Daqri, LLC.
 *  Copyright 2011-2015 ARToolworks, Inc.
 *
 *  Author(s): Julian Looser, Philip Lamb
 *
 */

package fr.norips.ar.ARMuseum;

import android.content.Context;

import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.rendering.ARRenderer;
import fr.norips.ar.ARMuseum.Config.ConfigHolder;
import fr.norips.ar.ARMuseum.Config.Model;
import fr.norips.ar.ARMuseum.Config.Canvas;
import fr.norips.ar.ARMuseum.Model.RectMovie;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * A very simple Renderer that adds a marker and draws a cube on it.
 */


public class SimpleRenderer extends ARRenderer {

    private Context context;
    /**
     * Markers can be configured here.
     */
    @Override
    public boolean configureARScene() {
        //Construction init, this will be done by a JSON Parser
        Canvas t = new Canvas("Pinball","Data/pinball");
        float[][] tab = {
                {0,100,0},
                {100,100,0},
                {100,0,0},
                {0,0,0},
        };
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add("Data/tex_pinball.png");
        tmp.add("Data/tex_pinball2.png");
        t.addModel(new Model("Sur tableau",tab,tmp,context));
        float[][] tab2 = {
                {-100,100,0},
                {0,100,0},
                {0,0,0},
                {-100,0,0},
        };
        //tmp.clear();
        //tmp.add("Data/movie.mp4");
        //t.addModel(new Model("Cote tableau",new RectMovie(tab2,tmp,context)));
        ArrayList<Canvas> tableaux = new ArrayList<Canvas>();
        tableaux.add(t);
        ConfigHolder.getInstance().init(tableaux);

        return true;
    }
    public SimpleRenderer(Context cont) {
        context = cont;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl,config);
    }

    /**
     * Override the draw function from ARRenderer.
     */
    @Override
    public void draw(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // Apply the ARToolKit projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);

        //Rotate it by 90 degrees
        gl.glLoadIdentity();
        gl.glRotatef(90.0f, 0.0f, 0.0f, -1.0f);
        gl.glMultMatrixf(ARToolKit.getInstance().getProjectionMatrix(), 0);

        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glFrontFace(GL10.GL_CW);

        ConfigHolder.getInstance().draw(gl);


//        // If the marker is visible, apply its transformation, and draw a cube
//        if (ARToolKit.getInstance().queryMarkerVisible(markerID)) {
//            gl.glMatrixMode(GL10.GL_MODELVIEW);
//            gl.glLoadMatrixf(ARToolKit.getInstance().queryMarkerTransformation(markerID), 0);
//
//            int pattID = ARToolKit.getInstance().getMarkerPatternCount(markerID);
//
//            if(pattID!=0 && rectTex == null ){
//                float[] matrix = new float[16];
//                float[] height = new float[1];
//                float[] width = new float[1];
//                int[] imsX = new int[1];
//                int[] imsY = new int[1];
//                ARToolKit.getInstance().getMarkerPatternConfig(markerID,pattID-1,matrix,width,height,imsX,imsY);
//                Log.d("SimpleRenderer","x:" + imsX[0] + " y:" + imsY[0]);
//                float[][] array = new float[4][3];
//                array[0][0] = 0.0f;
//                array[0][1] = height[0];
//                array[0][2] = 1.0f;
//
//                array[1][0] = width[0];
//                array[1][1] = height[0];
//                array[1][2] = 1.0f;
//
//                array[2][0] = width[0];
//                array[2][1] = 0.0f;
//                array[2][2] = 1.0f;
//
//                array[3][0] = 0.0f;
//                array[3][1] = 0.0f;
//                array[3][2] = 1.0f;
//                ArrayList<String> list = new ArrayList<String>();
//                list.add("Data/tex_pinball.png");
//                rectTex = new RectTex(array,list);
//            }
//            if(rectTex != null){
//                rectTex.draw(gl,context);
//            }
//
//        }
//        if (ARToolKit.getInstance().queryMarkerVisible(markerID2)) {
//
//            gl.glMatrixMode(GL10.GL_MODELVIEW);
//            gl.glLoadMatrixf(ARToolKit.getInstance().queryMarkerTransformation(markerID2), 0);
//            int pattID = ARToolKit.getInstance().getMarkerPatternCount(markerID2);
//
//
//            if(pattID!=0) {
//                float[] matrix = new float[16];
//                float[] height = new float[1];
//                float[] width = new float[1];
//                int[] imsX = new int[1];
//                int[] imsY = new int[1];
//                ARToolKit.getInstance().getMarkerPatternConfig(markerID2,pattID-1,matrix,width,height,imsX,imsY);
//                gl.glTranslatef(width[0]/2.0f,height[0]/2.0f,1);
//                gl.glScalef(width[0],height[0],1);
//
//            }
//
//            cubeTex.draw(gl);
//        }

    }
}
package com.badlogic.androidgames.gamedev2d;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.FPSCounter;
import com.badlogic.androidgames.framework.gl.SpatialHashGrid;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.Vertices;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;

public class TextureAtlasTest extends GLGame {

    @Override
    public Screen getStartScreen() {
        return new TextureAtlasScreen(this);
    }

    class TextureAtlasScreen extends Screen {
        final int NUM_TARGETS = 100;
        final float WORLD_WIDTH = 9.6f;
        final float WORLD_HEIGHT = 4.8f;
        GLGraphics glGraphics;        
        Cannon cannon;
        DynamicGameObject ball;
        List<GameObject> targets;
        SpatialHashGrid grid;
        Camera2D camera;
        Texture texture;
        
        Vertices cannonVertices;
        Vertices ballVertices;
        Vertices targetVertices;
        
        Vector2 touchPos = new Vector2();
        Vector2 gravity = new Vector2(0,-10);
        FPSCounter fpsCounter;
    
        public TextureAtlasScreen(Game game) {
            super(game);
            glGraphics = ((GLGame)game).getGLGraphics();
            
            cannon = new Cannon(0, 0, 1, 0.5f);
            ball = new DynamicGameObject(0, 0, 0.2f, 0.2f);
            targets = new ArrayList<GameObject>(NUM_TARGETS);
            grid = new SpatialHashGrid(WORLD_WIDTH, WORLD_HEIGHT, 2.5f);
            for(int i = 0; i < NUM_TARGETS; i++) {
                GameObject target = new GameObject((float)Math.random() * WORLD_WIDTH, 
                                                   (float)Math.random() * WORLD_HEIGHT,
                                                   0.5f, 0.5f);  
                grid.insertStaticObject(target);
                targets.add(target);
            }
            camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);            
            
            cannonVertices = new Vertices(glGraphics, 4, 6, false, true);
            cannonVertices.setVertices(new float[] { -0.5f, -0.25f, 0.0f, 0.5f, 
                                                      0.5f, -0.25f, 1.0f, 0.5f,
                                                      0.5f,  0.25f, 1.0f, 0.0f,
                                                     -0.5f,  0.25f, 0.0f, 0.0f }, 
                                                     0, 16);
            cannonVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
            
            ballVertices = new Vertices(glGraphics, 4, 6, false, true);            
            ballVertices.setVertices(new float[] { -0.1f, -0.1f, 0.0f, 0.75f,
                                                    0.1f, -0.1f, 0.25f, 0.75f,
                                                    0.1f,  0.1f, 0.25f, 0.5f,
                                                   -0.1f,  0.1f, 0.0f, 0.5f }, 
                                                    0, 16);
            ballVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
            
            targetVertices = new Vertices(glGraphics, 4, 6, false, true);
            targetVertices.setVertices(new float[] { -0.25f, -0.25f, 0.5f, 1.0f,
                                                      0.25f, -0.25f, 1.0f, 1.0f,
                                                      0.25f,  0.25f, 1.0f, 0.5f,
                                                     -0.25f,  0.25f, 0.5f, 0.5f }, 
                                                     0, 16);
            targetVertices.setIndices(new short[] {0, 1, 2, 2, 3, 0}, 0, 6);
            fpsCounter = new FPSCounter();
        }               

        @Override
        public void resume() {
            texture = new Texture(((GLGame)game), "atlas.png");
        }
        
        @Override
        public void update(float deltaTime) {
            List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
            game.getInput().getKeyEvents();
        
            int len = touchEvents.size();
            for (int i = 0; i < len; i++) {
                TouchEvent event = touchEvents.get(i);                
                camera.touchToWorld(touchPos.set(event.x, event.y));
                cannon.angle = touchPos.sub(cannon.position).angle();                       
                
                if(event.type == TouchEvent.TOUCH_UP) {
                    float radians = cannon.angle * Vector2.TO_RADIANS;
                    float ballSpeed = touchPos.len() * 2;
                    ball.position.set(cannon.position);
                    ball.velocity.x = FloatMath.cos(radians) * ballSpeed;
                    ball.velocity.y = FloatMath.sin(radians) * ballSpeed;
                    ball.bounds.lowerLeft.set(ball.position.x - 0.1f, ball.position.y - 0.1f);
                }
            }
                                          
            if(ball.velocity.x != 0 || ball.velocity.y != 0) {
                ball.velocity.add(gravity.x * deltaTime, gravity.y * deltaTime);
                ball.position.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
                ball.bounds.lowerLeft.add(ball.velocity.x * deltaTime, ball.velocity.y * deltaTime);
            }
            
            List<GameObject> colliders = grid.getPotentialColliders(ball);
            len = colliders.size(); 
            for(int i = 0; i < len; i++) {
                GameObject collider = colliders.get(i);
                if(OverlapTester.overlapRectangles(ball.bounds, collider.bounds)) {
                    grid.removeObject(collider);
                    targets.remove(collider);
                }
            }
            
            if(ball.position.y > 0) {
                camera.position.set(ball.position);
                camera.zoom = 1 + ball.position.y / WORLD_HEIGHT; 
            } else {
                camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2);
                camera.zoom = 1;
            }                           
        }

        @Override
        public void present(float deltaTime) {
            GL10 gl = glGraphics.getGL();
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            camera.setViewportAndMatrices();
            
            gl.glEnable(GL10.GL_BLEND);
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            texture.bind();
                        
            targetVertices.bind();
            int len = targets.size();
            for(int i = 0; i < len; i++) {               
                GameObject target = targets.get(i);
                gl.glLoadIdentity();
                gl.glTranslatef(target.position.x, target.position.y, 0);
                targetVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            }
            targetVertices.unbind();
            
            gl.glLoadIdentity();
            gl.glTranslatef(ball.position.x, ball.position.y, 0);            
            ballVertices.bind();
            ballVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            ballVertices.unbind();  
            
            gl.glLoadIdentity();
            gl.glTranslatef(cannon.position.x, cannon.position.y, 0);
            gl.glRotatef(cannon.angle, 0, 0, 1);            
            cannonVertices.bind();
            cannonVertices.draw(GL10.GL_TRIANGLES, 0, 6);
            cannonVertices.unbind();
            fpsCounter.logFrame();
        }

        @Override
        public void pause() {
            
        }

        @Override
        public void dispose() {
            
        }        
    }
}

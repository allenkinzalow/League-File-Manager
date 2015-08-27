package com.kinztech.league.scene;

import com.kinztech.league.raf.archives.files.skeleton.SklFile;
import com.kinztech.league.raf.archives.files.skin.SkinVertex;
import com.kinztech.league.raf.archives.files.skin.SknFile;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.*;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 */
public class QuadExample {

    public SklFile skeleton;
    public SknFile skin;

    private static float x = 0.5f;
    private static float y = 0.5f;
    private static float z = 0.5f;
    private static float yaw = 0f;
    private static float pitch = 0f;

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        Matrix4f mvMatrix = new Matrix4f();
        mvMatrix.setIdentity();

        // init OpenGL
        /*GL11.glViewport(0, 0, 800, 600);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_STENCIL_TEST);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glEnable(GL11.GL_TEXTURE_2D);*/

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        //GLU.gluPerspective(65, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 800);
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);

        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glViewport(0, 0, 800, 600);

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("C:\\Users\\allen_000\\Desktop\\League Cache\\DATA\\Characters\\Janna\\Skins\\Base\\Janna_base_TX_CM.png"));
        } catch (Exception e){
            e.printStackTrace();
        }

        int texid = GL11.glGenTextures();

        int[] pixels = new int[image.getWidth() * image.getHeight()];

        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = ByteBuffer.allocateDirect(pixels.length * 4).order(ByteOrder.nativeOrder());
        for (int pixel : pixels) {
            buffer.put((byte) ((pixel >> 16) & 0xFF));
            buffer.put((byte) ((pixel >>  8) & 0xFF));
            buffer.put((byte) ( pixel        & 0xFF));
            buffer.put((byte) ((pixel >> 24) & 0xFF));
        }
        buffer.flip();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texid);

		/* use trilinear filtering (TODO is this the best place?) */
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        //GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        /*int[] texturePixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0,0,image.getWidth(),image.getHeight(), texturePixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = texturePixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip();*/

        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, "#ifdef GL_ES precision highp float; #endif varying vec2 vTextureCoord; uniform sampler2D uSampler; void main(void) { gl_FragColor = texture2D(uSampler, vec2(vTextureCoord.s, vTextureCoord.t)); }");
        GL20.glCompileShader(fragmentShader);

        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, "attribute vec3 aVertexPosition; attribute vec2 aTextureCoord; uniform mat4 uMVMatrix; uniform mat4 uPMatrix; uniform float uScale; varying vec2 vTextureCoord; void main(void) { gl_Position = uPMatrix * uMVMatrix * vec4(aVertexPosition * uScale, 1.0); vTextureCoord = aTextureCoord; }");
        GL20.glCompileShader(vertexShader);

        int shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);
        GL20.glUseProgram(shaderProgram);

        int vertexPositiveAttribute = GL20.glGetAttribLocation(shaderProgram, "aVertexPosition");
        GL20.glEnableVertexAttribArray(vertexPositiveAttribute);

        int textureCoordAttribute = GL20.glGetAttribLocation(shaderProgram, "aTextureCoord");
        GL20.glEnableVertexAttribArray(textureCoordAttribute);

        int pMatrixUniform = GL20.glGetUniformLocation(shaderProgram, "uPMatrix");
        int mvMatrixUniform = GL20.glGetUniformLocation(shaderProgram, "uMVMatrix");
        int samplerUniform = GL20.glGetUniformLocation(shaderProgram, "uSampler");
        int scaleUniform = GL20.glGetUniformLocation(shaderProgram, "uScale");

        /*int texture = GL11.glGenTextures();
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, texture);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_NEAREST);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);*/

        /*int vertexArrayObject = 0;
        vertexArrayObject = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayObject);*/

        int vertexBuffer = GL15.glGenBuffers();
        int vertexNormalBuffer = GL15.glGenBuffers();
        int textureBuffer = GL15.glGenBuffers();
        int indexBuffer = GL15.glGenBuffers();

        /**
         * Vertex Array Buffer
         */
        FloatBuffer vertexFloatBuffer = BufferUtils.createFloatBuffer(skin.getSkin().getVerticesCount() * 3);
        for(SkinVertex vertex : skin.getSkin().getVertices()) {
            /*for(int vi = 0; vi < vertex.getPosition().length; vi++) {
                float pos = vertex.getPosition()[vi];
                if(vi == 0)
                    pos += 400;
                if(vi == 1) {
                    pos += 300;
                }
                if(vi == 2)
                    pos *= -1;
                vertexFloatBuffer.put(pos);
            }*/
            vertexFloatBuffer.put(vertex.getPosition()[0] + 400);
            vertexFloatBuffer.put(vertex.getPosition()[1] + 300);
            vertexFloatBuffer.put(-vertex.getPosition()[2]);
        }
        vertexFloatBuffer.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexFloatBuffer, GL15.GL_STATIC_DRAW);
        //GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        //GL20.glEnableVertexAttribArray(0);


        /**
         * Vertex Normal Array Buffer
         */
        FloatBuffer vertexNormalFloatBuffer = BufferUtils.createFloatBuffer(skin.getSkin().getVerticesCount() * 3);
        for(SkinVertex vertex : skin.getSkin().getVertices()) {
            /*for(int vi = 0; vi < vertex.getNormal().length; vi++) {
                float pos = vertex.getNormal()[vi];
                if(vi == 2)
                    pos = -pos;
                vertexNormalFloatBuffer.put(pos);
            }*/
            vertexNormalFloatBuffer.put(vertex.getNormal()[0] + 400);
            vertexNormalFloatBuffer.put(vertex.getNormal()[1] + 300);
            vertexNormalFloatBuffer.put(-vertex.getNormal()[2]);
        }
        vertexNormalFloatBuffer.flip();

        //GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexNormalBuffer);
        //GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexNormalFloatBuffer, GL15.GL_STATIC_DRAW);
        //GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
        //GL20.glEnableVertexAttribArray(1);


        /**
         * Texture Coordinate Buffer
         */
        FloatBuffer textCoordBuffer = BufferUtils.createFloatBuffer(skin.getSkin().getVerticesCount() * 2);
        for(SkinVertex vertex : skin.getSkin().getVertices()) {
            textCoordBuffer.put(vertex.getTextureCoords()[0]);
            textCoordBuffer.put(vertex.getTextureCoords()[1]);
        }
        textCoordBuffer.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureBuffer);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textCoordBuffer, GL15.GL_STATIC_DRAW);
        //GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 0, 0);
        //GL20.glEnableVertexAttribArray(2);


        /**
         * Index Buffer
         */
        ShortBuffer indexFloatBuffer = BufferUtils.createShortBuffer(skin.getSkin().getIndicesCount());
        for(int id : skin.getSkin().getIndices()) {
            indexFloatBuffer.put((short)id);
        }
        indexFloatBuffer.flip();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexFloatBuffer, GL15.GL_STATIC_DRAW);

        //GL30.glBindVertexArray(0);

        while (!Display.isCloseRequested()) {
            GL11.glLoadIdentity();

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_STENCIL_TEST);


            // set the color of the quad (R,G,B,A)
            GL11.glClearColor(0.1f, 0.2f, 0.5f, 0);

            //GL11.glRotatef(1.20f, 1f, 0, 0);

            /*Matrix4f pMatrix = Matrix4f.perspective(45, 800 / 600, 0.3f, 3000);
            GL20.glUniformMatrix4(pMatrixUniform, false, pMatrix.getBuffer());*/

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer);
            GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
            //GL20.glVertexAttribPointer(vertexPositiveAttribute, 3, GL11.GL_FLOAT, false, 0 ,0);
            //GL20.glEnableVertexAttribArray(vertexPositiveAttribute);


            //GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexNormalBuffer);
            //GL11.glNormalPointer(GL11.GL_FLOAT, 0, 0);

            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textureBuffer);
            //GL20.glVertexAttribPointer(textureCoordAttribute, 2, GL11.GL_FLOAT, false, 0 ,0);
            //GL20.glEnableVertexAttribArray(textureCoordAttribute);
            GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);

            /*GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texid);
            GL20.glUniform1i(samplerUniform, 0);
            GL20.glUniform1f(scaleUniform, 1);*/

            /*GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBuffer);
            GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);*/

            /*GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
            GL20.glUniformMatrix4(mvMatrixUniform, false, mvMatrix.getBuffer());*/

            //GL11.glBegin(GL11.GL_TRIANGLES);

            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texid);
            GL20.glUniform1i(samplerUniform, 0);
            GL20.glUniform1f(scaleUniform, 1);

            //GL30.glBindVertexArray(vertexArrayObject);
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
            GL11.glDrawElements(GL11.GL_TRIANGLES, skin.getSkin().getIndicesCount(), GL11.GL_UNSIGNED_SHORT, 0);
            //GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, skin.getSkin().getIndicesCount());

            GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

            //GL11.glBegin(GL11.GL_QUADS);

            GL11.glBegin(GL11.GL_TRIANGLES);
            for(SkinVertex vertex : skin.getSkin().getVertices()) {
                GL11.glTexCoord2f(vertex.getTextureCoords()[0], vertex.getTextureCoords()[1]);
                GL11.glNormal3f(vertex.getNormal()[0] + 600, vertex.getNormal()[1] + 300, -vertex.getNormal()[2]);
                GL11.glVertex3f(vertex.getPosition()[0] + 600,vertex.getPosition()[1] + 300,-vertex.getPosition()[2]);

            }

            /*GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
            GL11.glDrawElements(GL11.GL_TRIANGLES, skin.getSkin().getIndicesCount(), GL11.GL_UNSIGNED_SHORT, 0);*/

            GL11.glEnd();

            Display.update();
        }

        Display.destroy();
    }

    public static void main(String[] argv) {
        QuadExample quadExample = new QuadExample();
        /*quadExample.skeleton = new SklFile(new File("C:\\Users\\allen_000\\Desktop\\League Cache\\DATA\\Characters\\Azir\\Skins\\Base\\Azir.skl"));
        quadExample.skeleton.decode();*/
        quadExample.skin = new SknFile(new File("C:\\Users\\allen_000\\Desktop\\League Cache\\DATA\\Characters\\Janna\\Skins\\Base\\Janna.skn"));
        quadExample.skin.decode();
        quadExample.start();
    }
}
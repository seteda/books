public class TexturedSquareRenderer extends AbstractSingleTexturedRenderer
{
   //Number of points or vertices we want to use
   private final static int VERTS = 4;

   //A raw native buffer to hold the point coordinates
   private FloatBuffer mFVertexBuffer;

   //A raw native buffer to hold the point coordinates
   private FloatBuffer mFTextureBuffer;

   //A raw native buffer to hold indices
   //allowing a reuse of points.
   private ShortBuffer mIndexBuffer;
   private int numOfIndices = 0;
   private int sides = 4;

   public TexturedSquareRenderer(Context context)
   {
      super(context,com.ai.android.OpenGL.R.drawable.robot);
      prepareBuffers(sides);
   }
   private void prepareBuffers(int sides)
   {
      RegularPolygon t = new RegularPolygon(0,0,0,0.5f,sides);
      this.mFVertexBuffer = t.getVertexBuffer();
      this.mFTextureBuffer = t.getTextureBuffer();
      this.mIndexBuffer = t.getIndexBuffer();
      this.numOfIndices = t.getNumberOfIndices();
      this.mFVertexBuffer.position(0);
      this.mIndexBuffer.position(0);
      this.mFTextureBuffer.position(0);
   }

   //overriden method
   protected void draw(GL10 gl)
   {
      prepareBuffers(sides);
      gl.glEnable(GL10.GL_TEXTURE_2D);
      CHAPTER 10: Programming 3D Graphics with OpenGL 55
      gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
      gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mFTextureBuffer);
      gl.glDrawElements(GL10.GL_TRIANGLES, this.numOfIndices,
      GL10.GL_UNSIGNED_SHORT, mIndexBuffer);
   }
}

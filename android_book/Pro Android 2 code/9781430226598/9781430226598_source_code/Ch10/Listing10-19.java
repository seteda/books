public class MultiViewTestHarnessActivity extends Activity {
   private GLSurfaceView mTestHarness;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      mTestHarness = new GLSurfaceView(this);
      mTestHarness.setEGLConfigChooser(false);

      Intent intent = getIntent();
      int mid = intent.getIntExtra("com.ai.menuid", R.id.MenuId_OpenGL15_Current);

      if (mid == R.id.MenuId_OpenGL15_Current)
      {
         mTestHarness.setRenderer(new TexturedPolygonRenderer(this));
         mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
         setContentView(mTestHarness);
         return;
      }
      if (mid == R.id.mid_OpenGL15_SimpleTriangle)
      {
         mTestHarness.setRenderer(new SimpleTriangleRenderer(this));
         mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
         setContentView(mTestHarness);
         return;
      }
      if (mid == R.id.mid_OpenGL15_AnimatedTriangle15)
      {
         mTestHarness.setRenderer(new AnimatedSimpleTriangleRenderer(this));
         setContentView(mTestHarness);
         return;
      }
      if (mid == R.id.mid_rectangle)
      {
         mTestHarness.setRenderer(new SimpleRectRenderer(this));
         mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
         setContentView(mTestHarness);
         return;
      }
      if (mid == R.id.mid_square_polygon)
      {
         mTestHarness.setRenderer(new SquareRenderer(this));
         mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
         setContentView(mTestHarness);
         return;
      }
      if (mid == R.id.mid_polygon)
      {
         mTestHarness.setRenderer(new PolygonRenderer(this));
         setContentView(mTestHarness);
         return;
      }
      if (mid == R.id.mid_textured_square)
      {
         mTestHarness.setRenderer(new TexturedSquareRenderer(this));
         mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
         setContentView(mTestHarness);
         return;
      }
      //otherwise do this
      mTestHarness.setRenderer(new TexturedPolygonRenderer(this));
      mTestHarness.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
      setContentView(mTestHarness);
      return;
   }

   @Override
   protected void onResume() {
      super.onResume();
      mTestHarness.onResume();
   }

   @Override
   protected void onPause() {
      super.onPause();
      mTestHarness.onPause();
   }
}

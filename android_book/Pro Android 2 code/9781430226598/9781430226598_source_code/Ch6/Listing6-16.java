public class ViewAnimation extends Animation
{
   public ViewAnimation2(){}

   @Override
   public void initialize(int width, int height, int parentWidth,
                                 int parentHeight)
   {
      super.initialize(width, height, parentWidth, parentHeight);
      setDuration(2500);
      setFillAfter(true);
      setInterpolator(new LinearInterpolator());
   }

   @Override
   protected void applyTransformation(float interpolatedTime, Transformation t)
   {
      final Matrix matrix = t.getMatrix();
      matrix.setScale(interpolatedTime, interpolatedTime);
   }
}



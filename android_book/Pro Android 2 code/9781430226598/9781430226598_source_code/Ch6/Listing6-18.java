public class ViewAnimationListener
   implements Animation.AnimationListener 
{
   private ViewAnimationListener(){}

   public void onAnimationStart(Animation animation)
   {
      Log.d("Animation Example", "onAnimationStart");
   }

   public void onAnimationEnd(Animation animation)
   {
      Log.d("Animation Example", "onAnimationEnd");
   }

   public void onAnimationRepeat(Animation animation)
   {
      Log.d("Animation Example", "onAnimationRepeat");
   }
}


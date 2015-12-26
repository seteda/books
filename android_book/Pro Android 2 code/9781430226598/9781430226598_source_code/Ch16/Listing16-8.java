private VelocityTracker vTracker = null;

@Override
public boolean onTouchEvent(MotionEvent event) {
    int action = event.getAction();
    switch(action) {
        case MotionEvent.ACTION_DOWN:
            if(vTracker == null) {
                vTracker = VelocityTracker.obtain();
            }
            else {
                vTracker.clear();
            }
            vTracker.addMovement(event);
            break;
        case MotionEvent.ACTION_MOVE:
            vTracker.addMovement(event);
            vTracker.computeCurrentVelocity(1000);
            Log.v(TAG, "X velocity is " + vTracker.getXVelocity() + " pixels per second");
            Log.v(TAG, "Y velocity is " + vTracker.getYVelocity() + " pixels per second");
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            vTracker.recycle();
            break;
    }
    event.recycle();
    return true;
}


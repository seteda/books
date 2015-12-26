    int action = event.getAction();
    int ptrId = event.getPointerId(0);
    if(event.getPointerCount() > 1)
        ptrId = (action & MotionEvent.ACTION_POINTER_ID_MASK) >>>
                                      MotionEvent.ACTION_POINTER_ID_SHIFT;
    action = action & MotionEvent.ACTION_MASK;
    if(action < 7 && action > 4)
        action = action - 5;
    int ptrIndex = event.findPointerIndex(ptrId);


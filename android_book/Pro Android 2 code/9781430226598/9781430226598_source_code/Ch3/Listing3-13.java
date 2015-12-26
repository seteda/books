//Call getDrawable to get the image
BitmapDrawable d = 
  activity.getResources().getDrawable(R.drawable.sample_image);

//You can use the drawable then to set the background
button.setBackgroundDrawable(d);

//or you can set the background directly from the Resource Id
button.setBackgroundResource(R.drawable.icon);



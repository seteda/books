package apt.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class LunchList extends FragmentActivity
    implements LunchFragment.OnRestaurantListener {
  public final static String ID_EXTRA="apt.tutorial._ID";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    LunchFragment lunch
      =(LunchFragment)getSupportFragmentManager()
                            .findFragmentById(R.id.lunch);
    
    lunch.setOnRestaurantListener(this);
  }
  
  public void onRestaurantSelected(long id) {
    Intent i=new Intent(this, DetailForm.class);

    i.putExtra(ID_EXTRA, String.valueOf(id));
    startActivity(i);
  }
}
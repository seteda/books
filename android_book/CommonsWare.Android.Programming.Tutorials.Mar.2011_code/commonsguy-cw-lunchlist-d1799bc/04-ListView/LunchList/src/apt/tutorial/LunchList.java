package apt.tutorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import java.util.ArrayList;
import java.util.List;

public class LunchList extends Activity {
  List<Restaurant> model=new ArrayList<Restaurant>();
  ArrayAdapter<Restaurant> adapter=null;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    Button save=(Button)findViewById(R.id.save);
    
    save.setOnClickListener(onSave);
    
    ListView list=(ListView)findViewById(R.id.restaurants);
    
    adapter=new ArrayAdapter<Restaurant>(this,
                        android.R.layout.simple_list_item_1,
                        model);
    list.setAdapter(adapter);
  }
  
  private View.OnClickListener onSave=new View.OnClickListener() {
    public void onClick(View v) {
      Restaurant r=new Restaurant();
      EditText name=(EditText)findViewById(R.id.name);
      EditText address=(EditText)findViewById(R.id.addr);
      
      r.setName(name.getText().toString());
      r.setAddress(address.getText().toString());
      
      RadioGroup types=(RadioGroup)findViewById(R.id.types);
      
      switch (types.getCheckedRadioButtonId()) {
        case R.id.sit_down:
          r.setType("sit_down");
          break;
          
        case R.id.take_out:
          r.setType("take_out");
          break;
          
        case R.id.delivery:
          r.setType("delivery");
          break;
      }
      
      adapter.add(r);
    }
  };
}
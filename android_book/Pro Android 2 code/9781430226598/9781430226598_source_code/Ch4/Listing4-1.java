package pro.android;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
public class MainActivity extends Activity
{
    private LinearLayout nameContainer;

    private LinearLayout addressContainer;

    private LinearLayout parentContainer;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        createNameContainer();

        createAddressContainer();

        createParentContainer();

        setContentView(parentContainer);

    }

    private void createNameContainer()
    {
        nameContainer = new LinearLayout(this);

        nameContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        nameContainer.setOrientation(LinearLayout.HORIZONTAL);

        TextView nameLbl = new TextView(this);

        nameLbl.setText("Name: ");
        nameContainer.addView(nameLbl);

        TextView nameValueLbl = new TextView(this);
        nameValueLbl.setText("John Doe");

        nameContainer.addView(nameValueLbl);
    }

    private void createAddressContainer()
    {
        addressContainer = new LinearLayout(this);
    
        addressContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        addressContainer.setOrientation(LinearLayout.VERTICAL);

        TextView addrLbl = new TextView(this);

        addrLbl.setText("Address:");

        TextView addrValueLbl = new TextView(this);

        addrValueLbl.setText("911 Hollywood Blvd");

        addressContainer.addView(addrLbl);
        addressContainer.addView(addrValueLbl);

    }

    private void createParentContainer()
    {
        parentContainer = new LinearLayout(this);
    
        parentContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        parentContainer.setOrientation(LinearLayout.VERTICAL);
    
        parentContainer.addView(nameContainer);
        parentContainer.addView(addressContainer);
    }
}
package pro.android;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
public class MainActivity extends Activity
{
    private LinearLayout nameContainer;

    private LinearLayout addressContainer;

    private LinearLayout parentContainer;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        createNameContainer();

        createAddressContainer();

        createParentContainer();

        setContentView(parentContainer);

    }

    private void createNameContainer()
    {
        nameContainer = new LinearLayout(this);

        nameContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        nameContainer.setOrientation(LinearLayout.HORIZONTAL);

        TextView nameLbl = new TextView(this);

        nameLbl.setText("Name: ");
        nameContainer.addView(nameLbl);

        TextView nameValueLbl = new TextView(this);
        nameValueLbl.setText("John Doe");

        nameContainer.addView(nameValueLbl);
    }

    private void createAddressContainer()
    {
        addressContainer = new LinearLayout(this);
    
        addressContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        addressContainer.setOrientation(LinearLayout.VERTICAL);

        TextView addrLbl = new TextView(this);

        addrLbl.setText("Address:");

        TextView addrValueLbl = new TextView(this);

        addrValueLbl.setText("911 Hollywood Blvd");

        addressContainer.addView(addrLbl);
        addressContainer.addView(addrValueLbl);

    }

    private void createParentContainer()
    {
        parentContainer = new LinearLayout(this);
    
        parentContainer.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        parentContainer.setOrientation(LinearLayout.VERTICAL);
    
        parentContainer.addView(nameContainer);
        parentContainer.addView(addressContainer);
    }
}


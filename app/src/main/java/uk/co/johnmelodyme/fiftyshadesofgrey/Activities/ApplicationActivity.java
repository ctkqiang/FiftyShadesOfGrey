package uk.co.johnmelodyme.fiftyshadesofgrey.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import uk.co.johnmelodyme.fiftyshadesofgrey.Constants.MenuConstants;
import uk.co.johnmelodyme.fiftyshadesofgrey.CustomComponents.CustomMenuList;
import uk.co.johnmelodyme.fiftyshadesofgrey.R;

public class ApplicationActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";
    public ListView listView;

    public void renderComponents()
    {
        /* Set content view to activity */
        ApplicationActivity.this.getWindow().setContentView(R.layout.activity_main);

        /* Declaration of layout user components */
        listView = (ListView) findViewById(R.id.list_menu);

        /* Occupy list view with the constant data */
        CustomMenuList customMenuList = new CustomMenuList(
                ApplicationActivity.this,
                MenuConstants.id,
                MenuConstants.menuNames,
                MenuConstants.menuDescriptions,
                MenuConstants.imageId
        );

        /* Assign adapter to listView */
        this.listView.setAdapter(customMenuList);

        /* Assign onClick event for listView */
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                switch (MenuConstants.id[position])
                {
                    case 0:

                    case 1:
                    case 2:
                    default:
                        break;
                }

                Log.d(TAG, "... Navigate to " + MenuConstants.menuNames[position]);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Render Components */
        this.renderComponents();
    }
}
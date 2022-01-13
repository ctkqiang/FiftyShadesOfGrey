package uk.co.johnmelodyme.fiftyshadesofgrey.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import uk.co.johnmelodyme.fiftyshadesofgrey.Constants.MenuConstants;
import uk.co.johnmelodyme.fiftyshadesofgrey.CustomComponents.CustomMenuList;
import uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces.Services;
import uk.co.johnmelodyme.fiftyshadesofgrey.R;
import uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces.Utilities;

public class ApplicationActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";
    public AlertDialog.Builder builder;
    public Utilities utilities;
    public ListView listView;
    public ImageButton coffee;
    public Services services;

    public void setActionBar(Activity activity)
    {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.action_bar, null);

        TextView appBarTitle = (TextView) view.findViewById(R.id.action_title);
        appBarTitle.setText(this.getString(R.string.app_name));
    }

    public void renderComponents()
    {
        /* Get Utilities class */
        utilities = new Utilities(TAG);
        services = new Services(TAG, ApplicationActivity.this);

        /* Set custom action bar */
        this.setActionBar(ApplicationActivity.this);

        /* Set content view to activity */
        ApplicationActivity.this.getWindow().setContentView(R.layout.activity_main);

        /* Declaration of layout user components */
        builder = new AlertDialog.Builder(this);
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
                utilities.parseData(
                        ApplicationActivity.this,
                        SelectorActivity.class,
                        "id",
                        MenuConstants.id[position].toString()
                );

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }


    public void showPaymentBottomSheetDialogue()
    {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        bottomSheetDialog.setContentView(R.layout.buy_me_a_coffee_bottom);


        /* Buy Me a Coffee */
        this.coffee = (ImageButton) bottomSheetDialog.findViewById(R.id.buymecoffee);

        if (coffee != null)
        {
            this.coffee.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    services.openBuyMeCoffee(TAG);
                }
            });
        }

        bottomSheetDialog.show();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        this.startService(new Intent(this.getBaseContext(), Service.class));
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.about:
                builder.setMessage(R.string.about_body).setNegativeButton(
                        R.string.donate,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ApplicationActivity.this.showPaymentBottomSheetDialogue();
                            }
                        }
                ).setPositiveButton(
                        R.string.ok,
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                /* Do Nothing */
                            }
                        }
                );

                AlertDialog alert = builder.create();
                alert.setTitle(R.string.about_dev_msg);
                alert.show();

                Log.d(TAG, "onOptionsItemSelected: ... about selected");
                break;

            case R.id.report:
            case R.id.support:
            default:
                break;

            /* TODO add */
        }

        return (super.onOptionsItemSelected(item));
    }

}
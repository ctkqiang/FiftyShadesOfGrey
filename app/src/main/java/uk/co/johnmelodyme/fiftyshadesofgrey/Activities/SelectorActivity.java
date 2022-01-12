package uk.co.johnmelodyme.fiftyshadesofgrey.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import uk.co.johnmelodyme.fiftyshadesofgrey.Constants.BooksMenu;
import uk.co.johnmelodyme.fiftyshadesofgrey.Constants.MoviesMenu;
import uk.co.johnmelodyme.fiftyshadesofgrey.CustomComponents.EbookMenuList;
import uk.co.johnmelodyme.fiftyshadesofgrey.CustomComponents.MoviesMenuList;
import uk.co.johnmelodyme.fiftyshadesofgrey.R;
import uk.co.johnmelodyme.fiftyshadesofgrey.Interfaces.Utilities;

public class SelectorActivity extends AppCompatActivity
{
    private static final String TAG = "50ShadesOfGrey";
    public ListView listView;
    public Utilities utilities;

    public int getPageId(Activity activity)
    {
        /* Get Utilities class */
        utilities = new Utilities(TAG);

        return Integer.parseInt(utilities.getParsedData(activity, "id"));
    }

    public void renderComponents()
    {
        /* Set content view to activity */
        SelectorActivity.this.getWindow().setContentView(R.layout.activity_selector);

        /* Declaration of layout user components */
        listView = (ListView) findViewById(R.id.selector_listview);

        switch (this.getPageId(SelectorActivity.this))
        {
            case 0:

                /* Occupy list view with the constant data */
                EbookMenuList ebookMenuList = new EbookMenuList(
                        SelectorActivity.this,
                        BooksMenu.id,
                        BooksMenu.bookCover,
                        BooksMenu.file,
                        BooksMenu.bookTitle,
                        BooksMenu.bookDescription
                );

                /* Assign adapter to listView */
                this.listView.setAdapter(ebookMenuList);

                /* Assign onClick event for listView */
                this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                            long l)
                    {
                        utilities.parseData(
                                SelectorActivity.this,
                                BookActivity.class,
                                "book_asset",
                                String.valueOf(BooksMenu.file[position])
                        );

                        Log.d(TAG, "... opening ebook at " + position);
                    }
                });

                break;

            case 1:
                /* Occupy list view with the constant data */
                MoviesMenuList moviesMenuList = new MoviesMenuList(
                        SelectorActivity.this,
                        MoviesMenu.id,
                        MoviesMenu.name,
                        MoviesMenu.url,
                        MoviesMenu.cover
                );

                /* Assign adapter to listView */
                this.listView.setAdapter(moviesMenuList);

                /* Assign onClick event for listView */
                this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                            long l)
                    {
                        utilities.parseData(
                                SelectorActivity.this,
                                VideoActivity.class,
                                "movie_asset",
                                String.valueOf(MoviesMenu.url[position])
                        );


                        Log.d(TAG, "... opening movies at " + position);
                    }
                });

                break;

            case 2: /*TODO add this */
            case 3: /*TODO add this */
            default:
                break;
        }

        Log.d(TAG, "renderComponents: item number " + this.getPageId(SelectorActivity.this));
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        this.getPageId(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.renderComponents();
    }
}
package uk.co.johnmelodyme.fiftyshadesofgrey.CustomComponents;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import uk.co.johnmelodyme.fiftyshadesofgrey.R;

public class MoviesMenuList extends ArrayAdapter
{
    public Activity activity;
    public Integer[] id;
    public String[] moviesName;
    public String[] moviesUrl;
    public Integer[] moviesCover;

    public MoviesMenuList(Activity activity, Integer[] id, String[] moviesName,
                          String[] moviesUrl, Integer[] moviesCover)
    {
        super(activity, R.layout.list_view_item, moviesName);
        this.activity = activity;
        this.id = id;
        this.moviesName = moviesName;
        this.moviesUrl = moviesUrl;
        this.moviesCover = moviesCover;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater layoutInflater = this.activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.list_view_item, null, true);
        }

        ImageView imageItem = (ImageView) convertView.findViewById(R.id.item_image);
        TextView itemTitle = (TextView) convertView.findViewById(R.id.item_title);
        TextView itemDescription = (TextView) convertView.findViewById(R.id.item_description);

        imageItem.setImageResource(moviesCover[position]);
        itemTitle.setText(moviesName[position]);
        itemDescription.setText(null);

        return convertView;
    }
}

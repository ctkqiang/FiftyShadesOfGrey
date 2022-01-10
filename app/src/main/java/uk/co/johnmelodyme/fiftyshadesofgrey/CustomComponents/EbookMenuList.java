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

public class EbookMenuList extends ArrayAdapter
{
    public Activity activity;
    public Integer[] id;
    public Integer[] cover;
    public String[] file;
    public String[] bookName;

    public String[] bookDescription;

    public EbookMenuList(Activity activity, Integer[] id, Integer[] cover, String[] file,
                         String[] bookName,
                         String[] bookDescription)
    {
        super(activity, R.layout.list_view_item, bookName);
        this.activity = activity;
        this.id = id;
        this.cover = cover;
        this.file = file;
        this.bookName = bookName;
        this.bookDescription = bookDescription;
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

        imageItem.setImageResource(cover[position]);
        itemTitle.setText(bookName[position]);
        itemDescription.setText(bookDescription[position]);

        return convertView;
    }
}

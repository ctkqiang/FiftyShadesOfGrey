package uk.co.johnmelodyme.fiftyshadesofgrey.CustomComponents;

import android.annotation.SuppressLint;
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

public class CustomMenuList extends ArrayAdapter
{
    public Integer[] id;
    public String[] menuName;
    public String[] menuDescription;
    public Integer[] imageId;
    public Activity activity;

    public CustomMenuList(Activity activity, Integer[] id, String[] menuName, String[] menuDescription,
                          Integer[] imageId)
    {
        super(activity, R.layout.list_view_item, menuName);
        this.activity = activity;
        this.id = id;
        this.menuName = menuName;
        this.menuDescription = menuDescription;
        this.imageId = imageId;
    }

    @SuppressLint("InflateParams")
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

        imageItem.setImageResource(imageId[position]);
        itemTitle.setText(menuName[position]);
        itemDescription.setText(menuDescription[position]);

        return convertView;
    }
}

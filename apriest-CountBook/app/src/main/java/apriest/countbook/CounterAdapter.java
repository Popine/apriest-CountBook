package apriest.countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 2017-09-30.
 */

public class CounterAdapter extends BaseAdapter implements ListAdapter {    //custom ArrayAdapter for list entries

    private ArrayList<Counter> list = new ArrayList<Counter>();
    private Context context;

    public CounterAdapter(ArrayList<Counter> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }


    //https://stackoverflow.com/questions/17525886/listview-with-add-and-delete-buttons-in-each-row-in-android
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.counter_view, null);
        }

        TextView listItemText = (TextView) view.findViewById(R.id.textSummary); //initialize views
        listItemText.setText(list.get(position).toString());
        ImageButton incrementButton = (ImageButton) view.findViewById(R.id.incrementButton);
        ImageButton decrementButton = (ImageButton) view.findViewById(R.id.decrementButton);
        ImageButton resetButton = (ImageButton) view.findViewById(R.id.resetButton);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {    //Long click to edit counter
                Intent intent = new Intent(v.getContext(), EditCounterActivity.class);
                intent.putExtra("counterTag", (Parcelable) list.get(position));
                intent.putExtra("position", position);
                (((Activity) context)).startActivityForResult(intent, 1);   //Sends intent to EditCounterActivity
                return true;
            }

        });

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).increment();
                notifyDataSetChanged();
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).decrement();
                notifyDataSetChanged();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).resetValue();
                notifyDataSetChanged();
            }
        });

        return view;
    }


}

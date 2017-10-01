package apriest.countbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Counter> countBook;
    private CounterAdapter adapter;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countBook = new ArrayList<>();
        adapter = new CounterAdapter(countBook, this);
        fileName = "counterData";

        try {   //get saved data from previous sessions
            FileInputStream fis = openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            while (true) {
                try {
                    countBook.add((Counter) is.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        final FloatingActionButton addCounterButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        addCounterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {   //button to create counters
                countBook.add(new Counter());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {    //receives result from edit, either edits or deltes
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Counter theCounter = data.getParcelableExtra("counterKey");
                int position = data.getIntExtra("position", 0);
                countBook.set(position, theCounter);
                adapter.notifyDataSetChanged();

            } else if (resultCode == 3) {
                int position = data.getIntExtra("position", 0);
                countBook.remove(position);
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void onPause() { //saves data for future sessions
        super.onPause();
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            for (Counter aCounter : countBook) {
                os.writeObject(aCounter);
            }
            os.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
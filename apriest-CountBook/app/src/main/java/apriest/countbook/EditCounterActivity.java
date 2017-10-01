package apriest.countbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditCounterActivity extends AppCompatActivity {

    private Counter theCounter;
    private int position;
    private EditText nameText;
    private EditText commentText;
    private EditText initialValueText;
    private EditText currentValueText;
    private Button saveButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        theCounter = getIntent().getParcelableExtra("counterTag");  //initialize views and counter to be edited
        position = getIntent().getIntExtra("position", 0);
        nameText = (EditText) findViewById(R.id.nameText);
        commentText = (EditText) findViewById(R.id.commentText);
        initialValueText = (EditText) findViewById(R.id.initialValueText);
        currentValueText = (EditText) findViewById(R.id.currentValueText);
        saveButton = (Button) findViewById(R.id.saveButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        nameText.setText(theCounter.getName(), TextView.BufferType.EDITABLE);
        commentText.setText(theCounter.getComment(), TextView.BufferType.EDITABLE);
        initialValueText.setText(String.valueOf(theCounter.getInitialValue()), TextView.BufferType.EDITABLE);
        currentValueText.setText(String.valueOf(theCounter.getCurrentValue()), TextView.BufferType.EDITABLE);

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {   //saves changes by returning result of intent back to main
                theCounter.setInitialValue(Integer.parseInt(initialValueText.getText().toString()));
                theCounter.setName(nameText.getText().toString());
                theCounter.setComment(commentText.getText().toString());
                theCounter.setCurrentValue(Integer.parseInt(currentValueText.getText().toString()));

                Intent intent = new Intent();
                intent.putExtra("counterKey", (Parcelable) theCounter);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);
                finish();
                ((Activity) v.getContext()).onBackPressed();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {   //deletes counter by sending result back to main
                Intent intent = new Intent();
                intent.putExtra("position", position);
                setResult(3, intent);   //3 is the int for deletion
                finish();
                ((Activity) v.getContext()).onBackPressed();
            }
        });
    }

}

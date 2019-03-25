package pl.piotrdawidziuk.simplenotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    String note = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.datetime:
                Toast.makeText(this, "MENU CLICKED", Toast.LENGTH_SHORT).show();

                return true;

            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        note = getSavedText(getApplicationContext(),"note");
        editText.setText(note);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                note = editText.getText().toString();
                Log.i("Text:", note);
                saveText(getApplicationContext(), "note",note);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public static boolean saveText(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences("note", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getSavedText(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences("note", Context.MODE_PRIVATE);
        return settings.getString(key, "Write here");
    }
}

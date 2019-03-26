package pl.piotrdawidziuk.simplenotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    String note = "";
    String dateAndTimeString = "";

    Date date;

    AlertDialog.Builder builder;
    AlertDialog dialog;

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
                date = new Date();
                dateAndTimeString = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss").format(date);
                editText.append("\n"+dateAndTimeString+"\n");
                return true;

            case R.id.clear:

                builder = new AlertDialog.Builder(this);
                builder.setTitle("Clear");
                builder.setMessage("Do you want to clear all?");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                editText.setText("");
                                Toast.makeText(MainActivity.this, "Notes cleared", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                builder.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
                return true;

                case R.id.exit:
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
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

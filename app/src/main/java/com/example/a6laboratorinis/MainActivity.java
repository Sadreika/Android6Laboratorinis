package com.example.a6laboratorinis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView text, count, symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        count = findViewById(R.id.count);
        symbol = findViewById(R.id.symbols);
        registerForContextMenu(text);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.option1:
                String tekstas = "Tekste yra "+ text.getText().toString().length()+" simboliai";
                showAlertDialog(tekstas);
                count.setText(tekstas);
                return true;
            case R.id.option2:
                runthread();
                return true;
            default:    return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.exit:
                MainActivity.this.finish();
                System.exit(0);
                return true;
            case R.id.difference:
                openTimePicker();
            default:    return super.onOptionsItemSelected(item);
        }

    }

    private void runthread() {
        final String textViewText = text.getText().toString();
        final char[] chars = textViewText.toCharArray();

        final Handler handler = new Handler();
        handler.post(new Runnable() {

            int i = 0;
            @Override
            public void run() {
                symbol.setText("" + chars[i]);
                i++;
                if (i == chars.length) {
                    handler.removeCallbacks(this);
                } else {
                    handler.postDelayed(this, 800);
                }
            }
        });
    }

    private void openTimePicker(){
        new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                int chosen_time = hourOfDay * 60 + minute;
                int currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 60 + Calendar.getInstance().get(Calendar.MINUTE);
                int difference = currentTime - chosen_time;
                if(difference < 0){
                    difference *= (-1);
                }

                showAlertDialog("Skirtumas tarp dabartinio ir nurodyto laiko yra " + difference + " minutes");

                text.setText("Skirtumas tarp dabartinio ir nurodyto laiko yra " + difference + " minutes");
            }
        },0,0,true).show();
    }

    private void showAlertDialog(String message){
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

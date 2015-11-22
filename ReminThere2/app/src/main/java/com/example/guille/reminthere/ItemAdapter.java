package com.example.guille.reminthere;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Created by Guille on 18/11/15.
 */
public class ItemAdapter extends ArrayAdapter<Reminder> {

    TextView reminder_message;
    CheckBox checked;
    public ItemAdapter(Context context, int layout_resource, ArrayList<Reminder> data) {
        super(context, layout_resource, R.id.reminder_message, data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);
        Reminder item = getItem(position);

        // Obtenim referències a les parts de l'item de la llista
        // a la posició 'position'
        reminder_message = (TextView) convertView.findViewById(R.id.reminder_message);
        checked   = (CheckBox) convertView.findViewById(R.id.checked);

        checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem(position).setCheckState(checked.isChecked());
            }
        });

        reminder_message.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Start nueva activity
                Intent intent = new Intent(getContext(),Activity_Details.class);
                getContext().startActivity(intent);
            }
        });

        // Transferim dades del item al view que sortirà a la llista
        reminder_message.setText(item.getMessage());
        checked.setChecked(item.isChecked());

        return convertView;
    }
}
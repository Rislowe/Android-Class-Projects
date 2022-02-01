package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ToDoListManager listManager;
    private ToDoItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is the list view.
        ListView todoList = findViewById(R.id.todo_list);
        ImageButton addButton = findViewById(R.id.add_item);

        listManager = new ToDoListManager(this);
        adapter = new ToDoItemAdapter(this, listManager.getItems());
        todoList.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onAddButtonClick();
            }
        });
    }

    private void onAddButtonClick()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_item);

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToDoItem item = new ToDoItem(
                                input.getText().toString(),
                                false
                        );

                        listManager.addItem(item);
                        adapter.updateItems(listManager.getItems());
                    }
                });

        builder.show();
    };

    private class ToDoItemAdapter extends ArrayAdapter<ToDoItem>
    {
        private Context context;
        private List<ToDoItem> items;


        private ToDoItemAdapter(Context context, List<ToDoItem> items)
        {
            super(context, -1, listManager.getItems());

            this.context = context;
            this.items = items;
        }

        // this function updates app to new information input in database
        public void updateItems(List<ToDoItem> items)
        {
            this.items = items;
            notifyDataSetChanged(); //notifies app that Data has changed
        }

        //Helps app realize it needs to repopulate items
        @Override
        public int getCount()
        {
            return items.size();
        }

        //This is an override of a function of the Adapter Interface
        //If it looks like it handles one row, that is because it does handle
        //one and only one row. The ListView, probably calls this a bunch of times according to the
        //amount of items you pass it on creation. item is determined by int position.
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            /*View Holder Pattern
            * This pattern recycles row items so that the list does not have to call the views
            * every time something is added     */

            final ItemViewHolder holder;

            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.to_do_item_layout,
                        parent,
                        false);

                holder = new ItemViewHolder();
                holder.itemDescription = convertView.findViewById(R.id.itemTextView);
                holder.itemState = convertView.findViewById(R.id.completedCheckBox);
                holder.itemDate = convertView.findViewById(R.id.timestampTextView);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ItemViewHolder)  convertView.getTag();
            }

            //Try and make date relative String
            long milliseconds = items.get(position).getDate().getTime();
            long now = new Date().getTime();
            String relativeDate = DateUtils.getRelativeTimeSpanString(
                    milliseconds,
                    now,
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE).toString();


            //Set up the text for each row
            holder.itemDescription.setText(items.get(position).getDescription());
            holder.itemState.setChecked(items.get(position).isComplete());
            holder.itemDate.setText(relativeDate);

            //Sets Tags for the checkbox on the item
            holder.itemState.setTag(items.get(position));

            //Image button for Deleting a row.
            ImageButton deleteButton = convertView.findViewById(R.id.deleteButton);


            /*
            This function is an onClickListener
            It is used to toggle the checkbox and update the item in the database
            */
            View.OnClickListener onClickListener = new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    //get item
                    ToDoItem item = (ToDoItem) holder.itemState.getTag();

                    item.toggleComplete();

                    listManager.updateItem(item);

                    notifyDataSetChanged();
                }
            };

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteButtonClick(position);
                    notifyDataSetChanged();
                }
            });


            //These lines set the row and the checkbox to activate
            // our saved onClickListener function
            convertView.setOnClickListener(onClickListener);
            holder.itemState.setOnClickListener(onClickListener);

            return convertView;
        }

        //delete button onClick function
        private void onDeleteButtonClick(int position)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.delete_item);

            TextView question = new TextView(context);

            question.setText(R.string.confirm_delete);

            builder.setView(question);

            builder.setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listManager.removeItem(items.get(position));
                            updateItems(listManager.getItems());
                        }
                    });

            builder.show();
        }
    }

    //This class is created to hold the view information
    public static class ItemViewHolder
    {
        public TextView itemDescription;
        public CheckBox itemState;
        public TextView itemDate;
    }
}
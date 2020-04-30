package com.example.mycontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontacts.Data.DatabaseHandler;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<Contact> mData;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    public MyAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.layout, parent, false);
        return new MyViewHolder(view,mContext);
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.idd.setText(( mData.get(position).getId()));
        holder.nam.setText(mData.get(position).getName());
        holder.address.setText(mData.get(position).getPhone());

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView nam,idd;
        TextView address;
        Button b1,b2;

        LinearLayout layout;

        public MyViewHolder(View itemView, Context ctx) {



            super(itemView);
            mContext=ctx;
            idd=(TextView)itemView.findViewById(R.id.text);
            nam= (TextView) itemView.findViewById(R.id.text1);
            address = (TextView) itemView.findViewById(R.id.phone);
            layout= (LinearLayout) itemView.findViewById(R.id.linear);
            b1=itemView.findViewById(R.id.update);
            b2=itemView.findViewById(R.id.delete);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Contact contact=mData.get(position);
                    updateitem(contact);
                }
            });

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 int position=getAdapterPosition();
                 Contact contact=mData.get(position);
                 deleteitem(contact.getId());

                }
            });

        }

        private void updateitem(final Contact contact) {

            alertDialogBuilder = new AlertDialog.Builder(mContext);

            inflater = LayoutInflater.from(mContext);
            final View view = inflater.inflate(R.layout.popup, null);

            final EditText groceryItem = (EditText) view.findViewById(R.id.nam);
            final EditText quantity = (EditText) view.findViewById(R.id.ph);

            Button saveButton = (Button) view.findViewById(R.id.saveButton);


            alertDialogBuilder.setView(view);
            dialog = alertDialogBuilder.create();
            dialog.show();
             saveButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     DatabaseHandler db = new DatabaseHandler(mContext);
                    contact.setName(groceryItem.getText().toString());
                    contact.setPhone(quantity.getText().toString());
                     if (!groceryItem.getText().toString().isEmpty()
                             && !quantity.getText().toString().isEmpty())
                     {

                        db.updatecontact(contact);
                        notifyItemChanged(getAdapterPosition(),contact);
                         dialog.dismiss();
                     }

                 }
             });


        }

        private void deleteitem(String id) {
            DatabaseHandler db = new DatabaseHandler(mContext);
            db.deleteContact(id);
           mData.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());



        }
    }



}
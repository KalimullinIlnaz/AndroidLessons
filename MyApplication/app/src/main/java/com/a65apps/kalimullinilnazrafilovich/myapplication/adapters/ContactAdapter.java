package com.a65apps.kalimullinilnazrafilovich.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces.ItemAdapterClickListener;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter {
    private ArrayList<Contact> contacts;
    private ItemAdapterClickListener itemAdapterClickListener;

    public void setData(ArrayList<Contact> contacts){
        this.contacts = contacts;;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(view,contacts,itemAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ContactViewHolder)holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setClickListener(ItemAdapterClickListener itemAdapterClickListener){
        this.itemAdapterClickListener = itemAdapterClickListener;
    }

    private static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name;
        private TextView telephoneNumber;
        private ArrayList<Contact> contacts;
        private ItemAdapterClickListener itemAdapterClickListener;

        ContactViewHolder(View itemView, ArrayList<Contact> contacts,ItemAdapterClickListener itemAdapterClickListener){
            super(itemView);
            this.contacts = contacts;
            this.itemAdapterClickListener = itemAdapterClickListener;

            name = (TextView) itemView.findViewById(R.id.namePerson);
            telephoneNumber = (TextView) itemView.findViewById(R.id.telephoneNumberPerson);

            itemView.setOnClickListener(this);
        }

        void bindView(int position){
            name.setText(contacts.get(position).getName());
            telephoneNumber.setText(contacts.get(position).getTelephoneNumber());
        }
        private  String TAG = "111";

        @Override
        public void onClick(View v) {
            if (itemAdapterClickListener != null){
                if (getAdapterPosition() != RecyclerView.NO_POSITION){
                    itemAdapterClickListener.onClick(v,getAdapterPosition());
                }
            }
        }
    }
}


package com.a65apps.kalimullinilnazrafilovich.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.interfaces.ItemAdapterClickListener;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.ArrayList;

public class ContactAdapter extends ListAdapter<Contact, ContactAdapter.ContactViewHolder> {
    private ArrayList<Contact> contacts;
    private ItemAdapterClickListener itemAdapterClickListener;

    public ContactAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final  DiffUtil.ItemCallback<Contact> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Contact>() {

                @Override
                public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
                    return oldItem.getTelephoneNumber().equals(newItem.getTelephoneNumber())
                            && oldItem.getTelephoneNumber2().equals(newItem.getTelephoneNumber2())
                            && oldItem.getEmail().equals(newItem.getEmail())
                            && oldItem.getEmail2().equals(newItem.getEmail2());
                }
            };

    public void setData(ArrayList<Contact> contacts){
        /*При обновлении списка, данные во viewHolder не обновляются, и выводится
        *имя и телефон контакта, который первый в списке, но при нажатии( то есть
        * внутри находится тот самый элемент, который должен быть по поиску)
        * как обновлять эти данные?
        */
        this.contacts = contacts;
        submitList(contacts);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(view,contacts,itemAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ((ContactViewHolder)holder).bindView(position);
    }

    public void setClickListener(ItemAdapterClickListener itemAdapterClickListener){
        this.itemAdapterClickListener = itemAdapterClickListener;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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


package com.a65apps.kalimullinilnazrafilovich.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.a65apps.kalimullinilnazrafilovich.myapplication.Contact;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;

import java.util.ArrayList;

public class ContactAdapter extends ListAdapter<Contact, ContactAdapter.ContactViewHolder> {
    private onContactListener onContactListener;

    public ContactAdapter(onContactListener onContactListener) {
        super(DIFF_CALLBACK);
        this.onContactListener = onContactListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item,parent,false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view,onContactListener);
        return contactViewHolder;
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
                            && oldItem.getName().equals(newItem.getName());
                }
            };

    public void setData(ArrayList<Contact> contacts){
        submitList(contacts);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bindView(getItem(position));
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView telephoneNumber;
        private onContactListener onContactListener;

        public ContactViewHolder(View itemView,onContactListener onContactListener){
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.namePerson);
            telephoneNumber = (TextView) itemView.findViewById(R.id.telephoneNumberPerson);
            this.onContactListener = onContactListener;

            itemView.setOnClickListener(this);
        }

        public void bindView(Contact contact){
            name.setText(contact.getName());
            telephoneNumber.setText(contact.getTelephoneNumber());
        }

        @Override
        public void onClick(View v) {
            onContactListener.onContactClick(getAdapterPosition());
        }
    }

    public interface onContactListener{
        void onContactClick(int position);
    }


}


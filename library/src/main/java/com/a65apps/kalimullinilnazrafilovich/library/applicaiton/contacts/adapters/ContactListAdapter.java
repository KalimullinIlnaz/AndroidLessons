package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.contacts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactListAdapter extends ListAdapter<ContactShortInfo, ContactListAdapter.ContactListItemViewHolder> {
    public static final DiffUtil.ItemCallback<ContactShortInfo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ContactShortInfo>() {
                @Override
                public boolean areItemsTheSame(@NonNull ContactShortInfo oldItem,
                                               @NonNull ContactShortInfo newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull ContactShortInfo oldItem,
                                                  @NonNull ContactShortInfo newItem) {
                    return oldItem.getTelephoneNumber().equals(newItem.getTelephoneNumber())
                            && oldItem.getName().equals(newItem.getName());
                }
            };

    private final OnContactListener onContactListener;

    public ContactListAdapter(@NonNull OnContactListener onContactListener) {
        super(DIFF_CALLBACK);
        this.onContactListener = onContactListener;
    }

    @NonNull
    @Override
    public ContactListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new ContactListItemViewHolder(view, onContactListener);
    }

    public void setData(@NonNull List<ContactShortInfo> contactShortInfoList) {
        submitList(contactShortInfoList);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListItemViewHolder holder, int position) {
        holder.bindView(getItem(position));
    }

    public interface OnContactListener {
        void onContactClick(int position);
    }

    public static class ContactListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnContactListener onContactListener;
        @BindView(R2.id.namePerson)
        TextView name;
        @BindView(R2.id.telephoneNumberPerson)
        TextView telephoneNumber;

        public ContactListItemViewHolder(@NonNull View itemView,
                                         @NonNull OnContactListener onContactListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.onContactListener = onContactListener;
            itemView.setOnClickListener(this);
        }

        public void bindView(@NonNull ContactShortInfo contactShortInfo) {
            name.setText(contactShortInfo.getName());
            telephoneNumber.setText(contactShortInfo.getTelephoneNumber());
        }

        @Override
        public void onClick(@NonNull View v) {
            if (onContactListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                onContactListener.onContactClick(getAdapterPosition());
            }
        }


    }
}

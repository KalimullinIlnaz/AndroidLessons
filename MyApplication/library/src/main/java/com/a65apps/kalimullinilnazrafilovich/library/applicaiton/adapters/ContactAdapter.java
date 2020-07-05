package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.adapters;

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

public class ContactAdapter extends ListAdapter<ContactShortInfo, ContactAdapter.ContactViewHolder> {
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

    private final transient OnContactListener onContactListener;

    public ContactAdapter(@NonNull OnContactListener onContactListener) {
        super(DIFF_CALLBACK);
        this.onContactListener = onContactListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view, onContactListener);
    }

    public void setData(@NonNull List<ContactShortInfo> contactShortInfoList) {
        submitList(contactShortInfoList);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bindView(getItem(position));
    }

    public interface OnContactListener {
        void onContactClick(int position);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final transient OnContactListener onContactListener;
        @BindView(R2.id.namePerson)
        transient TextView name;
        @BindView(R2.id.telephoneNumberPerson)
        transient TextView telephoneNumber;

        public ContactViewHolder(@NonNull View itemView,
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

package com.a65apps.kalimullinilnazrafilovich.library.applicaiton.delegate.contacts;

import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.a65apps.kalimullinilnazrafilovich.entities.ContactShortInfo;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.adapters.ContactListAdapter;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.adapters.ItemDecoration;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments.ContactDetailsFragment;
import com.a65apps.kalimullinilnazrafilovich.library.applicaiton.fragments.RouteMapFragment;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R;
import com.a65apps.kalimullinilnazrafilovich.myapplication.R2;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ViewsContactsDelegate implements ContactListAdapter.OnContactListener {
    private static final int OFFSET_DP = 6;

    private final Unbinder unbinder;
    private final FragmentActivity fragmentActivity;

    @BindView(R2.id.contact_list_recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.circular_progress_view)
    CircularProgressView circularProgressView;

    private ContactListAdapter contactListAdapter;
    private List<ContactShortInfo> contactShortInfoList;


    public ViewsContactsDelegate(@NonNull View view,
                                 @NonNull FragmentActivity fragmentActivity
    ) {
        this.fragmentActivity = fragmentActivity;

        unbinder = ButterKnife.bind(this, view);
    }

    public void initRecyclerView() {
        recyclerView.addItemDecoration(
                new ItemDecoration(dpToPx(OFFSET_DP), dpToPx(OFFSET_DP), dpToPx(OFFSET_DP), dpToPx(OFFSET_DP)));

        contactListAdapter = new ContactListAdapter(this);
        recyclerView.setAdapter(contactListAdapter);
    }

    public void showContacts(@NonNull List<ContactShortInfo> contactShortInfoList) {
        this.contactShortInfoList = contactShortInfoList;

        contactListAdapter.setData(contactShortInfoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(fragmentActivity);
        recyclerView.setLayoutManager(layoutManager);
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = Objects.requireNonNull(fragmentActivity).getResources().getDisplayMetrics();
        return (int) (dp * displayMetrics.density);
    }

    public void showLoading() {
        circularProgressView.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        circularProgressView.setVisibility(View.GONE);
    }

    @Override
    public void onContactClick(int position) {
        ContactDetailsFragment contactDetailsFragment = ContactDetailsFragment.
                newInstance(contactShortInfoList.get(position).getId());
        FragmentManager fragmentManager = Objects.requireNonNull(fragmentActivity).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, contactDetailsFragment).addToBackStack(null).commit();
    }

    public void openRouteMapFragment() {
        RouteMapFragment routeMapFragment = new RouteMapFragment();
        FragmentManager fragmentManager = Objects.requireNonNull(fragmentActivity).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, routeMapFragment).addToBackStack(null).commit();
    }

    public void unBind() {
        contactListAdapter = null;
        unbinder.unbind();
    }
}

package com.scottbishop.HotOffTheDialer;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scottbishop.HotOffTheDialer.commons.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author scott.bishop
 * @since 3/19/15
 */
public class RecentContactsFragment extends Fragment {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.add_button)
    View addButton;

    public static RecentContactsFragment newInstance() {
        return new RecentContactsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setupRecentContactsListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_contacts, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupRecentContactsListView();
    }

    private void setupRecentContactsListView() {
        RecentContactsAdapter adapter = new RecentContactsAdapter(getActivity(), getRecentContacts());
        recyclerView.setAdapter(adapter);
    }

    public List<ContactInfo> getRecentContacts() {
        Map<String, ContactInfo> contactMap = new LinkedHashMap<String, ContactInfo>();

        Cursor cursor = getActivity().getContentResolver()
                                     .query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor != null && cursor.moveToNext()) {
            String timeStampStr = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP));
            if (timeStampStr != null && !timeStampStr.isEmpty()) {
                // Exclude google contacts etc. by checking to see if there is a number
                if ("1".equals(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String contactName = cursor.getString(
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Uri photoUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
                                                              Long.parseLong(id));

                    ContactInfo contactInfo = new ContactInfo(id, contactName, photoUri);
                    // Remove duplicates
                    if (!contactMap.containsKey(contactName)) {
                        contactMap.put(contactName, contactInfo);
                    }
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        ArrayList<ContactInfo> contactList = new ArrayList<ContactInfo>(contactMap.values());
        Collections.reverse(contactList);
        return contactList;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == -1) {
            getActivity().onBackPressed();
        }
    }

    @OnClick(R.id.add_button)
    public void onAddButtonClicked() {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        startActivityForResult(intent, -1);
    }

}

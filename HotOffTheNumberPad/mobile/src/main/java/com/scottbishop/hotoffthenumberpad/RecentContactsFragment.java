package com.scottbishop.hotoffthenumberpad;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author scott.bishop
 * @since 3/19/15
 */
public class RecentContactsFragment extends Fragment {

    @InjectView(R.id.list_view)
    ListView listView;

    public static RecentContactsFragment newInstance() {
        return new RecentContactsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecentContactsListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recent_contacts, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    private void setupRecentContactsListView() {
        RecentContactsAdapter adapter = new RecentContactsAdapter();
        adapter.setContacts(getRecentContacts());
        listView.setAdapter(adapter);
    }

    public Map<String, String> getRecentContacts() {
        HashMap<String, String> contactMap = new HashMap<>();

        Uri queryUri = android.provider.CallLog.Calls.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.DATE
        };

        String sortOrder = String.format("%s limit 500 ", CallLog.Calls.DATE + " DESC");

        Cursor cursor = getActivity().getContentResolver().query(queryUri, projection, null, null, sortOrder);

        while (cursor.moveToNext()) {
            String phoneNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));

            String title = (cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));

            if (phoneNumber == null || title == null) {
                continue;
            }

            String uri = "tel:" + phoneNumber;
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            String intentUriString = intent.toUri(0);

            contactMap.put(title, intentUriString);

        }
        cursor.close();
        return contactMap;
    }
}

package com.scottbishop.hotoffthenumberpad;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Map;

/**
 * @author scott.bishop
 * @since 3/19/15
 */
public class RecentContactsAdapter extends BaseAdapter {

    private Map<String, String> contacts;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public void setContacts(Map<String, String> contacts) {
        this.contacts = contacts;
    }
}

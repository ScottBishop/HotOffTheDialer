package com.scottbishop.hotoffthedialer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author scott.bishop
 * @since 3/19/15
 */
public class RecentContactsAdapter extends RecyclerView.Adapter<RecentContactsAdapter.ViewHolder> {

    private List<ContactInfo> contacts;
    private Context context;

    public RecentContactsAdapter(Context context, List<ContactInfo> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public View view;
        public TextView nameTextView;
        public ImageView photoImageView;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            this.nameTextView = ButterKnife.findById(view, R.id.name);
            this.photoImageView = ButterKnife.findById(view, R.id.image);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecentContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ContactInfo item = contacts.get(position);
        holder.nameTextView.setText(item.getName());

        InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), item.getPhotoUri());
        BufferedInputStream buf = new BufferedInputStream(photo_stream);
        Bitmap bitmap = BitmapFactory.decodeStream(buf);
        if (bitmap != null) {
            holder.photoImageView.setImageBitmap(bitmap);
        } else {
            holder.photoImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(item.getId()));
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contacts.size();
    }

}

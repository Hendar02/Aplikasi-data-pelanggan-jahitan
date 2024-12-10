package com.abrorrahmad.perpustakaanapp.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.abrorrahmad.perpustakaanapp.R;

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater ly;
    private SparseBooleanArray mSelectedItems;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        ly = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSelectedItems = new SparseBooleanArray();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = ly.inflate(R.layout.row_data, viewGroup, false);
        MyHolder holder = new MyHolder();

        holder.ListAntrian = (TextView)v.findViewById(R.id.listAntrian);
        holder.ListNama = (TextView)v.findViewById(R.id.listNama);
        holder.ListDiproses = (TextView)v.findViewById(R.id.listTglProses);
        holder.ListStatus = (TextView)v.findViewById(R.id.listStatus);

        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListAntrian.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_id)));
        holder.ListNama.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_nama)));
        holder.ListDiproses.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_tglMasuk)) +
                " - " + cursor.getString(cursor.getColumnIndex(DBHelper.row_tglSelesai)));
        holder.ListStatus.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_status)));



    }

    class MyHolder {
        TextView ListAntrian;
        TextView ListNama;
        TextView ListDiproses;
        TextView ListStatus;
    }
}

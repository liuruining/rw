package com.liuruining.rw.learn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuruining.model.dao.Book;
import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseListAdapter;

import java.util.List;

/**
 * Created by nielongyu on 15/3/9.
 */
public class BooksAdapter extends BaseListAdapter<Book> {
    private Context mContext;

    public BooksAdapter(Context context) {
        super(context, null);
        this.mContext = context;
    }

    public BooksAdapter(Context context, List<Book> data) {
        super(context, data);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.booklist_item, null);
            holder = new Holder();
            holder.bookName = (TextView) convertView.findViewById(R.id.bookName);
            holder.numberOfWords = (TextView) convertView.findViewById(R.id.numberOfWords);
            holder.textHint = (TextView) convertView.findViewById(R.id.hint_text);
            convertView.setTag(holder);
        }
        Book book = getItem(position);
        holder = (Holder) convertView.getTag();
        holder.bookName.setText(book.getNAME());
        holder.numberOfWords.setText(book.getNUMOFWORD() + "");
        return convertView;
    }

    static class Holder {
        TextView bookName;
        TextView numberOfWords;
        TextView textHint;
    }
}

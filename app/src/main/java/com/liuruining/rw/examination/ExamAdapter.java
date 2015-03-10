package com.liuruining.rw.examination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuruining.model.dao.Book2;
import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseListAdapter;

import java.util.List;

/**
 * Created by nielongyu on 15/3/10.
 */
public class ExamAdapter extends BaseListAdapter<Book2> {
    private Context context;


    public void setAllUnSelected() {
        for (int i = 0; i < getCount(); i++) {
            getItem(i).setIsSelect(false);
        }
    }

    public ExamAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Holder holder;
        Book2 book = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_choose, null);
            holder = new Holder();
            holder.meaning = (TextView) convertView.findViewById(R.id.word_meaning);
            holder.mark = (TextView) convertView.findViewById(R.id.mark);
            convertView.setTag(holder);
        }
        holder = (Holder) convertView.getTag();
        holder.meaning.setText(book.getMeanning());
        if (book.getIsSelect() == null) {
            book.setIsSelect(false);
        }
        holder.mark.setBackgroundResource(book.getIsSelect() ? R.mipmap.select_pressed : R.mipmap.select_normal);
        return convertView;
    }

    static class Holder {
        TextView meaning;
        TextView mark;
    }

    public void setSelected(int position, boolean selected) {
        Book2 book2 = getItem(position);
        setAllUnSelected();
        book2.setIsSelect(selected);
        notifyDataSetChanged();
    }


    public int whichSelect() {
        for (int i = 0; i < getCount(); i++) {
            boolean isSelect = getItem(i).getIsSelect();
            if (isSelect) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void setData(List<Book2> data) {
        for (Book2 book2 : data) {
            book2.setIsSelect(false);
        }
        super.setData(data);
    }
}

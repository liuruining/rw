package com.liuruining.rw.wordsnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuruining.model.dao.Word;
import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseListAdapter;

import java.util.List;

/**
 * Created by nielongyu on 15/3/9.
 */
public class WordsNoteAdapter extends BaseListAdapter<Word> {
    private Context context;

    public WordsNoteAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public WordsNoteAdapter(Context context, List<Word> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_word_note, null);
            holder = new Holder();
            holder.spelling = (TextView) convertView.findViewById(R.id.spelling);
            convertView.setTag(holder);
        }
        Word word = getItem(position);
        holder = (Holder) convertView.getTag();
        holder.spelling.setText(word.getSpelling());
        return convertView;
    }

    static class Holder {
        TextView spelling;
    }
}

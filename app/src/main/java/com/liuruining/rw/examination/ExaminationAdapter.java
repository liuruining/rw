package com.liuruining.rw.examination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseListAdapter;

/**
 * Created by nielongyu on 15/3/10.
 */
public class ExaminationAdapter extends BaseListAdapter<ExaminationFragment.Exam> {
    private Context context;

    public ExaminationAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_exam, null);
            holder = new Holder();
            holder.examName = (TextView) convertView.findViewById(R.id.word_list);
            convertView.setTag(holder);
        }
        ExaminationFragment.Exam exam = null;
        exam = getItem(position);
        holder = (Holder) convertView.getTag();
        holder.examName.setText(exam.getListName());
        return convertView;
    }

    static class Holder {
        TextView examName;
    }
}

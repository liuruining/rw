package com.liuruining.rw.examination;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.liuruining.model.dao.Book1;
import com.liuruining.model.dao.Book1Dao;
import com.liuruining.model.dao.DaoSession;
import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;

/**
 * Created by nielongyu on 15/3/10.
 */
public class ExaminationFragment extends BaseListFragment {
    private static final int LOAD_LISTS = 199;

    public static ExaminationFragment newInstance() {
        return new ExaminationFragment();
    }

    @Override
    protected View createDefaultEmptyView() {
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.list_empty_view, null);
        TextView emptyTextView = (TextView) emptyView.findViewById(R.id.empty_text);
        emptyTextView.setText(R.string.empty_list);
        return emptyView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListAdapter(new ExaminationAdapter(getActivity()));
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().getSupportLoaderManager().initLoader(LOAD_LISTS, null, listsCallBacks);
        }
    }

    LoaderManager.LoaderCallbacks<List<Book1>> listsCallBacks = new LoaderManager.LoaderCallbacks<List<Book1>>() {
        @Override
        public Loader<List<Book1>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<List<Book1>>(getActivity()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                }

                @Override
                public List<Book1> loadInBackground() {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        DaoSession daoSession = RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class);
                        return daoSession.getBook1Dao().queryBuilder().orderDesc(Book1Dao.Properties.ID).limit(1).list();
                    }
                    return null;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Book1>> loader, List<Book1> data) {
            if (null == data) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().getSupportLoaderManager().restartLoader(LOAD_LISTS, null, listsCallBacks);
                }
                return;
            }
            long index = data.get(0).getList();
            List<Exam> examList = new ArrayList<Exam>();
            for (long i = 0; i < index; i++) {
                Exam exam = new Exam();
                exam.setListId(i);
                exam.setListName("第" + (i + 1) + "单元");
                examList.add(exam);
            }
            ((ExaminationAdapter) getListAdapter()).setData(examList);
            ((ExaminationAdapter) getListAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<Book1>> loader) {

        }
    };

    public class Exam {
        private String listName;
        private long listId;

        public String getListName() {
            return listName;
        }

        public void setListName(String listName) {
            this.listName = listName;
        }

        public long getListId() {
            return listId;
        }

        public void setListId(long listId) {
            this.listId = listId;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Exam exam = (Exam) getListAdapter().getItem(position);
        Intent examIntent = new Intent(getActivity(), ExamActivity.class);
        examIntent.putExtra("unit", exam.getListId() + 1);
        examIntent.putExtra("list_name", exam.getListName());
        startActivity(examIntent);
    }
}

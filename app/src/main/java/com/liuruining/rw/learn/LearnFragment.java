package com.liuruining.rw.learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.liuruining.model.dao.Book;
import com.liuruining.model.dao.DaoSession;
import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;

/**
 * Created by nielongyu on 15/3/9.
 */
public class LearnFragment extends BaseListFragment {

    private static final int LOAD_BOOKS = 123;

    public static LearnFragment newInstance() {
        return new LearnFragment();
    }

    @Override
    protected View createDefaultEmptyView() {
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.list_empty_view, null);
        TextView emptyTextView = (TextView) emptyView.findViewById(R.id.empty_text);
        emptyTextView.setText(R.string.empty_book);
        return emptyView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(new BooksAdapter(getActivity()));
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().getSupportLoaderManager().initLoader(LOAD_BOOKS, null, booksCallBacks);
        }
    }

    LoaderManager.LoaderCallbacks<List<Book>> booksCallBacks = new LoaderManager.LoaderCallbacks<List<Book>>() {
        @Override
        public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<List<Book>>(getActivity()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                }

                @Override
                public List<Book> loadInBackground() {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        return RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class).getBookDao().loadAll();
                    }
                    return new ArrayList<Book>();
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Book>> loader, List<Book> data) {
            if (null == data || data.size() == 0) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().getSupportLoaderManager().restartLoader(LOAD_BOOKS, null, booksCallBacks);
                }
                return;
            }
            ((BooksAdapter) getListAdapter()).setData(data);
            ((BooksAdapter) getListAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<Book>> loader) {

        }
    };

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Book book = (Book) getListAdapter().getItem(position);
        Intent detailIntent = new Intent(getActivity(), WordDetailActivity.class);
        if ("book1".equalsIgnoreCase(book.getID())) {
            detailIntent.putExtra("book", 1);
        } else if ("book2".equalsIgnoreCase(book.getID())) {
            detailIntent.putExtra("book", 2);
        } else {
            detailIntent.putExtra("book", 3);
        }
        startActivity(detailIntent);

    }
}

package com.liuruining.rw.learn;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liuruining.model.dao.Book;
import com.liuruining.model.dao.DaoSession;
import com.liuruining.model.dao.WordLocation;
import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseListFragment;
import com.liuruining.rw.common.BaseConfig;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;

/**
 * Created by nielongyu on 15/3/9.
 */
public class LearnFragment extends BaseListFragment {

    private static final int LOAD_BOOKS = 123;
    private static final int LOAD_LOCATION = 1011;
    private PopupWindow popupWindow;
    private WordLocation wordLocation;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View customView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_filter_and_more, null);
        final View filterButton = customView.findViewById(R.id.last_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    LayoutInflater inflater = LayoutInflater.from(getActivity());
                    View contentView = inflater.inflate(R.layout.item_word_location, null);
                    if (popupWindow == null) {
                        popupWindow = new PopupWindow(contentView, BaseConfig.dp2px(270), ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setOutsideTouchable(true);
                        TextView indexView = (TextView) contentView.findViewById(R.id.hint_index);
                        if (wordLocation != null) {
                            String text = "";
                            switch (wordLocation.getBook()) {
                                case 1:
                                    text = "MBA(上) 第";
                                    break;
                                case 2:
                                    text = "MBA(中) 第";
                                    break;
                                case 3:
                                    text = "MBA(下) 第";
                                    break;
                            }
                            indexView.setText(text + (wordLocation.getIndex() + 1) + "词");
                        } else {
                            indexView.setText("还没有背诵记录~");
                        }
                        contentView.findViewById(R.id.hint_index).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                    popupWindow.showAsDropDown(filterButton, -BaseConfig.dp2px(110), 0);
                }
            }
        });
        getActionBar().setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams paramsActionBar = new ActionBar.LayoutParams(Gravity.RIGHT);
        getActionBar().setCustomView(customView, paramsActionBar);
    }

    @Override
    public void onStart() {
        super.onStart();
        setListAdapter(new BooksAdapter(getActivity()));
        if (getActivity() != null && !getActivity().isFinishing()) {
            getActivity().getSupportLoaderManager().initLoader(LOAD_BOOKS, null, booksCallBacks);
            getActivity().getSupportLoaderManager().initLoader(LOAD_LOCATION, null, lastCallback);
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

    LoaderManager.LoaderCallbacks<WordLocation> lastCallback = new LoaderManager.LoaderCallbacks<WordLocation>() {
        @Override
        public Loader<WordLocation> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<WordLocation>(getActivity()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                }

                @Override
                public WordLocation loadInBackground() {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        List<WordLocation> wordLocationList = RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class).getWordLocationDao().loadAll();
                        if (wordLocationList != null && wordLocationList.size() >= 1) {
                            return wordLocationList.get(0);
                        }
                    }
                    return null;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<WordLocation> loader, WordLocation data) {
            if (data == null) {
                return;
            }
            wordLocation = data;
        }

        @Override
        public void onLoaderReset(Loader<WordLocation> loader) {

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

package com.liuruining.rw.wordsnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.liuruining.model.dao.DaoSession;
import com.liuruining.model.dao.Word;
import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseListFragment;
import com.liuruining.rw.learn.WordDetailActivity;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;

/**
 * Created by nielongyu on 15/3/9.
 */
public class WordsNoteFragment extends BaseListFragment {
    public static WordsNoteFragment newInstance() {
        return new WordsNoteFragment();
    }

    private static final int LOADER_NOTICES = 300;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListAdapter(new WordsNoteAdapter(getActivity()));
        getActivity().getSupportLoaderManager().initLoader(LOADER_NOTICES, null, wordListLoaderCallbacks);
    }

    @Override
    protected View createDefaultEmptyView() {
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.list_empty_view, null);
        TextView emptyTextView = (TextView) emptyView.findViewById(R.id.empty_text);
        emptyTextView.setText(R.string.empty_word_list);
        return emptyView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().getSupportLoaderManager().restartLoader(LOADER_NOTICES, null, wordListLoaderCallbacks);
    }

    LoaderManager.LoaderCallbacks<List<Word>> wordListLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<Word>>() {
        @Override
        public Loader<List<Word>> onCreateLoader(int i, Bundle bundle) {
            return new AsyncTaskLoader<List<Word>>(getActivity()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                }

                @Override
                public List<Word> loadInBackground() {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        return RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class).getWordDao().loadAll();
                    }
                    return new ArrayList<Word>();
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Word>> listLoader, List<Word> words) {
            if (null == words) {
                return;
            }
            ((WordsNoteAdapter) getListAdapter()).setData(words);
            ((WordsNoteAdapter) getListAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onLoaderReset(Loader<List<Word>> listLoader) {

        }

    };

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent wordDetail = new Intent(getActivity(), WordDetailActivity.class);
        wordDetail.putExtra("word", true);
        wordDetail.putExtra("position", position);
        startActivity(wordDetail);
    }
}

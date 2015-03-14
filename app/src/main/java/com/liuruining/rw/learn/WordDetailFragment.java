package com.liuruining.rw.learn;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuruining.model.dao.Book1;
import com.liuruining.model.dao.Book2;
import com.liuruining.model.dao.Book3;
import com.liuruining.model.dao.DaoSession;
import com.liuruining.model.dao.Word;
import com.liuruining.model.dao.WordLocation;
import com.liuruining.rw.R;
import com.liuruining.rw.RwApplication;
import com.liuruining.rw.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * Created by nielongyu on 15/3/9.
 */
public class WordDetailFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.word)
    private TextView mWord;

    @InjectView(R.id.soundMarker)
    private TextView mSoundMarker;

    @InjectView(R.id.add_wordsNote)
    private TextView mAddWordNote;

    @InjectView(R.id.meaning)
    private TextView mMeaning;

    @InjectView(R.id.last)
    private TextView mLast;

    @InjectView(R.id.next)
    private TextView mNext;

    @InjectView(R.id.del_wordsNote)
    private TextView mDelWordsNote;

    private int mNum;
    private boolean isFromWord = false;
    private int index = 0;
    private static final int LOAD_BOOK = 667;
    private static final int LOAD_WORD = 697;
    private List<Book1> book1List = new ArrayList<Book1>();
    private List<Book2> book2List = new ArrayList<Book2>();
    private List<Book3> book3List = new ArrayList<Book3>();
    private List<Word> wordList = new ArrayList<Word>();

    public static WordDetailFragment newInstance() {
        return new WordDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        RelativeLayout customView = (RelativeLayout) inflater.inflate(R.layout.fragment_word_detail, null);
        return customView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        if (getActivity().getIntent().hasExtra("book")) {
            mNum = getActivity().getIntent().getIntExtra("book", 1);
            switch (mNum) {
                case 1:
                    getActivity().getSupportLoaderManager().initLoader(LOAD_BOOK, null, wordDetailCallbacks1);
                    break;
                case 2:
                    getActivity().getSupportLoaderManager().initLoader(LOAD_BOOK, null, wordDetailCallbacks2);
                    break;
                case 3:
                    getActivity().getSupportLoaderManager().initLoader(LOAD_BOOK, null, wordDetailCallbacks3);
                    break;
            }
        } else if (getActivity().getIntent().hasExtra("word")) {
            isFromWord = getActivity().getIntent().getBooleanExtra("word", false);
            index = getActivity().getIntent().getIntExtra("position", 0);
            getActivity().getSupportLoaderManager().initLoader(LOAD_WORD, null, wordsCallback);
        } else if (getActivity().getIntent().hasExtra("location")) {
            WordLocation location = (WordLocation) getActivity().getIntent().getSerializableExtra("location");
            mNum = location.getBook();
            switch (mNum) {
                case 1:
                    getActivity().getSupportLoaderManager().initLoader(LOAD_BOOK, null, wordDetailCallbacks1);
                    break;
                case 2:
                    getActivity().getSupportLoaderManager().initLoader(LOAD_BOOK, null, wordDetailCallbacks2);
                    break;
                case 3:
                    getActivity().getSupportLoaderManager().initLoader(LOAD_BOOK, null, wordDetailCallbacks3);
                    break;
            }
            index = location.getIndex();
        }
    }

    private void initListener() {
        mAddWordNote.setOnClickListener(this);
        mDelWordsNote.setOnClickListener(this);
        mLast.setOnClickListener(this);
        mNext.setOnClickListener(this);
    }

    LoaderManager.LoaderCallbacks<List<Book1>> wordDetailCallbacks1 = new LoaderManager.LoaderCallbacks<List<Book1>>() {
        @Override
        public Loader<List<Book1>> onCreateLoader(int id, Bundle args) {
            AsyncTaskLoader<List<Book1>> loader = new AsyncTaskLoader<List<Book1>>(getActivity()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                }

                @Override
                public List<Book1> loadInBackground() {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        return RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class).getBook1Dao().loadAll();
                    }
                    return new ArrayList<Book1>();

                }
            };
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<Book1>> loader, List<Book1> data) {
            if (null == data) {
                return;
            }
            book1List = data;
            initView1(index);
            getActivity().getSupportLoaderManager().destroyLoader(LOAD_BOOK);
        }

        @Override
        public void onLoaderReset(Loader<List<Book1>> loader) {

        }
    };
    LoaderManager.LoaderCallbacks<List<Book2>> wordDetailCallbacks2 = new LoaderManager.LoaderCallbacks<List<Book2>>() {
        @Override
        public Loader<List<Book2>> onCreateLoader(int id, Bundle args) {
            AsyncTaskLoader<List<Book2>> loader = new AsyncTaskLoader<List<Book2>>(getActivity()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                }

                @Override
                public List<Book2> loadInBackground() {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        return RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class).getBook2Dao().loadAll();
                    }
                    return new ArrayList<Book2>();

                }
            };
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<Book2>> loader, List<Book2> data) {
            if (null == data) {
                return;
            }
            book2List = data;
            initView2(index);
            getActivity().getSupportLoaderManager().destroyLoader(LOAD_BOOK);
        }

        @Override
        public void onLoaderReset(Loader<List<Book2>> loader) {

        }
    };
    LoaderManager.LoaderCallbacks<List<Book3>> wordDetailCallbacks3 = new LoaderManager.LoaderCallbacks<List<Book3>>() {
        @Override
        public Loader<List<Book3>> onCreateLoader(int id, Bundle args) {
            AsyncTaskLoader<List<Book3>> loader = new AsyncTaskLoader<List<Book3>>(getActivity()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                }

                @Override
                public List<Book3> loadInBackground() {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        return RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class).getBook3Dao().loadAll();
                    }
                    return new ArrayList<Book3>();

                }
            };
            return loader;
        }

        @Override
        public void onLoadFinished(Loader<List<Book3>> loader, List<Book3> data) {
            if (null == data) {
                return;
            }
            book3List = data;
            initView3(index);
            getActivity().getSupportLoaderManager().destroyLoader(LOAD_BOOK);
        }

        @Override
        public void onLoaderReset(Loader<List<Book3>> loader) {

        }
    };

    LoaderManager.LoaderCallbacks<List<Word>> wordsCallback = new LoaderManager.LoaderCallbacks<List<Word>>() {
        @Override
        public Loader<List<Word>> onCreateLoader(int id, Bundle args) {
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
        public void onLoadFinished(Loader<List<Word>> loader, List<Word> data) {
            if (null == data) {
                return;
            }
            wordList = data;
            initWordView(index);
            mAddWordNote.setVisibility(View.GONE);
            mDelWordsNote.setVisibility(View.VISIBLE);
            getActivity().getSupportLoaderManager().destroyLoader(LOAD_WORD);
        }

        @Override
        public void onLoaderReset(Loader<List<Word>> loader) {

        }
    };

    private void initWordView(int index) {
        viewRefresh();
        mWord.setText(wordList.get(index).getSpelling());
        mSoundMarker.setText(wordList.get(index).getPhonetic_alphabet());
        mMeaning.setText(wordList.get(index).getMeanning());
        getActivity().setTitle("记单词  " + (index + 1) + "/" + wordList.size());
    }

    private void initView1(int index) {
        viewRefresh();
        mWord.setText(book1List.get(index).getSpelling());
        mSoundMarker.setText(book1List.get(index).getPhonetic_alphabet());
        mMeaning.setText(book1List.get(index).getMeanning());
        getActivity().setTitle("记单词  " + (index + 1) + "/" + book1List.size());
    }

    private void initView2(int index) {
        viewRefresh();
        mWord.setText(book2List.get(index).getSpelling());
        mSoundMarker.setText(book2List.get(index).getPhonetic_alphabet());
        mMeaning.setText(book2List.get(index).getMeanning());
        getActivity().setTitle("记单词  " + (index + 1) + "/" + book2List.size());
    }

    private void initView3(int index) {
        viewRefresh();
        mWord.setText(book3List.get(index).getSpelling());
        mSoundMarker.setText(book3List.get(index).getPhonetic_alphabet());
        mMeaning.setText(book3List.get(index).getMeanning());
        getActivity().setTitle("记单词  " + (index + 1) + "/" + book3List.size());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_wordsNote:
                addToNote();
                break;
            case R.id.del_wordsNote:
                deleteFromWordNote();
                break;
            case R.id.last:
                moveToLast();
                break;
            case R.id.next:
                moveToNext();
                break;
        }
    }

    private void moveToNext() {
        index++;
        if (isFromWord) {
            initWordView(index);
            return;
        }
        switch (mNum) {
            case 1:
                initView1(index);
                break;
            case 2:
                initView2(index);
                break;
            case 3:
                initView3(index);
                break;
        }
    }

    private void moveToLast() {
        index--;
        if (isFromWord) {
            initWordView(index);
            return;
        }
        switch (mNum) {
            case 1:
                initView1(index);
                break;
            case 2:
                initView2(index);
                break;
            case 3:
                initView3(index);
                break;
        }
    }

    private void addToNote() {
        Word word = new Word();
        switch (mNum) {
            case 1:
                word.setID(book1List.get(index).getID());
                word.setSpelling(book1List.get(index).getSpelling());
                word.setMeanning(book1List.get(index).getMeanning());
                word.setPhonetic_alphabet(book1List.get(index).getPhonetic_alphabet());
                word.setList(book1List.get(index).getList());
                break;
            case 2:
                word.setID(book2List.get(index).getID());
                word.setSpelling(book2List.get(index).getSpelling());
                word.setMeanning(book2List.get(index).getMeanning());
                word.setPhonetic_alphabet(book2List.get(index).getPhonetic_alphabet());
                word.setList(book2List.get(index).getList());
                break;
            case 3:
                word.setID(book3List.get(index).getID());
                word.setSpelling(book3List.get(index).getSpelling());
                word.setMeanning(book3List.get(index).getMeanning());
                word.setPhonetic_alphabet(book3List.get(index).getPhonetic_alphabet());
                word.setList(book3List.get(index).getList());
                break;
        }
        if (word.getID() != null) {
            saveToWordNote(word);
        }
    }

    private void viewRefresh() {
        if (isFromWord) {
            if (index == 0) {
                mLast.setVisibility(View.GONE);
            } else {
                mLast.setVisibility(View.VISIBLE);
            }
            if (index == wordList.size() - 1) {
                mNext.setVisibility(View.GONE);
            } else {
                mNext.setVisibility(View.VISIBLE);
            }
            return;
        }
        switch (mNum) {
            case 1:
                if (index == 0) {
                    mLast.setVisibility(View.GONE);
                } else {
                    mLast.setVisibility(View.VISIBLE);
                }
                if (index == book1List.size() - 1) {
                    mNext.setVisibility(View.GONE);
                } else {
                    mNext.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                if (index == 0) {
                    mLast.setVisibility(View.GONE);
                } else {
                    mLast.setVisibility(View.VISIBLE);
                }
                if (index == book2List.size() - 1) {
                    mNext.setVisibility(View.GONE);
                } else {
                    mNext.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                if (index == 0) {
                    mLast.setVisibility(View.GONE);
                } else {
                    mLast.setVisibility(View.VISIBLE);
                }
                if (index == book3List.size() - 1) {
                    mNext.setVisibility(View.GONE);
                } else {
                    mNext.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void saveToWordNote(final Word word) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class).getWordDao().insertOrReplace(word);
                if (getActivity() != null && !getActivity().isFinishing()) {
                    Toast.makeText(getActivity(), "已加入生词本", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteFromWordNote() {
        final Word word = new Word();
        word.setID(wordList.get(index).getID());
        word.setSpelling(wordList.get(index).getSpelling());
        word.setMeanning(wordList.get(index).getMeanning());
        word.setPhonetic_alphabet(wordList.get(index).getPhonetic_alphabet());
        word.setList(wordList.get(index).getList());
        if (word.getID() != null) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class).getWordDao().delete(word);
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        Toast.makeText(getActivity(), "已从生词本中删除 下次启动后生效", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        saveLocation();
        super.onDestroy();
    }

    @Override
    public void onStop() {
        saveLocation();
        super.onStop();
    }

    @Override
    public void onPause() {
        saveLocation();
        super.onPause();
    }

    private void saveLocation() {
        if (!isFromWord) {
            final WordLocation wordLocation = new WordLocation();
            wordLocation.setID(Long.valueOf("1"));
            switch (mNum) {
                case 1:
                    wordLocation.setBook(1);
                    break;
                case 2:
                    wordLocation.setBook(2);
                    break;
                case 3:
                    wordLocation.setBook(3);
                    break;
            }
            wordLocation.setIndex(index);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    RoboGuice.getInjector(RwApplication.getContext()).getInstance(DaoSession.class).getWordLocationDao().insertOrReplace(wordLocation);
                }
            });
        }
    }
}

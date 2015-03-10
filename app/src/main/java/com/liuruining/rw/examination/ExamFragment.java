package com.liuruining.rw.examination;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuruining.model.dao.Book1;
import com.liuruining.model.dao.Book1Dao;
import com.liuruining.model.dao.Book2;
import com.liuruining.model.dao.DaoSession;
import com.liuruining.model.dao.Word;
import com.liuruining.rw.R;
import com.liuruining.rw.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * Created by nielongyu on 15/3/10.
 */
public class ExamFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(R.id.word_spelling_tv)
    private TextView mWordSpelling;

    @InjectView(R.id.add_wordsNote)
    private TextView mAddWordNote;

    @InjectView(R.id.next)
    private TextView mNext;

    @InjectView(R.id.choose_list)
    private ListView mChooseList;

    private ExamAdapter examAdapter;
    private int totalTrueCount;
    private int trueIndex;
    private int examIndex;
    private static final int LOAD_BOOK1 = 12;
    private static final int LOAD_BOOK2 = 13;
    private long mUnitId;
    private List<Book1> book1List = new ArrayList<Book1>();
    private List<Book2> book2List = new ArrayList<Book2>();

    public static ExamFragment newInstance() {
        return new ExamFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        RelativeLayout customView = (RelativeLayout) inflater.inflate(R.layout.fragment_examing, null);
        return customView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity().getIntent().hasExtra("unit")) {
            mUnitId = getActivity().getIntent().getLongExtra("unit", 0);
            if (getActivity() != null && !getActivity().isFinishing()) {
                getActivity().getSupportLoaderManager().initLoader(LOAD_BOOK1, null, unitLoaderCallback);
            }
        } else {
            getActivity().finish();
        }

    }

    private void initListener() {
        mAddWordNote.setOnClickListener(this);
        mNext.setOnClickListener(this);
    }

    LoaderManager.LoaderCallbacks<List<Book1>> unitLoaderCallback = new LoaderManager.LoaderCallbacks<List<Book1>>() {
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
                    return RoboGuice.getInjector(getActivity())
                            .getInstance(DaoSession.class)
                            .getBook1Dao()
                            .queryBuilder()
                            .where(Book1Dao.Properties.List.eq(mUnitId))
                            .list();
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Book1>> loader, List<Book1> data) {
            if (data == null || data.size() == 0) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().getSupportLoaderManager().restartLoader(LOAD_BOOK1, null, unitLoaderCallback);
                }
                return;
            }
            book1List = data;
            getActivity().getSupportLoaderManager().initLoader(LOAD_BOOK2, null, meaningCallback);
        }

        @Override
        public void onLoaderReset(Loader<List<Book1>> loader) {

        }
    };

    LoaderManager.LoaderCallbacks<List<Book2>> meaningCallback = new LoaderManager.LoaderCallbacks<List<Book2>>() {
        @Override
        public Loader<List<Book2>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<List<Book2>>(getActivity()) {

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    forceLoad();
                }

                @Override
                public List<Book2> loadInBackground() {
                    return RoboGuice.getInjector(getActivity()).getInstance(DaoSession.class).getBook2Dao().loadAll();
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<List<Book2>> loader, List<Book2> data) {
            if (data == null || data.size() == 0) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().getSupportLoaderManager().restartLoader(LOAD_BOOK2, null, meaningCallback);
                }
                return;
            }
            book2List = data;
            mAddWordNote.setVisibility(View.VISIBLE);
            mNext.setVisibility(View.GONE);
            initListener();
            examIndex = 0;
            totalTrueCount = 0;
            examAdapter = new ExamAdapter(getActivity());
            mChooseList.setAdapter(examAdapter);
            mChooseList.setOnItemClickListener(onItemClickListener);
            initView(examIndex);
        }

        @Override
        public void onLoaderReset(Loader<List<Book2>> loader) {

        }
    };

    private void addToNote() {
        Word word = new Word();
        word.setID(book1List.get(examIndex).getID());
        word.setSpelling(book1List.get(examIndex).getSpelling());
        word.setMeanning(book1List.get(examIndex).getMeanning());
        word.setPhonetic_alphabet(book1List.get(examIndex).getPhonetic_alphabet());
        word.setList(book1List.get(examIndex).getList());
        saveToWordNote(word);
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

    private void initView(int index) {
        if (index == book1List.size()) {
            showTotal();
            mNext.setVisibility(View.GONE);
            return;
        }
        mNext.setVisibility(View.GONE);
        mWordSpelling.setText(book1List.get(index).getSpelling());
        randomMeanings(book1List.get(index).getMeanning());
    }

    private void showTotal() {
        final AlertDialog tDialog;
        tDialog = new AlertDialog.Builder(getActivity()).create();
        tDialog.setCancelable(false);
        tDialog.setCanceledOnTouchOutside(false);
        tDialog.setTitle("测验结果");
        tDialog.setMessage("   正确数/总数:  " + totalTrueCount + "/" + book1List.size());
        tDialog.setCanceledOnTouchOutside(true);
        tDialog.setButton(DialogInterface.BUTTON_POSITIVE, "知道啦", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    tDialog.dismiss();
                    getActivity().finish();
                }
            }
        });
        tDialog.show();
    }

    private void randomMeanings(String trueMeaning) {
        List<Integer> ids = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int num;
            do {
                num = random.nextInt(book2List.size());
            } while (ids.contains(num));
            ids.add(num);
        }
        trueIndex = random.nextInt(4);
        List<Book2> book2s = new ArrayList<Book2>();
        for (int i = 0; i < 4; i++) {
            if (trueIndex == i) {
                Book2 book2 = new Book2();
                book2.setMeanning(trueMeaning);
                book2.setPhonetic_alphabet("true");
                book2s.add(book2);
            }
            if (i == 3) {
                break;
            }
            book2s.add(book2List.get(ids.get(i)));
        }
        examAdapter.setData(book2s);
        examAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_wordsNote:
                addToNote();
                break;
            case R.id.next:
                isTrue();
                examIndex++;
                initView(examIndex);
                break;
            default:
                break;
        }
    }

    private void isTrue() {
        if (trueIndex != examAdapter.whichSelect()) {
            if (getActivity() != null && !getActivity().isFinishing()) {
                Toast.makeText(getActivity(), "正确答案:" + book1List.get(examIndex).getMeanning(), Toast.LENGTH_SHORT).show();
            }
        } else {
            totalTrueCount++;
        }
    }

    ListView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            examAdapter.setSelected(i, true);
            mNext.setVisibility(View.VISIBLE);
            if (examIndex > book1List.size() - 1) {
                mNext.setVisibility(View.GONE);
                showTotal();
            }
        }
    };
}

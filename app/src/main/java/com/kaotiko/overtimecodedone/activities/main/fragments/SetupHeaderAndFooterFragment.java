package com.kaotiko.overtimecodedone.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.activities.main.adapters.HeaderAndFooterAdapter;
import com.kaotiko.overtimecodedone.model.context.HeaderAndFooterContext;
import com.kaotiko.overtimecodedone.model.domain.HeaderAndFooter;
import com.kaotiko.overtimecodedone.model.domain.HeaderAndFooterAttributes;
import com.kaotiko.overtimecodedone.utils.views.AppEditText;
import com.kaotiko.overtimecodedone.utils.views.AppToolbar;

import java.util.ArrayList;

public class SetupHeaderAndFooterFragment extends DialogFragment {

    private AppToolbar toolbar;
    private HeaderAndFooterContext headerAndFooterContext;
    private OnReadyEmail onReadyEmail;
    private ArrayList<HeaderAndFooter> headerAndFooters;
    private AppEditText headerFooter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private final int drawableRight = 2;
    private MenuItem next;

    public interface OnReadyEmail {

        void onReadyEmail();

    }

    @Override
    public void onAttach(Context context) {

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        super.onAttach(context);

        onReadyEmail = (OnReadyEmail) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.header_footer_fragment, container, false);

        toolbar = (AppToolbar) rootView.findViewById(R.id.toolbar);

        headerAndFooterContext = HeaderAndFooterContext.context(getContext(), headerAndFooterContext);

        toolbar.inflateMenu(R.menu.menu_next);

        next = toolbar.getMenu().getItem(0);

        next.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                HeaderAndFooterAdapter headerAndFooterAdapter = (HeaderAndFooterAdapter) adapter;

                if(headerAndFooterAdapter.isHaveSelected()) {

                    setupFooter();

                }

                return false;

            }

        });

        headerFooter = (AppEditText) rootView.findViewById(R.id.text);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(layoutManager);

        setupHeader();

        return rootView;

    }

    private void setupFooter() {

        toolbar.inflateMenu(R.menu.menu_ready);

        next.setVisible(false);

        MenuItem ready = toolbar.getMenu().getItem(1);

        ready.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                HeaderAndFooterAdapter headerAndFooterAdapter = (HeaderAndFooterAdapter) adapter;

                if(headerAndFooterAdapter.isHaveSelected()) {

                    onReadyEmail.onReadyEmail();

                    dismiss();

                }

                return false;

            }

        });

        toolbar.setTitle(getString(R.string.select_footer));

            headerFooter.setHint(R.string.footer);

            headerFooter.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    boolean isDrawable = event.getRawX() >= (headerFooter.getRight() - headerFooter.getCompoundDrawables()[drawableRight].getBounds().width());

                    boolean isEmpty = headerFooter.getText().length() == 0;

                    if (! isEmpty && isDrawable) {

                        HeaderAndFooter headerAndFooter = new HeaderAndFooter(0, headerFooter.getText().toString(), HeaderAndFooterAttributes.typeFooter);

                        headerAndFooterContext.insert(headerAndFooter);

                        headerAndFooters.add(headerAndFooter);

                        adapter.notifyDataSetChanged();

                        headerFooter.setText("");

                    }

                    return false;

                }

            });

        headerAndFooters = headerAndFooterContext.getAllFooters();

        adapter = new HeaderAndFooterAdapter(headerAndFooters, headerAndFooterContext, (HeaderAndFooterAdapter.OnHeaderFooterSelected) getContext());

        recyclerView.setAdapter(adapter);

    }

    private void setupHeader() {

        toolbar.setTitle(getString(R.string.select_header));

        headerFooter.setHint(R.string.header);

        headerFooter.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean isDrawable = event.getRawX() >= (headerFooter.getRight() - headerFooter.getCompoundDrawables()[drawableRight].getBounds().width());

                boolean isEmpty = headerFooter.getText().length() == 0;

                if (! isEmpty && isDrawable) {

                    HeaderAndFooter headerAndFooter = new HeaderAndFooter(0, headerFooter.getText().toString(), HeaderAndFooterAttributes.typeHeader);

                    headerFooter.setText("");

                    headerAndFooterContext.insert(headerAndFooter);

                    headerAndFooters.add(headerAndFooter);

                    adapter.notifyDataSetChanged();

                }

                return false;

            }

        });


        headerAndFooters = headerAndFooterContext.getAllHeaders();

    adapter = new HeaderAndFooterAdapter(headerAndFooters, headerAndFooterContext, (HeaderAndFooterAdapter.OnHeaderFooterSelected) getContext());

    recyclerView.setAdapter(adapter);
    }

}

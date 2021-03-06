package com.kaotiko.overtimecodedone.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.activities.main.adapters.EmailsAdapter;
import com.kaotiko.overtimecodedone.model.context.EmailContext;
import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.Record;
import com.kaotiko.overtimecodedone.utils.views.AppEditText;
import com.kaotiko.overtimecodedone.utils.views.AppTextView;
import com.kaotiko.overtimecodedone.utils.views.AppToolbar;

import java.util.ArrayList;

public class SetupEmailsFragment extends DialogFragment {

    private AppToolbar toolbar;
    private AppEditText emailInput;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Email> emails;
    private final int drawableRight = 2;
    EmailContext  emailContext;
    private EmailsAdapter.OnEmailSelected emailSelected;
    private OnEmailsFinished onEmailsFinished;

    public interface OnEmailsFinished{

        boolean onEmailsFinished();

    }

    @Override
    public void onAttach(Context context) {

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        super.onAttach(context);

        onEmailsFinished = (OnEmailsFinished) context;

        emailSelected = (EmailsAdapter.OnEmailSelected) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.email_setup_fragment, container, false);

        emailContext = new EmailContext(getContext());

        toolbar = (AppToolbar) rootView.findViewById(R.id.toolbar);

        toolbar.inflateMenu(R.menu.menu_next);

        toolbar.setTitle(getString(R.string.who_send));

        emailInput = (AppEditText) rootView.findViewById(R.id.textEmailAddress);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        emailContext.open();

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        emails = emailContext.getAllEmails();

        adapter = new EmailsAdapter(emails, emailContext, emailSelected);

        recyclerView.setAdapter(adapter);

        MenuItem allSet = toolbar.getMenu().getItem(0);

        emailInput.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean isDrawable = event.getRawX() >= (emailInput.getRight() - emailInput.getCompoundDrawables()[drawableRight].getBounds().width());

                if(event.getAction() == MotionEvent.ACTION_UP) {

                    emailInput.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.ic_plus), null);

                    boolean isEmpty = emailInput.getText().length() == 0;

                    if (! isEmpty && isDrawable) {

                        Email email = new Email(0, emailInput.getText().toString());

                        long id = emailContext.insertEmail(email);

                        emailInput.setText("");

                        email.setId(id);

                        emails.add(email);

                        adapter.notifyDataSetChanged();

                    }

                } else if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    emailInput.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.ic_plus_pressed), null);

                }

                    return false;

            }

        });

        allSet.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                    if(onEmailsFinished.onEmailsFinished()) {

                        dismiss();

                    }

                return false;

            }

        });

        return rootView;

    }

}

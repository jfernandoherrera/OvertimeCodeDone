package com.kaotiko.overtimecodedone.activities.main.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.model.context.EmailContext;
import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.Record;
import com.kaotiko.overtimecodedone.utils.views.AppEditText;

import java.util.ArrayList;

public class EmailsAdapter extends RecyclerView.Adapter{

    ArrayList<Email> emails;
    private final int drawableRight = 2;
    private EmailContext emailContext;
    private OnEmailSelected onEmailSelected;

    public interface OnEmailSelected{

        boolean onEmailSelected(Email email);

    }

    public EmailsAdapter(ArrayList<Email> emails, EmailContext emailContext, OnEmailSelected onEmailSelected) {

        this.emails = emails;

        this.emailContext = emailContext;

        this.onEmailSelected = onEmailSelected;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_view, parent, false);

        EmailHolder emailHolder = new EmailHolder(v);

        return emailHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final EmailHolder emailHolder = (EmailHolder)holder;

        emailHolder.removeTextChangedListeners();

        emailHolder.emailIndex = position;

        emailHolder.email.setText(emails.get(position).getEmail());

        emailHolder.setTextWatcher();

        emailHolder.select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean isThere = onEmailSelected.onEmailSelected(emails.get(position));

                if(isThere) {

                    v.setBackground(v.getResources().getDrawable(R.mipmap.ic_select));

                    Drawable normalBackground = v.getResources().getDrawable(android.R.drawable.editbox_background_normal);

                    emailHolder.linearLayout.setBackground(normalBackground);

                } else {

                    v.setBackground(v.getResources().getDrawable(R.mipmap.ic_un_select));

                    emailHolder.linearLayout.setBackgroundColor(v.getResources().getColor(R.color.colorAccent2));

                }

            }

        });

    }

    @Override
    public int getItemCount() {

        return emails.size();

    }

    public class EmailHolder extends RecyclerView.ViewHolder {

        protected AppEditText email;
        protected Button delete;
        protected Button select;
        private TextWatcher textWatcher;
        protected int emailIndex;
        protected LinearLayout linearLayout;

        public EmailHolder(View itemView) {

            super(itemView);

            email = (AppEditText) itemView.findViewById(R.id.textEmailAddress);

            delete = (Button) itemView.findViewById(R.id.delete);

            select = (Button) itemView.findViewById(R.id.select);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.emailContainer);

        }

        protected void setTextWatcher() {

            textWatcher = new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    EmailHolder.this.afterTextChanged();

                }

            };

            email.addTextChangedListener(textWatcher);

        }

        protected void removeTextChangedListeners() {

            email.removeTextChangedListener(textWatcher);

        }

        protected void afterTextChanged() {

            email.setCompoundDrawablesWithIntrinsicBounds(null, null, email.getResources().getDrawable(R.mipmap.ic_edit), null);

            emails.get(emailIndex).setEmail(email.getText().toString());

            final int width = email.getResources().getDrawable(R.mipmap.ic_edit).getIntrinsicWidth();

            email.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    boolean isDrawable = event.getRawX() >= (email.getRight() - width);

                    if(isDrawable) {

                        emailContext.updateEmail(emails.get(emailIndex));

                        email.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                    }

                    return false;

                }

            });

        }

    }

}

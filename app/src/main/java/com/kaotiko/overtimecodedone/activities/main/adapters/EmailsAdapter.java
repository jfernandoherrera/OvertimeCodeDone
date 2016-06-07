package com.kaotiko.overtimecodedone.activities.main.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.kaotiko.overtimecodedone.R;
import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.utils.views.AppEditText;

import java.util.ArrayList;

public class EmailsAdapter extends RecyclerView.Adapter{

    ArrayList<Email> emails;

    public EmailsAdapter(ArrayList<Email> emails) {

        this.emails = emails;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_view, parent, false);

        EmailHolder emailHolder = new EmailHolder(v);

        return emailHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        EmailHolder emailHolder = (EmailHolder)holder;

        emailHolder.email.setText(emails.get(position).getEmail());

    }

    @Override
    public int getItemCount() {

        return emails.size();

    }

    public class EmailHolder extends RecyclerView.ViewHolder{

        protected AppEditText email;
        protected Button delete;

        public EmailHolder(View itemView) {

            super(itemView);

            email = (AppEditText) itemView.findViewById(R.id.textEmailAddress);

            delete = (Button) itemView.findViewById(R.id.delete);

        }

    }

}

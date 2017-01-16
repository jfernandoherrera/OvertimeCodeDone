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
import com.kaotiko.overtimecodedone.model.context.HeaderAndFooterContext;
import com.kaotiko.overtimecodedone.model.domain.Email;
import com.kaotiko.overtimecodedone.model.domain.HeaderAndFooter;
import com.kaotiko.overtimecodedone.utils.views.AppEditText;

import java.util.ArrayList;

public class HeaderAndFooterAdapter  extends RecyclerView.Adapter{

    ArrayList<HeaderAndFooter> headerAndFooters;
    private final int drawableRight = 2;
    private HeaderAndFooterContext headerAndFooterContext;
    private OnHeaderFooterSelected onHeaderFooterSelected;
    private boolean haveSelected;

    public interface OnHeaderFooterSelected{

        boolean onHeaderFooterSelected(HeaderAndFooter headerAndFooter);

    }

    public HeaderAndFooterAdapter( ArrayList<HeaderAndFooter> headerAndFooters, HeaderAndFooterContext headerAndFooterContext, OnHeaderFooterSelected onHeaderFooterSelected) {

        this.headerAndFooters = headerAndFooters;

        haveSelected = false;

        this.headerAndFooterContext = headerAndFooterContext;

        this.onHeaderFooterSelected = onHeaderFooterSelected;

    }

    public boolean isHaveSelected() {

        return haveSelected;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_footer_view, parent, false);

        HeaderFooterHolder headerFooterHolder = new HeaderFooterHolder(v);

        return headerFooterHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final HeaderFooterHolder emailHolder = (HeaderFooterHolder)holder;

        final HeaderAndFooter headerAndFooter = headerAndFooters.get(position);

        emailHolder.removeTextChangedListeners();

        emailHolder.textIndex = position;

        emailHolder.text.setText(headerAndFooter.getText());

        emailHolder.setTextWatcher();

        emailHolder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                headerAndFooterContext.delete(headerAndFooters.get(position));

                headerAndFooters.remove(position);

                notifyDataSetChanged();

            }

        });

        if(headerAndFooter.isSelected()) {

            emailHolder.select.setBackground(emailHolder.linearLayout.getResources().getDrawable(R.mipmap.ic_un_select));

            emailHolder.linearLayout.setBackgroundColor(emailHolder.linearLayout.getResources().getColor(R.color.colorAccent2));

        }else {

            Drawable normalBackground = emailHolder.linearLayout.getResources().getDrawable(android.R.drawable.editbox_background_normal);

            emailHolder.linearLayout.setBackground(normalBackground);

            emailHolder.select.setBackground(emailHolder.select.getResources().getDrawable(R.mipmap.ic_select));

        }

        if(headerAndFooter.isReadyToEdit()) {

            emailHolder.afterTextChanged();

        }

        emailHolder.select.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                boolean isThere = onHeaderFooterSelected.onHeaderFooterSelected(headerAndFooters.get(position));

                if(isThere) {

                    haveSelected = false;

                    headerAndFooters.get(position).setSelected(false);

                    v.setBackground(v.getResources().getDrawable(R.mipmap.ic_select));

                    Drawable normalBackground = v.getResources().getDrawable(android.R.drawable.editbox_background_normal);

                    emailHolder.linearLayout.setBackground(normalBackground);

                } else {

                    haveSelected = true;

                    headerAndFooterUnSelectAll(position);

                    headerAndFooters.get(position).setSelected(true);

                    v.setBackground(v.getResources().getDrawable(R.mipmap.ic_un_select));

                    emailHolder.linearLayout.setBackgroundColor(v.getResources().getColor(R.color.colorAccent2));

                }

            }

        });

    }

    private void headerAndFooterUnSelectAll( int index) {

        for(int i = 0; i < headerAndFooters.size(); i++) {

            boolean lastSelected = headerAndFooters.get(i).isSelected();

            if(i != index && lastSelected) {

                headerAndFooters.get(i).setSelected(false);

                notifyDataSetChanged();

                break;

            }

        }

    }

    @Override
    public int getItemCount() {

        return headerAndFooters.size();

    }

    public class HeaderFooterHolder extends RecyclerView.ViewHolder {

        protected AppEditText text;
        protected Button delete;
        protected Button select;
        private TextWatcher textWatcher;
        protected int textIndex;
        protected LinearLayout linearLayout;

        public HeaderFooterHolder(View itemView) {

            super(itemView);

            text = (AppEditText) itemView.findViewById(R.id.text);

            delete = (Button) itemView.findViewById(R.id.delete);

            select = (Button) itemView.findViewById(R.id.select);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.textContainer);

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

                   HeaderFooterHolder.this.afterTextChanged();

                }

            };

            text.addTextChangedListener(textWatcher);

        }

        protected void removeTextChangedListeners() {

            text.removeTextChangedListener(textWatcher);

        }

        protected void afterTextChanged() {

            headerAndFooters.get(textIndex).setReadyToEdit(true);

            text.setCompoundDrawablesWithIntrinsicBounds(null, null, text.getResources().getDrawable(R.mipmap.ic_edit), null);

            headerAndFooters.get(textIndex).setText(text.getText().toString());

            final int width = text.getResources().getDrawable(R.mipmap.ic_edit).getIntrinsicWidth();

            text.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    boolean isDrawable = event.getRawX() >= (text.getRight() - width);

                    if(isDrawable) {

                        headerAndFooterContext.update(headerAndFooters.get(textIndex));

                        text.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                        headerAndFooters.get(textIndex).setReadyToEdit(false);

                    }

                    return false;

                }

            });

        }

    }

}

package com.kaotiko.overtimecodedone.model.domain;


public class HeaderAndFooter {
        private long id;
        private String text;
        private boolean readyToEdit;
        private boolean selected;
        private String type;

        public HeaderAndFooter(long id, String text, String type) {

            this.type = type;
            this.id = id;
            this.text = text;
            readyToEdit = false;
            selected = false;

        }

    public String getType() {

        return type;

    }

    public void setType(String type) {

        this.type = type;

    }

    public void setSelected(boolean selected) {

            this.selected = selected;

        }

        public void setReadyToEdit(boolean readyToEdit) {

            this.readyToEdit = readyToEdit;

        }

        public boolean isReadyToEdit() {

            return readyToEdit;

        }

        public boolean isSelected() {

            return selected;

        }

        public void setId(long id) {

            this.id = id;

        }

        public long getId() {

            return id;

        }

        public void setText(String text) {

            this.text = text;

        }

        public String getText() {

            return text;

        }

}
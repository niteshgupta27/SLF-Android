package com.storelogflog.StorageSelection.model;

public class SelectedGridModel {
    private String  gridcell;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }



    public String getGridcell() {
        return gridcell;
    }


    public void setGridcell(String gridcell) {
        this.gridcell = gridcell;
    }


}

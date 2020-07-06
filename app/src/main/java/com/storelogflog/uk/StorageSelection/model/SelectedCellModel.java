package com.storelogflog.uk.StorageSelection.model;

public class SelectedCellModel {
    private String  gridcell;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public SelectedCellModel(String gridcell) {
        this.gridcell = gridcell;
    }

    public String getGridcell() {
        return gridcell;
    }


    public void setGridcell(String gridcell) {
        this.gridcell = gridcell;
    }


}

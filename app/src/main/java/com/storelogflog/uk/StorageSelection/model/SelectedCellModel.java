package com.storelogflog.uk.StorageSelection.model;

public class SelectedCellModel {
    private String  gridcell;
    private boolean isChecked;
    private boolean isChecked2;

    public boolean isChecked2() {
        return isChecked2;
    }

    public void setChecked2(boolean checked2) {
        isChecked2 = checked2;
    }

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

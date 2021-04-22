package com.storelogflog.uk.StorageSelection.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StorageShapeModel {
    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("Storage")
    @Expose
    private Storage storage;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
    public static class Storage {

        @SerializedName("UnitType")
        @Expose
        private Integer unitType;
        @SerializedName("Doors")
        @Expose
        private String doors;
        @SerializedName("DoorType")
        @Expose
        private String doorType;

        @SerializedName("DoorColor")
        @Expose
        private String DoorColor;


        @SerializedName("ShapsList")
        @Expose
        private List<ShapsList> shapsList = null;

        public Integer getUnitType() {
            return unitType;
        }

        public void setUnitType(Integer unitType) {
            this.unitType = unitType;
        }

        public String getDoors() {
            return doors;
        }

        public void setDoors(String doors) {
            this.doors = doors;
        }

        public String getDoorType() {
            return doorType;
        }

        public void setDoorType(String doorType) {
            this.doorType = doorType;
        }

        public String getDoorColor() {
            return DoorColor;
        }

        public void setDoorColor(String doorColor) {
            DoorColor = doorColor;
        }


        public List<ShapsList> getShapsList() {
            return shapsList;
        }

        public void setShapsList(List<ShapsList> shapsList) {
            this.shapsList = shapsList;
        }

        public static class ShapsList {

            @SerializedName("ShapID")
            @Expose
            private Integer shapID;
            @SerializedName("ShapValue")
            @Expose
            private Integer shapValue;
            @SerializedName("RackStatus")
            @Expose
            private String rackStatus;
            @SerializedName("RackList")
            @Expose
            private List<RackList> rackList = null;

            private boolean isChecked;
            private String shape_name;
            private String shape_rack_value;
            private String shape_id2;
            private String RackID_position;
            private String RackID;


            private String  DoorPosition;


            public String getRackID() {
                return RackID;
            }

            public void setRackID(String rackID) {
                RackID = rackID;
            }

            public String getDoorPosition() {
                return DoorPosition;
            }

            public void setDoorPosition(String doorPosition) {
                DoorPosition = doorPosition;
            }

            public String getRackID_position() {
                return RackID_position;
            }

            public void setRackID_position(String rackID_position) {
                RackID_position = rackID_position;
            }

            public String getShape_id2() {
                return shape_id2;
            }

            public void setShape_id2(String shape_id2) {
                this.shape_id2 = shape_id2;
            }

            public String getShape_rack_value() {
                return shape_rack_value;
            }

            public void setShape_rack_value(String shape_rack_value) {
                this.shape_rack_value = shape_rack_value;
            }

            public String getShape_name() {
                return shape_name;
            }

            public void setShape_name(String shape_name) {
                this.shape_name = shape_name;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public Integer getShapID() {
                return shapID;
            }

            public void setShapID(Integer shapID) {
                this.shapID = shapID;
            }

            public Integer getShapValue() {
                return shapValue;
            }

            public void setShapValue(Integer shapValue) {
                this.shapValue = shapValue;
            }

            public String getRackStatus() {
                return rackStatus;
            }

            public void setRackStatus(String rackStatus) {
                this.rackStatus = rackStatus;
            }

            public List<RackList> getRackList() {
                return rackList;
            }

            public void setRackList(List<RackList> rackList) {
                this.rackList = rackList;
            }


            public class RackList {

                @SerializedName("fld_racks_id")
                @Expose
                private Integer fldRacksId;
                @SerializedName("fld_storage_id")
                @Expose
                private Integer fldStorageId;
                @SerializedName("fld_unit_id")
                @Expose
                private Integer fldUnitId;
                @SerializedName("fld_shaps_id")
                @Expose
                private Integer fldShapsId;
                @SerializedName("fld_rack_value")
                @Expose
                private Integer fldRackValue;
                @SerializedName("fld_created_at")
                @Expose
                private String fldCreatedAt;
                @SerializedName("fld_updated_at")
                @Expose
                private String fldUpdatedAt;

                private boolean CheckRack;

                public boolean isCheckRack() {
                    return CheckRack;
                }

                public void setCheckRack(boolean checkRack) {
                    CheckRack = checkRack;
                }

                public Integer getFldRacksId() {
                    return fldRacksId;
                }

                public void setFldRacksId(Integer fldRacksId) {
                    this.fldRacksId = fldRacksId;
                }

                public Integer getFldStorageId() {
                    return fldStorageId;
                }

                public void setFldStorageId(Integer fldStorageId) {
                    this.fldStorageId = fldStorageId;
                }

                public Integer getFldUnitId() {
                    return fldUnitId;
                }

                public void setFldUnitId(Integer fldUnitId) {
                    this.fldUnitId = fldUnitId;
                }

                public Integer getFldShapsId() {
                    return fldShapsId;
                }

                public void setFldShapsId(Integer fldShapsId) {
                    this.fldShapsId = fldShapsId;
                }

                public Integer getFldRackValue() {
                    return fldRackValue;
                }

                public void setFldRackValue(Integer fldRackValue) {
                    this.fldRackValue = fldRackValue;
                }

                public String getFldCreatedAt() {
                    return fldCreatedAt;
                }

                public void setFldCreatedAt(String fldCreatedAt) {
                    this.fldCreatedAt = fldCreatedAt;
                }

                public String getFldUpdatedAt() {
                    return fldUpdatedAt;
                }

                public void setFldUpdatedAt(String fldUpdatedAt) {
                    this.fldUpdatedAt = fldUpdatedAt;
                }

            }

        }

    }
}

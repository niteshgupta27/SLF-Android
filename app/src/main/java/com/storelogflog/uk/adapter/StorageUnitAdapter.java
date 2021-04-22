package com.storelogflog.uk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.storelogflog.uk.R;
import com.storelogflog.uk.apputil.Constants;
import com.storelogflog.uk.bean.storageDetailsBean.Unit;

import java.util.List;

public class StorageUnitAdapter extends RecyclerView.Adapter<StorageUnitAdapter.StorageUnitHolder> {

    FragmentActivity activity;
    List<Unit>unitList;

    public StorageUnitAdapter(FragmentActivity activity,List<Unit>unitList) {
        this.activity = activity;
        this.unitList=unitList;
    }

    @NonNull
    @Override
    public StorageUnitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.item_storage_unit,parent,false);
        return new StorageUnitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageUnitHolder holder, int position) {

         if(position==1)
             holder.txtHorizontalLine.setVisibility(View.GONE);

         Unit unit=unitList.get(position);
         holder.txtUnitName.setText(""+unit.getName());
         holder.txtDescription.setText(""+unit.getDesp());
         holder.txtAvailabilityValue.setText(unit.getAvail()+" units");
         holder.txtPriceValue.setText(unit.getPrice());

    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public class StorageUnitHolder extends RecyclerView.ViewHolder
    {

        private AppCompatTextView txtHorizontalLine;
        private AppCompatTextView txtUnitName;
        private AppCompatTextView txtDescription;
        private AppCompatTextView txtAvailabilityValue;
        private AppCompatTextView txtPriceValue;

        public StorageUnitHolder(@NonNull View itemView) {
            super(itemView);

            txtHorizontalLine=itemView.findViewById(R.id.txt_horizontal_line);
            txtUnitName=itemView.findViewById(R.id.txt_unit_name);
            txtDescription=itemView.findViewById(R.id.txt_description);
            txtAvailabilityValue=itemView.findViewById(R.id.txt_availability_value);
            txtPriceValue=itemView.findViewById(R.id.txt_price_value);


        }
    }
}

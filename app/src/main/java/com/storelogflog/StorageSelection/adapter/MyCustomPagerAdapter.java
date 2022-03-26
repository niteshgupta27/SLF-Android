package com.storelogflog.StorageSelection.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.request.RequestOptions;
import com.storelogflog.R;
import com.storelogflog.StorageSelection.activity.ShanpeInfoActivity;
import com.storelogflog.StorageSelection.fragment.Card_fragment2;
import com.storelogflog.StorageSelection.model.CardViewModel;
import com.storelogflog.StorageSelection.model.StorageShapeModel;
import com.storelogflog.adapter.CategryListAdapter;
import com.storelogflog.apputil.Common;
import com.storelogflog.apputil.Constants;
import com.storelogflog.bean.storageBean.Storage;
import com.storelogflog.fragment.AddAuctionItemFragment;
import com.storelogflog.fragment.AddItemFragment;
import com.storelogflog.fragment.EditItemFragment;
import com.storelogflog.fragment.StoreFragment;

import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyCustomPagerAdapter extends PagerAdapter {

    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.
    FragmentActivity context;
    LayoutInflater layoutInflater;
    List<CardViewModel.Item> arPhotoIn;
    RequestOptions options;
    int currentPage = 0;
    Timer timer;
    AlertDialog alertDialog;
    int pagNo = 0;
    Storage storage;
    ArrayList<StorageShapeModel.Storage.ShapsList> shape_name_list;


    public MyCustomPagerAdapter(FragmentActivity context, List<CardViewModel.Item> arPhoto_, ArrayList<StorageShapeModel.Storage.ShapsList> shape_name, Storage storage) {
        this.context = context;
        arPhotoIn = arPhoto_;
        shape_name_list = shape_name;
        this.storage = storage;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return arPhotoIn.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.card_item, container, false);

        TextView card_category_txt = itemView.findViewById(R.id.card_category_txt);
        TextView item_title_txt = itemView.findViewById(R.id.item_title_txt);
        TextView value_txt = itemView.findViewById(R.id.value_txt);
        TextView category_txt = itemView.findViewById(R.id.category_txt);
        TextView size_txt = itemView.findViewById(R.id.size_txt);
        TextView size = itemView.findViewById(R.id.size);
        TextView location_txt = itemView.findViewById(R.id.location_txt);
        // ImageView card_img = itemView.findViewById(R.id.card_img);
        TextView short_desc = itemView.findViewById(R.id.short_desc);
        ImageView share = itemView.findViewById(R.id.share);
        RelativeLayout category_linear = itemView.findViewById(R.id.category_linear);
        LinearLayout location_linear = itemView.findViewById(R.id.location_linear);
        ViewPager img_viewpager = itemView.findViewById(R.id.img_viewpager);
        RelativeLayout rlPre = itemView.findViewById(R.id.rlPre);
        RelativeLayout rlNext = itemView.findViewById(R.id.rlNext);
        RelativeLayout viewpager_relative = itemView.findViewById(R.id.viewpager_relative);
        ImageView img_cardview = itemView.findViewById(R.id.card_img);
        AppCompatTextView add_value = itemView.findViewById(R.id.add_value);
        AppCompatTextView relocate_item = itemView.findViewById(R.id.relocate_item);
        RelativeLayout card_relative = itemView.findViewById(R.id.card_relative);
        AppCompatTextView Get_value = itemView.findViewById(R.id.Get_value);
        AppCompatTextView edit_item = itemView.findViewById(R.id.edit_item);
        ImageView add_plus = itemView.findViewById(R.id.add_plus);
        LinearLayout item_linear = itemView.findViewById(R.id.item_linear);
        LinearLayout item_linear2 = itemView.findViewById(R.id.item_linear2);
        AppCompatTextView add_value2 = itemView.findViewById(R.id.add_value2);
        AppCompatTextView Get_value2 = itemView.findViewById(R.id.Get_value2);
        AppCompatTextView edit_item2 = itemView.findViewById(R.id.edit_item2);

        CardViewModel.Item item = arPhotoIn.get(position);

        options = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .fitCenter()
                .placeholder(R.drawable.card_images)
                .error(R.drawable.card_images);

        card_category_txt.setText(String.valueOf(position + 1) + "/" + String.valueOf(arPhotoIn.size()));

        item_title_txt.setText(arPhotoIn.get(position).getName());

        if (arPhotoIn.get(position).getQty() != null) {
            if (!arPhotoIn.get(position).getQty().equals("")) {
                if (Integer.parseInt(arPhotoIn.get(position).getQty()) > 1) {
                    item_title_txt.setText(String.valueOf(arPhotoIn.get(position).getName()+ " (" + String.valueOf(arPhotoIn.get(position).getQty())+ ") " ));
                } else {
                    item_title_txt.setText(String.valueOf(arPhotoIn.get(position).getName()));
                }
            }

        } else {
            item_title_txt.setText(String.valueOf(arPhotoIn.get(position).getName()));
        }

        value_txt.setText(String.valueOf(arPhotoIn.get(position).getValue()));

        if (arPhotoIn.get(position).getCategory().size() > 0) {
            category_txt.setText(String.valueOf(arPhotoIn.get(position).getCategory().get(0).getName()));
        }

        if (arPhotoIn.get(position).getCategory().size() > 1) {
            add_plus.setVisibility(View.VISIBLE);
        } else {
            add_plus.setVisibility(View.GONE);
        }

        // Log.e("Item_size", String.valueOf(arPhotoIn.size()));

        if (arPhotoIn.size() > 1) {
            item_linear.setVisibility(View.VISIBLE);
            item_linear2.setVisibility(View.GONE);
        } else {
            item_linear2.setVisibility(View.GONE);
            item_linear2.setVisibility(View.VISIBLE);
        }

        if (arPhotoIn.get(position).getLength() != null && arPhotoIn.get(position).getWidth() != null && arPhotoIn.get(position).getHeight() != null) {

            if (!arPhotoIn.get(position).getLength().equals("") && !(arPhotoIn.get(position).getWidth().equals("")) && !(arPhotoIn.get(position).getHeight().equals(""))) {

                size_txt.setText(String.valueOf(arPhotoIn.get(position).getLength()) + "x" + String.valueOf(arPhotoIn.get(position).getWidth()) + "x" + String.valueOf(arPhotoIn.get(position).getHeight()));

                if (arPhotoIn.get(position).getUnit().equals("I")) {

                    size.setText("Size (Inches) (LxWxT)");
                } else {
                    size.setText("Size (CM) (LxWxT)");

                }

            }
        }

        if (shape_name_list.get(position).getShape_name() != null) {
            if (!shape_name_list.get(position).getShape_name().equals("")) {
                location_txt.setText(String.valueOf(shape_name_list.get(position).getShape_name()));
            }
        }

        String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(arPhotoIn.get(position).getDesp());
        short_desc.setText(fromServerUnicodeDecoded);


        if (arPhotoIn.get(position).getPhotos().size() > 0) {
            viewpager_relative.setVisibility(View.VISIBLE);
            img_cardview.setVisibility(View.GONE);

            ImageAdapter image_adapter = new ImageAdapter(context, arPhotoIn.get(position).getPhotos());
            img_viewpager.setAdapter(image_adapter);
            img_viewpager.setCurrentItem(pagNo);

            Log.e("Photos", String.valueOf(arPhotoIn.get(position).getPhotos()));
        } else {
            viewpager_relative.setVisibility(View.GONE);
            img_cardview.setVisibility(View.VISIBLE);

        }


        if (arPhotoIn.get(position).getPhotos().size() > 1) {


            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == arPhotoIn.get(position).getPhotos().size()) {
                        currentPage = 0;
                    }
                    img_viewpager.setCurrentItem(currentPage++, true);
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);


        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Bitmap bitmap = ScreenshotUtils.getScreenShot(card_relative);

                card_relative.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(card_relative.getDrawingCache());
                shareScreenshot(bitmap);
            }
        });


        category_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arPhotoIn.get(position).getCategory().size() > 1) {
                    CategoryDialog(position);
                }
            }
        });

        location_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ShanpeInfoActivity.class);
                intent.putExtra("unitID", storage.getUnitID());
                intent.putExtra("title_name", arPhotoIn.get(position).getName());
                intent.putExtra("Shape_id2", shape_name_list.get(position).getShape_id2());
                intent.putExtra("RackID_position", shape_name_list.get(position).getRackID_position());
                intent.putExtra("RackID", shape_name_list.get(position).getRackID());
                context.startActivity(intent);

            }
        });


        img_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i == 0) {
                    rlPre.setVisibility(View.GONE);
                    rlNext.setVisibility(View.VISIBLE);
                } else if (i == arPhotoIn.get(position).getPhotos().size() - 1) {
                    rlPre.setVisibility(View.VISIBLE);
                    rlNext.setVisibility(View.GONE);

                } else {
                    rlPre.setVisibility(View.VISIBLE);
                    rlNext.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    rlPre.setVisibility(View.GONE);
                    rlNext.setVisibility(View.VISIBLE);
                } else if (i == arPhotoIn.get(position).getPhotos().size() - 1) {
                    rlPre.setVisibility(View.VISIBLE);
                    rlNext.setVisibility(View.GONE);

                } else {
                    rlPre.setVisibility(View.VISIBLE);
                    rlNext.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        rlPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPre(false, img_viewpager, position);
            }
        });

        rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NextPre(true, img_viewpager, position);
            }
        });

        add_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Card_fragment2();
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                bundle.putString("AddedItems", String.valueOf(arPhotoIn.size()));
                fragment.setArguments(bundle);
                Common.loadFragment(context, fragment, true, null);
            }
        });

        relocate_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new StoreFragment();
                Bundle bundle = new Bundle();
                bundle.putString("from", "store");
                bundle.putString(Constants.ItemID, String.valueOf(arPhotoIn.get(position).getID()));
                fragment.setArguments(bundle);
                Common.loadFragment(context, fragment, true, null);
            }
        });

        Get_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddAuctionItemFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                bundle.putSerializable("item", item);
                fragment.setArguments(bundle);
                Common.loadFragment(context, fragment, true, null);

                Log.e("storage", String.valueOf(storage));

            }
        });

        edit_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditItemFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                bundle.putSerializable("item", item);
                bundle.putString("position", String.valueOf(position));
                bundle.putString("Shape_id2", shape_name_list.get(position).getShape_id2());
                bundle.putString("RackID_position", shape_name_list.get(position).getRackID_position());
                bundle.putString("RackID", shape_name_list.get(position).getRackID());
                fragment.setArguments(bundle);
                Common.loadFragment(context, fragment, true, null);
            }
        });


        add_value2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddItemFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                bundle.putString("AddedItems", String.valueOf(arPhotoIn.size()));
                fragment.setArguments(bundle);
                Common.loadFragment(context, fragment, true, null);
            }
        });


        Get_value2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddAuctionItemFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                bundle.putSerializable("item", item);
                fragment.setArguments(bundle);
                Common.loadFragment(context, fragment, true, null);

                Log.e("storage", String.valueOf(storage));
            }
        });


        edit_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditItemFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("storage", storage);
                bundle.putSerializable("item", item);
                bundle.putString("Shape_id2", shape_name_list.get(position).getShape_id2());
                bundle.putString("RackID_position", shape_name_list.get(position).getRackID_position());
                bundle.putString("RackID", shape_name_list.get(position).getRackID());
                fragment.setArguments(bundle);
                Common.loadFragment(context, fragment, true, null);
            }
        });

        container.addView(itemView);
        return itemView;
    }


    public void NextPre(boolean check, ViewPager img_viewpager, int position) {
        int setPos = 0;
        int pos = img_viewpager.getCurrentItem();
        if (check) //next
        {
            if (pos >= (arPhotoIn.get(position).getPhotos().size() - 1)) {
                setPos = 0;
            } else {
                setPos = pos + 1;
            }
        } else//previous
        {
            if (pos > 0) {
                setPos = pos - 1;
            } else {
                setPos = arPhotoIn.get(position).getPhotos().size();
            }
        }


        img_viewpager.setCurrentItem(setPos);
    }

    private void CategoryDialog(int position) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialogue_category,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        RelativeLayout close = layout.findViewById(R.id.close);
        RecyclerView category_list = layout.findViewById(R.id.category_list);
        category_list.setHasFixedSize(true);
        CategryListAdapter categryListAdapter = new CategryListAdapter(context, arPhotoIn.get(position).getCategory());
        category_list.setAdapter(categryListAdapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }


    private void shareScreenshot(Bitmap bitmap) {
        try {
            File file = new File(context.getExternalCacheDir(), "share.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri apkURI = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                intent.setDataAndType(apkURI, "image/jpg");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_TEXT, "Store Log Flog");
                intent.putExtra(Intent.EXTRA_STREAM, apkURI);
            } else {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.putExtra(Intent.EXTRA_TEXT, "Store Log Flog");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                intent.setDataAndType(Uri.fromFile(file), "image/jpg");
            }

            context.startActivity(Intent.createChooser(intent, "Share image via"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

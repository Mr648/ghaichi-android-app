package com.sorinaidea.ghaichi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sorinaidea.ghaichi.R;
import com.sorinaidea.ghaichi.ui.BarberShopGridActivity;
import com.sorinaidea.ghaichi.util.FontManager;
import com.sorinaidea.ghaichi.webservice.API;
import com.sorinaidea.ghaichi.webservice.BarbershopServices;

import java.util.ArrayList;

import retrofit2.Retrofit;


/**
 * Created by mr-code on 5/6/2018.
 */

public class BarberShopCategoryAdapter extends RecyclerView.Adapter<BarberShopCategoryAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mItems;
    Typeface fontIransans;

    public BarberShopCategoryAdapter(Context mContext,
                                     ArrayList<String> mItems) {

        this.mItems = mItems;
        this.mContext = mContext;
        fontIransans = FontManager.getTypeface(mContext, FontManager.IRANSANS_TEXTS);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final LinearLayout btnMore;
        private final TextView txtBarberShopCategory;

        private final RecyclerView recBarberShop;


        public ViewHolder(View view) {
            super(view);
            this.view = view;
            btnMore = view.findViewById(R.id.btnMore);
            txtBarberShopCategory = view.findViewById(R.id.txtBarberShopCategory);

            recBarberShop = view.findViewById(R.id.recBarberShop);
        }


        public View getView() {
            return view;
        }

        public LinearLayout getBtnMore() {
            return btnMore;
        }

        public RecyclerView getRecBarberShop() {
            return recBarberShop;
        }

        public TextView getTxtBarberShopCategory() {
            return txtBarberShopCategory;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.barbershop_category_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//
//       FontManager.setFont(holder.getTxtBuy(),fontMaterialIcons);
//       FontManager.setFont(holder.getTxtFavorite(),fontMaterialIcons);
//        FontManager.setFont(holder.getTxtShare(),fontMaterialIcons);
//        FontManager.setFont(holder.getTxtLike(),fontMaterialIcons);


        holder.getTxtBarberShopCategory().setText(mItems.get(position));


        holder.getRecBarberShop().setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        initBarberShops(holder);

        holder.getBtnMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BarberShopGridActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("TITLE", mItems.get(position));
                mContext.startActivity(intent);

            }
        });

        FontManager.setFont(holder.getTxtBarberShopCategory(), fontIransans);



    }

    private void initBarberShops(ViewHolder holder) {

        Retrofit retrofit = API.getRetrofit();

        BarbershopServices service = retrofit.create(BarbershopServices.class);

//        Call<List<BarbershopCard>> barbershopCall = service.barbershopsCards(Auth.getAccessKey(mContext));
//
//        barbershopCall.enqueue(new Callback<List<BarbershopCard>>() {
//            @Override
//            public void onResponse(Call<List<BarbershopCard>> call, Response<List<BarbershopCard>> response) {
//                if (response.body() != null) {
//                    ArrayList<BarbershopCard> list = new ArrayList<>();
//                    list.addAll(response.body());
//                    BarberShopMiniItemAdapter adapter = new BarberShopMiniItemAdapter(mContext, list);
//                    Log.d("BARBERSHOP", response.body().toString());
//                    holder.getRecBarberShop().setAdapter(adapter);
//                }
//                    Log.d("BARBERSHOP", call.isCanceled() + " C E " + call.isExecuted() + " "+ response);
//            }
//
//            @Override
//            public void onFailure(Call<List<BarbershopCard>> call, Throwable t) {
//                Log.d("FAILED", t.toString());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

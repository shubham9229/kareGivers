package com.example.karegivers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private List<product> listItems;
    private Context context;

    public ProductsAdapter(List<product> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listcardview,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        product listItem=listItems.get(i);


        viewHolder.textviewname.setText(listItem.getName() );
        viewHolder.textviewdes.setText(listItem.getDescription());
        viewHolder.textviewadd.setText(listItem.getAddress());
        viewHolder.textviewmobile.setText(listItem.getMobile());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
           public TextView textviewname;
           public TextView textviewdes;
           public TextView textviewadd;
           public TextView textviewmobile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textviewname=(TextView)itemView.findViewById(R.id.nameofmechanic);
            textviewdes=(TextView)itemView.findViewById(R.id.description);
            textviewadd=itemView.findViewById(R.id.nameofaddress);
            textviewmobile=itemView.findViewById(R.id.mobileno);
        }
    }
}

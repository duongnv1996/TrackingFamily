package com.software.trackingfamily;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.software.trackingfamily.interfaces.ICallback;
import com.software.trackingfamily.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DuongKK on 3/18/2018.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    List<User> list;
    Context context;
    ICallback<Integer> iCallback;

    public MemberAdapter(List<User> list, Context context, ICallback<Integer> iCallback) {
        this.iCallback = iCallback;
        this.list = list;
        this.context = context;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, final int position) {
        holder.tvName.setText(list.get(position).getUsername());
        if(position!=0 && position%4==0  ){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.itemView.setLayoutParams(params);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iCallback.callBack(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;

        public MemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

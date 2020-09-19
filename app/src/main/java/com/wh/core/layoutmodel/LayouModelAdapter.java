package com.wh.core.layoutmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class LayouModelAdapter extends RecyclerView.Adapter {

    protected ArrayList<BaseBean> mList;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private RecyclerViewOnClickListener onClickListener;
    protected View.OnClickListener buttonClickListener;


    public void setButtonClickListener(View.OnClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    //主要是处理一些特色的
    protected void beforeClick(int position, View view) {

    }


    public LayouModelAdapter(ArrayList<BaseBean> list) {
        this.mList = list;
        onClickListener = new RecyclerViewOnClickListener(this) {
            @Override
            public void onClick(int position, long itemId, View view) {
                beforeClick(position, view);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, itemId);
                }
            }
        };
    }

    public abstract class RecyclerViewOnClickListener implements View.OnClickListener {
        private LayouModelAdapter adapter;

        public RecyclerViewOnClickListener(LayouModelAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public void onClick(View v) {
            XHolder holder = (XHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId(), v);
        }

        public abstract void onClick(int position, long itemId, View view);
    }

    public class XHolder extends RecyclerView.ViewHolder {
        public XHolder(View itemView) {
            super(itemView);
        }


        public <T extends View> T findViewById(int resid) {
            return (T) itemView.findViewById(resid);
        }

    }

    public ArrayList<BaseBean> getList() {
        return mList;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() > 0) {
            int id = mList.get(position).getLayoutId();
            return id;
        }
        return super.getItemViewType(position);
    }

    public void setList(ArrayList<BaseBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mList.remove(index);
        notifyItemRemoved(index);
    }

    public boolean addAll(ArrayList<BaseBean> list) {
        boolean result = mList.addAll(list);
        notifyDataSetChanged();
        return result;
    }

    public void add(BaseBean data) {
        mList.add(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        View view = inflater.inflate(viewType, parent, false);
        XHolder holder = new XHolder(view);
        if (holder != null) {
            holder.itemView.setTag(holder);
            holder.itemView.setOnClickListener(onClickListener);
        }
        return holder;
    }

    protected abstract void onBindViewHolder(XHolder holder, BaseBean baseBean, long position);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof XHolder) {
            XHolder xHolder = (XHolder) holder;
            BaseBean bean = mList.get(position);
            HashMap<String, Object> map = bean.getData();
            onBindViewHolder(xHolder, bean, position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, long itemId);
    }


}

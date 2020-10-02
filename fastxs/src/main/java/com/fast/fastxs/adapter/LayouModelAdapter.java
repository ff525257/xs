package com.fast.fastxs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @ClassName: LayouModelAdapter
 * @Description:layout结构适配器
 * @Author: 范明华
 * @Version: 1.0
 */
public abstract class LayouModelAdapter<T extends LayouModelAdapter.BaseItem> extends RecyclerView.Adapter<LayouModelAdapter.XHolder> {

    protected ArrayList<T> mList;
    protected Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;
    private ArrayList<OnChildItemClickListener> onChildItemClickListeners = new ArrayList<>(0);
    private ArrayList<Integer> onClickIds = new ArrayList<>(0);

    public LayouModelAdapter(@NonNull ArrayList<T> list) {
        this.mList = list;
    }


    public static class XHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> viewArray = new SparseArray<>(0);

        public XHolder(View itemView) {
            super(itemView);
        }

        public <T extends View> T getView(int id) {
            View view = viewArray.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                viewArray.put(id, view);
            }
            return (T) view;
        }

        public void setText(int id, String str) {
            TextView tmp = getView(id);
            tmp.setText(str);
        }

        public ImageView setImageView(int id) {
            return getView(id);
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (mList.size() > 0) {
            int id = mList.get(position).getLayoutId();
            return id;
        }
        return super.getItemViewType(position);
    }

    public void setList(ArrayList<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public ArrayList<T> getList() {
        return mList;
    }

    public void remove(int index) {
        mList.remove(index);
        notifyItemRemoved(index);
    }

    public void addAll(ArrayList<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void add(T data) {
        mList.add(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    @NonNull
    @Override
    public XHolder onCreateViewHolder(@NonNull ViewGroup parent, int layoutId) {
        if (context == null) {
            context = parent.getContext();
        }
        if (inflater == null) {
            inflater = LayoutInflater.from(context);
        }
        View view = inflater.inflate(layoutId, parent, false);
        XHolder holder = new XHolder(view);
        return holder;
    }

    protected abstract void bindViewHolder(XHolder holder, T baseBean, long position);

    @Override
    public void onBindViewHolder(@NonNull final XHolder xHolder, final int position) {
        bindLisenter(xHolder);

        T bean = mList.get(position);
        bindViewHolder(xHolder, bean, position);
    }

    /**
     * 绑定事件
     *
     * @param xHolder
     */
    private void bindLisenter(@NonNull final XHolder xHolder) {
        xHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(xHolder.getAdapterPosition(), xHolder.itemView);
                }
            }
        });

        xHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onLongItemClickListener != null) {
                    onLongItemClickListener.onLongItemClick(xHolder.getAdapterPosition(), xHolder.itemView);
                }
                return false;
            }
        });

        if (onClickIds != null) {
            for (int i = 0; i < onClickIds.size(); i++) {
                final OnChildItemClickListener itemClickListener = onChildItemClickListeners.get(i);
                final View v = xHolder.getView(onClickIds.get(i));
                if (v != null) {
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemClickListener.onItemClick(xHolder.getAdapterPosition(), xHolder.itemView);
                        }
                    });
                }
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    /**
     * 为子控件添加单击事件
     *
     * @param onChildItemClickListener
     * @param id
     */
    public void addChildItemClickListener(int id, OnChildItemClickListener onChildItemClickListener) {
        checkIdAndLisenter(onChildItemClickListener, id);
        onClickIds.add(id);
        onChildItemClickListeners.add(onChildItemClickListener);
    }

    private void checkIdAndLisenter(OnChildItemClickListener onChildItemClickListener, int id) {
        if (id == 0 || onChildItemClickListener == null) {
            new IllegalAccessException("Params is invalidata");
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(int position, View view);
    }

    public interface OnChildItemClickListener {
        void onItemClick(int position, View view);
    }


    /**
     * @ClassName: BaseItem
     * @Description:适用于LayouModelAdapter
     * @Author: 范明华
     * @Version: 1.0
     */
    public static class BaseItem<T> {
        protected int layoutId;
        private T data;

        public int getLayoutId() {
            return layoutId;
        }

        public void setLayoutId(int layoutId) {
            this.layoutId = layoutId;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public BaseItem(int layoutId, T data) {
            this.layoutId = layoutId;
            this.data = data;
        }

        public BaseItem(T data) {
            this.data = data;
        }

    }


}

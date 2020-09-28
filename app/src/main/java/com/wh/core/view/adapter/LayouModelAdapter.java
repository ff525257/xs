package com.wh.core.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
 * @CreateDate: 2020/9/12 0012 11:15
 * @Version: 1.0
 */
public abstract class LayouModelAdapter<T extends LayouModelAdapter.BaseItem> extends RecyclerView.Adapter {

    protected ArrayList<T> mList;
    protected Context context;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;
    private ArrayList<OnChildItemClickListener> onChildItemClickListeners = new ArrayList<>(0);
    private ArrayList<Integer> onClickIds = new ArrayList<>(0);

    public LayouModelAdapter(ArrayList<T> list) {
        this.mList = list;
    }


    public static class XHolder extends RecyclerView.ViewHolder {
        private ArrayList<View> viewList;

        public XHolder(View itemView) {
            super(itemView);
            //存储所有View  优化每次需要findViewById操作
            viewList = getAllChildren(itemView);
        }

        public <T extends View> T findViewById(int id) {
            for (View v : viewList) {
                if (v.getId() != View.NO_ID && v.getId() == id) {
                    return (T) v;
                }
            }
            return null;
        }

        public void setText(int id, String str) {
            TextView tmp = findViewById(id);
            tmp.setText(str);
        }

        public ImageView getImageViewById(int id) {
            ImageView img = findViewById(id);
            return img;
        }


        private ArrayList<View> getAllChildren(View v) {
            if (!(v instanceof ViewGroup)) {
                ArrayList<View> viewArrayList = new ArrayList<View>();
                if (v.getId() != View.NO_ID) {
                    viewArrayList.add(v);
                }
                return viewArrayList;
            }
            ArrayList<View> result = new ArrayList<View>();
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                ArrayList<View> viewArrayList = new ArrayList<View>();
                if (v.getId() != View.NO_ID) {
                    viewArrayList.add(v);
                }
                viewArrayList.addAll(getAllChildren(child));
                result.addAll(viewArrayList);
            }
            return result;
        }
    }

    public ArrayList<T> getList() {
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

    public void setList(ArrayList<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mList.remove(index);
        notifyItemRemoved(index);
    }

    public boolean addAll(ArrayList<T> list) {
        boolean result = mList.addAll(list);
        notifyDataSetChanged();
        return result;
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        XHolder xHolder = (XHolder) holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.getAdapterPosition(), holder.itemView);
                }
            }
        });

        if (onClickIds != null) {
            for (int i = 0; i < onClickIds.size(); i++) {
                final OnChildItemClickListener itemClickListener = onChildItemClickListeners.get(i);
                final View v = xHolder.findViewById(onClickIds.get(i));
                if (v != null) {
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            itemClickListener.onItemClick(holder.getAdapterPosition(), holder.itemView);
                        }
                    });
                }
            }
        }

        T bean = mList.get(position);
        bindViewHolder(xHolder, bean, position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    public interface OnChildItemClickListener {
        void onItemClick(int position, View view);
    }


    /**
     * @ClassName: BaseItem
     * @Description:适用于LayouModelAdapter
     * @Author: 范明华
     * @CreateDate: 2020/9/12 0012 11:15
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

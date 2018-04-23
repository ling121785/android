package toolbox.ll.com.toolbox.ui.base.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import toolbox.ll.com.toolbox.R;

/**
 * Created by root on 2016/6/25.
 */
public abstract  class BaseRListAdapter<K,V extends BaseRListAdapter.ViewHolder> extends RecyclerView.Adapter<V> {
    private List<K> mDatas;
    private Context mContext;
    private OnItemClickListener<K> mItemListener;
    private View.OnClickListener mOnClickListener;
    public BaseRListAdapter(){
    }

    public BaseRListAdapter(Context context, List<K> data, OnItemClickListener<K> itemListener){
        mDatas=data;
        mContext=context;
        mItemListener=itemListener;
        mOnClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mItemListener!=null){
                    mItemListener.onItemListener((int)v.getTag(R.id.position),(K)v.getTag());
                }

            }
        };
    }
    @Override
    public int getItemCount() {
        if(mDatas==null)
            return 0;
        return mDatas.size();
    }

    public K getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public V onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        V holder=createCustomViewHolder(viewGroup,viewType);
        holder.itemView.setOnClickListener(mOnClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(V v, int i) {
        bindDataToViewHolder(v,getItem(i),i);
        v.itemView.setTag(mDatas.get(i));
        v.itemView.setTag(R.id.position,i);
    }


    public abstract V createCustomViewHolder(ViewGroup parent,int viewType);
    public abstract void bindDataToViewHolder(V viewHolder,K data,int postion);

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public List<K> getmDatas() {
        return mDatas;
    }

    public void setDatas(List<K> mDatas) {
            this.mDatas = mDatas;
    }

    public interface OnItemClickListener<K>{
        public void onItemListener(int positon,K data);
    }

    public OnItemClickListener<K> getmItemListener() {
        return mItemListener;
    }

    public void setmItemListener(OnItemClickListener<K> mItemListener) {
        this.mItemListener = mItemListener;
    }
}

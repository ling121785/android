package toolbox.ll.com.toolbox.ui.base.adapters;

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
        return createCustomViewHolder(viewGroup,viewType);
    }

    @Override
    public void onBindViewHolder(V v, int i) {
        bindDataToViewHolder(v,getItem(i),i);
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
}

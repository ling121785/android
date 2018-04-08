package toolbox.ll.com.toolbox.ui.base.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import toolbox.ll.com.toolbox.R;

/**
 * Created by root on 2016/6/25.
 */
public abstract  class BaseListAdapter<K,V extends BaseListAdapter.ViewHolder> extends BaseAdapter {
    private List<K> mDatas;

    @Override
    public int getCount() {
        if(mDatas==null)
            return 0;
        return mDatas.size();
    }

    @Override
    public K getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V viewHolder=null;
        if(convertView==null){
            viewHolder=createViewHolder(parent);
            convertView=viewHolder.itemView;
            convertView.setTag(R.id.viewHolder,viewHolder);
        }else{
            viewHolder=(V)convertView.getTag(R.id.viewHolder);
        }
        viewHolder.itemView.setTag(getItem(position));
        bindDataToViewHolder(viewHolder,(K)getItem(position));
        return convertView;
    }

    public abstract V createViewHolder(ViewGroup parent);
    public abstract void bindDataToViewHolder(V viewHolder,K data);

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setDatas(List<K> datas){
        mDatas=datas;
        notifyDataSetChanged();
    }

    public List<K> getmDatas() {
        return mDatas;
    }
}

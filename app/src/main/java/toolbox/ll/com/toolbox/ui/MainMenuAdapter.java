package toolbox.ll.com.toolbox.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import toolbox.ll.com.toolbox.R;
import toolbox.ll.com.toolbox.bean.MainMenu;
import toolbox.ll.com.toolbox.ui.base.adapters.BaseListAdapter;

/**
 * Created by ll on 2018/3/25.
 */

public class MainMenuAdapter extends BaseListAdapter<MainMenu,MainMenuAdapter.ViewHolder>{
    private Context mContext;
    public MainMenuAdapter(Context context, List<MainMenu> data) {
        this.mContext=context;
        this.setDatas(data);
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.main_item_menu,null);
        return new ViewHolder(view);
    }

    @Override
    public void bindDataToViewHolder(ViewHolder viewHolder, MainMenu data) {
        if(data.getType()==MainMenu.TYPE_NORMAL){
            viewHolder.mTVTitle.setText(data.getName());
        }
    }

    public class ViewHolder extends BaseListAdapter.ViewHolder{
        ImageView mIVIcon;
        TextView mTVTitle;
        ImageView mIVMore;
        public ViewHolder(View itemView) {
            super(itemView);
            mIVIcon=(ImageView)itemView.findViewById(R.id.item_iv_icon);
            mTVTitle=(TextView) itemView.findViewById(R.id.item_tv_name);
            mIVMore=(ImageView)itemView.findViewById(R.id.item_iv_more);
        }
    }
}

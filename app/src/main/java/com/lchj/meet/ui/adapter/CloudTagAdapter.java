package com.lchj.meet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchj.meet.R;
import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.List;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/9/24.
 * 3D球体适配器
 */
public class CloudTagAdapter extends TagsAdapter {
    private Context mContext;
    private List<String>mList;
    private LayoutInflater inflater;

    public CloudTagAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
       View view= inflater.inflate(R.layout.layout_star_view_item,null);
        ImageView mIvStar= view.findViewById(R.id.mIvStar);
        TextView mTvStar= view.findViewById(R.id.mTvStar);
        mTvStar.setText(mList.get(position));
        switch (position%10){
            case 1:
                mIvStar.setImageResource(R.drawable.icon_star_1);
                break;
            case 2:
                mIvStar.setImageResource(R.drawable.icon_star_2);
                break;
            case 3:
                mIvStar.setImageResource(R.drawable.icon_star_3);
                break;
            case 4:
                mIvStar.setImageResource(R.drawable.icon_star_4);
                break;
            case 5:
                mIvStar.setImageResource(R.drawable.icon_star_5);
                break;
            case 6:
                mIvStar.setImageResource(R.drawable.icon_star_6);
                break;
            default:
                mIvStar.setImageResource(R.drawable.icon_star_7);
                break;

        }
        return view;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }
}

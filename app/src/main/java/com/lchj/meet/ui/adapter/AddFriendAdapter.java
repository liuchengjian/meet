package com.lchj.meet.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchj.meet.R;
import com.lchj.meet.model.AddFriendMode;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/9/24.
 */
public class AddFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_CONTENT = 1;

    private Context mContext;
    private List<AddFriendMode> mList;
    private LayoutInflater inflater;


    public AddFriendAdapter(Context mContext, List<AddFriendMode> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TITLE) {
            return new TitleViewHolder(inflater.inflate(R.layout.loyout_search_title_item, null));
        } else {
            return new ContentViewHolder(inflater.inflate(R.layout.loyout_search_content_item, null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddFriendMode mode = mList.get(position);
        if (mode.getType() == TYPE_TITLE) {
            ((TitleViewHolder) holder).mTvTitle.setText(mode.getTitle());
        } else {
            ((ContentViewHolder) holder).mTvName.setText(mode.getUserName());
            ((ContentViewHolder) holder).mTvAge.setText(mode.getAge()+" 岁");
            ((ContentViewHolder) holder).mTvDesc.setText(TextUtils.isEmpty(mode.getDesc())?"暂无":mode.getDesc());
        }
        //点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.mTvTitle);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvUser;
        private TextView mTvName;
        private TextView mTvAge;
        private TextView mTvDesc;

        public ContentViewHolder(View itemView) {
            super(itemView);
            mIvUser = itemView.findViewById(R.id.mIvUser);
            mTvName = itemView.findViewById(R.id.mTvName);
            mTvAge = itemView.findViewById(R.id.mTvAge);
            mTvDesc = itemView.findViewById(R.id.mTvDesc);
        }
    }

    public interface OnClickListener {
        void onClick(int pos);
    }
}

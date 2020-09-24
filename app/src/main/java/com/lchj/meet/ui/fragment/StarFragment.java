package com.lchj.meet.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lchj.meet.R;
import com.lchj.meet.ui.AddFriendActivity;
import com.lchj.meet.ui.ScanActivity;
import com.lchj.meet.ui.adapter.CloudTagAdapter;
import com.lchj.meet.utils.LiuUtils;
import com.moxun.tagcloudlib.view.TagCloudView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StarFragment#newInstance} factory method to
 * 星球界面
 */
public class StarFragment extends BaseFragment {
    @BindView(R.id.mTagCloudView)
    TagCloudView mTagCloudView;
    private CloudTagAdapter mCloudTagAdapter;
    private List<String> mListStar = new ArrayList<>();

    public static StarFragment newInstance() {
        StarFragment fragment = new StarFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int contentViewId() {
        return R.layout.fragment_star;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {
        for (int i = 0; i < 100; i++) {
            mListStar.add("star" + i);
        }
        mCloudTagAdapter = new CloudTagAdapter(getActivity(), mListStar);
        mTagCloudView.setAdapter(mCloudTagAdapter);
        mCloudTagAdapter.notifyDataSetChanged();
        mTagCloudView.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                LiuUtils.makeText(getActivity(), mListStar.get(position));
            }
        });
    }

    @OnClick({R.id.mIvAdd,R.id.mIvScan})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.mIvScan:
                Intent scanIntent = new Intent(getActivity(), ScanActivity.class);
                getActivity().startActivity(scanIntent);
                break;
            case R.id.mIvAdd:
                Intent intent = new Intent(getActivity(), AddFriendActivity.class);
                getActivity().startActivity(intent);
                break;

        }
    }
}
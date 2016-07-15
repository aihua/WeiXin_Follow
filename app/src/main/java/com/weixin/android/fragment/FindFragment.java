package com.weixin.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.weixin.android.R;
import com.weixin.android.activity.MainActivity;

/**
 * Created by sujizhong on 16/7/11.
 */
public class FindFragment extends AppBaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_three, null, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).setTabThreeRemind("200", true);
        init();
    }

    private void init() {
        View v = getView();
        RelativeLayout friend = (RelativeLayout) v.findViewById(R.id.rela_friend);
        friend.setOnClickListener(mOnClickListener);
        RelativeLayout sys = (RelativeLayout) v.findViewById(R.id.rela_sys);
        sys.setOnClickListener(mOnClickListener);
        RelativeLayout yay = (RelativeLayout) v.findViewById(R.id.rela_yay);
        yay.setOnClickListener(mOnClickListener);
        RelativeLayout fjr = (RelativeLayout) v.findViewById(R.id.rela_fjr);
        fjr.setOnClickListener(mOnClickListener);
        RelativeLayout plp = (RelativeLayout) v.findViewById(R.id.rela_plp);
        plp.setOnClickListener(mOnClickListener);
        RelativeLayout gw = (RelativeLayout) v.findViewById(R.id.rela_gw);
        gw.setOnClickListener(mOnClickListener);
        RelativeLayout yx = (RelativeLayout) v.findViewById(R.id.rela_yx);
        yx.setOnClickListener(mOnClickListener);

    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.rela_friend:
                    break;
                case R.id.rela_sys:
                    break;

                case R.id.rela_yay:
                    break;

                case R.id.rela_fjr:
                    break;

                case R.id.rela_plp:
                    break;

                case R.id.rela_gw:
                    break;

                case R.id.rela_yx:
                    break;

                default:
                    break;
            }

        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
}

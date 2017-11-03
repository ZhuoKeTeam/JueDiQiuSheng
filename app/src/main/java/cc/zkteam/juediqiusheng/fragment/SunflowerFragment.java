package cc.zkteam.juediqiusheng.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cc.zkteam.juediqiusheng.R;

/**
 * Created by WangQing on 2017/10/30.
 */

public class SunflowerFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public SunflowerFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SunflowerFragment newInstance(String text) {
        SunflowerFragment fragment = new SunflowerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rec_sunflower, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.text);
        textView.setText(getString(R.string.section_format, getArguments().getString(ARG_SECTION_NUMBER)));
        return rootView;
    }
}

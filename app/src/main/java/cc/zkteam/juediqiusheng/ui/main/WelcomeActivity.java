package cc.zkteam.juediqiusheng.ui.main;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

import cc.zkteam.juediqiusheng.R;
import cc.zkteam.juediqiusheng.activity.BaseActivity;
import cc.zkteam.juediqiusheng.view.ZKViewPager;
import cc.zkteam.juediqiusheng.view.ZKViewPagerIndicator;

/**
 * Created by renxuelong on 2019-06-19
 */
public class WelcomeActivity extends BaseActivity {

    private TextView btnStart;
    private ZKViewPager zkViewPager;
    private ZKViewPagerIndicator zkViewPagerIndicator;

    private int[] ids = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViews() {
        btnStart = findViewById(R.id.btn_welcome_start);
        zkViewPager = findViewById(R.id.pager_welcome);
        zkViewPagerIndicator = findViewById(R.id.indicator_welcome);

        ids[0] = R.drawable.w1;
        ids[1] = R.drawable.w2;
        ids[2] = R.drawable.w3;
        ids[3] = R.drawable.w4;

        zkViewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return ids.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public ImageView instantiateItem(ViewGroup container, int position) {

                ImageView imageView = new ImageView(WelcomeActivity.this);
                imageView.setImageResource(ids[position]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ImageView imageView = (ImageView) object;
                container.removeView(imageView);
            }
        });

        zkViewPagerIndicator.setUpWithViewPager(zkViewPager);
        zkViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                btnStart.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}

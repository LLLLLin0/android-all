package com.ss.android.bytedance.view.viewpager;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.ss.android.bytedance.R;
import com.ss.android.bytedance.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 2019-09-12.
 * linzhipeng.1996@bytedance.com
 */
public class TestActivity extends AppCompatActivity {

    @BindView(R2.id.tabLayout) TabLayout tabLayout;
    @BindView(R2.id.pager) ViewPager pager;
    List<PageModel> pageModels = new ArrayList<>();

    {
//        pageModels.add(new PageModel());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_viewpager_activity);
        ButterKnife.bind(this);

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return PageFragment.newInstance(pageModel.sampleLayoutRes, pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pageModels.get(position).titleRes);
            }
        });

        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    private class PageModel {
        @LayoutRes int sampleLayoutRes;
        @StringRes int titleRes;
        @LayoutRes int practiceLayoutRes;

        PageModel(@LayoutRes int sampleLayoutRes, @StringRes int titleRes, @LayoutRes int practiceLayoutRes) {
            this.sampleLayoutRes = sampleLayoutRes;
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }
}

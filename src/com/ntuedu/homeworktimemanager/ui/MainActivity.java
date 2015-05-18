package com.ntuedu.homeworktimemanager.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ntuedu.homeworktimemanager.R;
import com.ntuedu.homeworktimemanager.widget.PagerSlidingTabStrip;

@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ShareActionProvider mShareActionProvider;
	private PagerSlidingTabStrip mPagerSlidingTabStrip;
	private ViewPager mViewPager;
	private Toolbar mToolbar;
	private ListView lvLeftMenu;

	private ArrayList<HashMap<String, Object>> listItems;
	private SimpleAdapter listItemAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homework_time_main);
		initViews();
	}

	@SuppressLint("ShowToast")
	private void initViews() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		// �������������setSupportActionBar֮ǰ����Ȼ����Ч
		mToolbar.setTitle(getResources().getString(R.string.app_name));
		mToolbar.setTitleTextColor(Color.WHITE);
		setSupportActionBar(mToolbar);

		/* �˵��ļ���������toolbar�����ã�Ҳ������ActionBar������ͨ������������ص����������� */
		mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.action_settings:
					Toast.makeText(MainActivity.this, "action_settings", 0)
							.show();
					break;
				default:
					break;
				}
				return true;
			}
		});

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// ���ò˵��б�
		lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
		View headerContainer = LayoutInflater.from(this).inflate(
				R.layout.siderbar_header, lvLeftMenu, false);

		lvLeftMenu.addHeaderView(headerContainer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
		listItems = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemTitle", "����");
			map.put("ItemImage", R.drawable.ic_drawer_settings);
			listItems.add(map);
		}

		listItemAdapter = new SimpleAdapter(this, listItems,
				R.layout.siderbar_lisetview_item, new String[] { "ItemTitle",
						"ItemImage" }, new int[] { R.id.ItemTitle,
						R.id.ItemImage });

		lvLeftMenu.setAdapter(listItemAdapter);
		lvLeftMenu.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO �Զ����ɵķ������

				Toast.makeText(MainActivity.this,position+"", 1000).show();

			}

		});
		
		
		
		
		// ������ҳ
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				mToolbar, R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		mPagerSlidingTabStrip.setViewPager(mViewPager);
		mPagerSlidingTabStrip
				.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						// colorChange(arg0);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
					}
				});
		initTabsValue();
	}

	private void initTabsValue() {
		// �ײ��α���ɫ
		mPagerSlidingTabStrip.setIndicatorColor(Color.WHITE);

		// tab�ķָ�����ɫ
		mPagerSlidingTabStrip.setDividerColor(Color.TRANSPARENT);
		// tab����
		mPagerSlidingTabStrip.setBackgroundColor(Color.parseColor("#6699FF"));

		mPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources()
						.getDisplayMetrics()));

		// ����ÿ������
		mPagerSlidingTabStrip.setShouldExpand(true);

		// �α�߶�
		mPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources()
						.getDisplayMetrics()));
		// ѡ�е�������ɫ
		mPagerSlidingTabStrip.setSelectedTextColor(Color.WHITE);
		// ����������ɫ
		mPagerSlidingTabStrip.setTextColor(Color.BLACK);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		/* ShareActionProvider���� */
		// mShareActionProvider = (ShareActionProvider) MenuItemCompat
		// .getActionProvider(menu.findItem(R.id.action_share));
		// Intent intent = new Intent(Intent.ACTION_SEND);
		// intent.setType("text/*");
		// mShareActionProvider.setShareIntent(intent);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// switch (item.getItemId()) {
		// case R.id.action_settings:
		// Toast.makeText(MainActivity.this, "action_settings", 0).show();
		// break;
		// case R.id.action_share:
		// Toast.makeText(MainActivity.this, "action_share", 0).show();
		// break;
		// default:
		// break;
		// }
		return super.onOptionsItemSelected(item);
	}

	/* ***************FragmentPagerAdapter***************** */
	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "��ҵʱ��", "�ɼ�����", "ʱ����ɼ�" };

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {

			return Fragement1.newInstance(position);

		}

	}

}

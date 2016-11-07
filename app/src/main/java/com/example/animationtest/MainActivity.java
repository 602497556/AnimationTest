package com.example.animationtest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        navViewToTop();
        initNavigationView();
    }

    private void initNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_item1:
                        myStartActivity(MenuActivity.class);
                        break;
                    case R.id.nav_item2:
                        myStartActivity(TimerActivity.class);
                        break;
                    case R.id.nav_item3:
                        myStartActivity(DropActivity.class);
                        break;
                    case R.id.nav_item4:
                        myStartActivity(PanelActivity.class);
                        break;
                    case R.id.nav_item5:
                        myStartActivity(CityActivity.class);
                        break;
                    case R.id.nav_item6:
                        myStartActivity(FlikerProgressBarActivity.class);
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void myStartActivity(Class<?> clazz){
        Intent intent = new Intent(MainActivity.this,clazz);
        startActivity(intent);
    }


    /**
     * 初始化
     */
    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mToolBar = (Toolbar) findViewById(R.id.main_tb);
        setSupportActionBar(mToolBar);
        setUpDrawer();
    }

    private void setUpDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerLayout,mToolBar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 使4.4以下的系统，NavigationView的颜色填充到状态栏
     */
    private void navViewToTop() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            drawerLayout.setFitsSystemWindows(true);
            drawerLayout.setClipToPadding(false);
        }
    }

    /**
     * 演示硬币掉入钱包
     *
     * @param view
     */
    public void btnCoinDrop(View view){
        myStartActivity(CoinDropActivity.class);
    }


    /**
     * 演示3D ViewPager
     *
     * @param view
     */
    public void btnGallery(View view){
        myStartActivity(VPGalleryActivity.class);
    }

    /**
     * 演示自定义的Camera
     *
     * @param view
     */
    public void btnCamera(View view){
        myStartActivity(CameraActivity.class);
    }

    /**
     * 演示LauncherView
     *
     * @param view
     */
    public void btnLauncherView(View view){
        myStartActivity(LauncherViewActivity.class);
    }

    /**
     * 演示PolygonsView
     *
     * @param view
     */
    public void btnPolygonsView(View view){
        myStartActivity(PolygonsViewActivity.class);
    }

}

package account.rb.com.elite_agent;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import account.rb.com.elite_agent.Dashboard.DashBoardFragment;
import account.rb.com.elite_agent.core.model.LoginEntity;
import account.rb.com.elite_agent.core.model.UserEntity;
import account.rb.com.elite_agent.database.DataBaseController;
import account.rb.com.elite_agent.login.ChangePasswordFragment;
import account.rb.com.elite_agent.login.loginActivity;
import account.rb.com.elite_agent.pendingDetail.PendingTaskDetailFragment;
import account.rb.com.elite_agent.product.ProductFragment;
import account.rb.com.elite_agent.splash.PrefManager;
import account.rb.com.elite_agent.taskDetail.TaskDetailFragment;


public class HomeActivity extends BaseActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    boolean doubleBackToExitPressedOnce = false;
    private Toolbar toolbar;
    public static int navItemIndex = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    DataBaseController dataBaseController;
    UserEntity loginEntity;

    private static final String TAG_HOME = "Home";
    private static final String TAG_TASK = "Task List";
    private static final String TAG_CHANGE_PWD = "Change Password";
    private static final String TAG_PENDING_TASK = "Pending Task";
    public static String CURRENT_TAG = TAG_HOME;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        dataBaseController = new DataBaseController(HomeActivity.this);
        loginEntity = dataBaseController.getUserData();
        prefManager = new PrefManager(this);
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment("Home");
        }

    }

    private void setUpNavigationView() {
        navigationView.setItemIconTintList(null);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                Intent intent1;
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;

                    case R.id.nav_pending:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PENDING_TASK;
                        break;


                    case R.id.nav_task:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_TASK;
                        break;

                    case R.id.nav_change_pwd:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CHANGE_PWD;
                        break;



                    case R.id.nav_logout:

                        dataBaseController.logout();
                        clear();
                        Intent intent = new Intent(HomeActivity.this, loginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;

                    default:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;


                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment(CURRENT_TAG);
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    private Fragment getHomeFragment() {
        Fragment fragment = null;
        switch (navItemIndex) {
            case 0:
                // home
                fragment = new DashBoardFragment();
                getSupportActionBar().setTitle("Elite Agent");
                return fragment;

            case 1:
                // Pending List
                fragment = new PendingTaskDetailFragment();
                getSupportActionBar().setTitle("Pending Detail");
                return fragment;


            case 2:
                // Task List
                fragment = new TaskDetailFragment();
                getSupportActionBar().setTitle("Task List");
                return fragment;

            case 3:
                fragment = new ChangePasswordFragment();
                getSupportActionBar().setTitle("Change  Password");
                return fragment;


            default:
                fragment = new DashBoardFragment();
                getSupportActionBar().setTitle("Elite Agent");
                return fragment;
        }
    }

    @SuppressLint("RestrictedApi")
    private void loadHomeFragment(String title) {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle(title);

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        // toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment(CURRENT_TAG);
                return;
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    dataBaseController.logout();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }

        // super.onBackPressed();
    }

    private void clear()
    {
        prefManager.setMobile("");
        prefManager.setPassword("");
    }


}

package com.moveitech.riderapp

import  android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.moveitech.riderapp.utils.DataStoreHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStoreHelper: DataStoreHelper
    private lateinit var navController: NavController
    private var isAdmin= false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!
        navController = navHostFragment.navController


        getDataFromStore()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_rider -> {
                navController.navigate(R.id.riderListFragment)
            }
            R.id.action_rider_role -> {
                navController.navigate(R.id.roleListFragment)
            }
            R.id.action_order -> {
                navController.navigate(R.id.addOrderFragment)
            }
            R.id.action_logout -> {
                lifecycleScope.launch {
                    dataStoreHelper.clear()
                    navController.navigate(R.id.splashFragment)
                }
            }
        }

        return true

    }

    fun getDataFromStore()
    {
        lifecycleScope.launch {
            dataStoreHelper.user.collect {
                isAdmin = it.RoleName== "Admin"
                runOnUiThread{
                    invalidateOptionsMenu()
                }
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (!isAdmin)
        {
            if (menu != null) {
                menu.findItem(R.id.action_rider).isVisible= false
                menu.findItem(R.id.action_rider_role).isVisible= false
                menu.findItem(R.id.action_order).isVisible= false
            }
        }else
        {
            if (menu != null) {
                menu.findItem(R.id.action_rider).isVisible= true
                menu.findItem(R.id.action_rider_role).isVisible= true
                menu.findItem(R.id.action_order).isVisible= true
            }
        }


        return true
    }

}
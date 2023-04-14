package cr.ac.menufragment


import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var drawerLayout: DrawerLayout
    lateinit var markerLabel: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        markerLabel = "Ubicación actual"
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)

        var toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(this)
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var title: Int? = null
        var fragment: Fragment? = null
        when (menuItem.getItemId()) {
            R.id.nav_camera -> {
                title = R.string.menu_camera
                fragment = MapsFragment()
            }
            R.id.nav_gallery -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                AlertDialog.Builder(this)
                .setMessage(R.string.integrantes)
                    .setTitle(R.string.menu_gallery)
                    .setPositiveButton("OK") { d, i ->
                        d.dismiss()
                    }
                    .create().show()
            }
            R.id.nav_manage -> {
                title = R.string.menu_tools
                fragment = ConfiguracionFragment()
            }

            else -> throw IllegalArgumentException("menu option not implemented!!")
        }
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                //.setCustomAnimations(R.anim.bottom_nav_enter, R.anim.bottom_nav_exit)
                .replace(R.id.home_content, fragment)
                .commit()
            setTitle(title?.let { getString(it) })
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        return true
    }
}
package com.example.moviestore

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.moviestore.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var badgeDrawable: BadgeDrawable
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieStore)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.root, R.string.open, R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        binding.viewPager.adapter = OrdersPagerAdapter(this)

        tabLayoutMediator = TabLayoutMediator(
            binding.tabLayout, binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy(
            ) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Movie"
                        tab.setIcon(R.drawable.movies_icon)
                    }
                    1 -> {
                        tab.text = "Favorite"
                        tab.setIcon(R.drawable.favorite_icon)
                        badgeDrawable = tab.orCreateBadge
                        badgeDrawable.backgroundColor =
                            ContextCompat.getColor(applicationContext, R.color.colorAccent)
                        setVisible(true)
                        badgeDrawable.maxCharacterCount = 3
                    }
                    2 -> {
                        tab.text = "Setting"
                        tab.setIcon(R.drawable.settings_icon)
                    }
                    3 -> {
                        tab.text = "About"
                        tab.setIcon(R.drawable.about_icon)
                    }
                }
            })
        tabLayoutMediator.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                badgeDrawable = binding.tabLayout.getTabAt(position)!!.orCreateBadge
                badgeDrawable.isVisible = false
                when (position) {
                    0 -> {
                        title = "MovieStore"
                    }
                    1 -> {
                        title = "Favorite"
                    }
                    2 -> {
                        title = "Setting"
                    }
                    3 -> {
                        title = "About"
                    }
                }
                super.onPageSelected(position)
            }
        })

        Fragment_nav_item_BtnEdit.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    EditProfileActivity::class.java
                )
            )
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        val id = item.itemId
        if (id == R.id.action_menu) {
            item.setIcon(R.drawable.ic_list)
        } else {
            item.setIcon(R.drawable.ic_grid)
        }
        return super.onOptionsItemSelected(item)
    }

}


package com.zkatemor.kudago.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.zkatemor.kudago.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.zkatemor.kudago.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        setData()

        createMapView()
    }

    private fun setData() {
        val data = intent.extras
        text_view_title.text = data.getString("title")
        text_view_short_description.text = data.getString("description")
        text_view_full_description.text = data.getString("fullDescription")

        if (data.getString("place") != "")
            text_view_location.text = data.getString("place")
        else
            location_layout.visibility = View.GONE

        if (data.getString("date") != "")
            text_view_date.text = data.getString("date")
        else
            date_layout.visibility = View.GONE

        if (data.getString("price") != "")
            text_view_cost.text = data.getString("price")
        else
            cost_layout.visibility = View.GONE

        val images = data.get("images") as ArrayList<String>

        if (images.size > 0) {
            val viewPager = view_pager
            val viewPagerAdapter = ViewPagerAdapter(this, images)
            viewPager.adapter = viewPagerAdapter
            tab_view_pager.setupWithViewPager(viewPager)
        }

    }

    private fun createMapView() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun onClickArrow(v: View) {
        onBackPressed()
    }
}
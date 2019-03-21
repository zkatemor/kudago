package com.zkatemor.kudago.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
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
import com.zkatemor.kudago.util.Tools

class EventActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private var coordinates: ArrayList<Double> = ArrayList()
    private val tools: Tools by lazy(LazyThreadSafetyMode.NONE) { Tools(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        setData()
    }

    private fun setData() {
        val data = intent.extras
        text_view_title.text = data.getString("title")

        text_view_short_description.text = tools.getSpanned(data.getString("description"))
        text_view_full_description.text = tools.getSpanned(data.getString("fullDescription"))

        if (data.getString("place") != "")
            text_view_location.text = data.getString("place")
        else {
            location_layout.visibility = View.GONE
            map_frame.visibility = View.GONE
        }

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

        coordinates = data.get("coordinates") as ArrayList<Double>

        if (coordinates.size != 0)
            createMapView()
        else
            map_frame.visibility = View.GONE

        text_view_short_description.movementMethod = LinkMovementMethod.getInstance()
        text_view_short_description.text = text_view_short_description.text.trim()
        text_view_full_description.movementMethod = LinkMovementMethod.getInstance()
        text_view_full_description.text = text_view_full_description.text.trim()
    }

    private fun createMapView() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_view) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val position = LatLng(coordinates[0], coordinates[1])

        googleMap.addMarker(MarkerOptions().position(position)
            .icon(tools.bitmapDescriptorFromVector(this, R.drawable.ic_map)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0F))
    }

    fun onClickRouteButton(v: View){
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse("http://maps.google.com/maps?saddr=My+Location&daddr=" +
                    coordinates[0].toString() + "," + coordinates[1].toString()))
        startActivity(intent)
    }

    fun onClickArrow(v: View) {
        onBackPressed()
    }
}
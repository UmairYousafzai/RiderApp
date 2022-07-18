package com.moveitech.riderapp.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.moveitech.riderapp.R
import com.moveitech.riderapp.dataModel.location.TrackingData
import com.moveitech.riderapp.databinding.FragmentTrackingMapBinding
import com.moveitech.riderapp.utils.DataStoreHelper
import com.moveitech.riderapp.utils.hideToolbar
import com.moveitech.riderapp.viewModel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TrackMapFragment : BaseFragment<FragmentTrackingMapBinding>(), OnMapReadyCallback {

    @Inject
    lateinit var dataStore: DataStoreHelper
    private lateinit var mMap: GoogleMap
    private val viewModel: OrderViewModel by viewModels()


    override fun initViews() {

        hideToolbar()
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.track_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        arguments?.let {
            val id= TrackMapFragmentArgs.fromBundle(it).trackingId
            getTrackData(id)

        }

    }

    private fun getTrackData(id: String) {
        viewModel.getTrackingData(id,true)

    }


    override fun liveDataObserver() {

        with(viewModel)
        {
            trackingResponse.observe(viewLifecycleOwner)
            {
                it.getContentIfNotHandled().let {
                    drawPolyLine(it)
                }
            }
        }
    }

    private fun drawPolyLine(it: ArrayList<TrackingData>?) {

        val latLngList = ArrayList<LatLng>()
        if (it != null) {
            for (model in it) {
                if (model.Latitude.isEmpty().not() && model.Longitude.isEmpty().not()) {

                    val marker =
                        LatLng(model.Latitude.toDouble(), model.Longitude.toDouble())
                    latLngList.add(marker)

                }
            }
        }
        if (mMap != null) {
            val icon: BitmapDescriptor? = BitmapFromVector()

            val polyline1 = mMap.addPolyline(
                PolylineOptions()
                    .clickable(true)
                    .addAll(latLngList)
                    .color(resources.getColor(R.color.blue_text))
            )
            mMap.addMarker(
                MarkerOptions().position(latLngList[latLngList.size - 1]).title("End").icon(icon)
            )

            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    latLngList[latLngList.size - 1],
                    12.0f
                )
            )

        }

    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTrackingMapBinding.inflate(layoutInflater, container, false)


    override fun btnListener() {
    }

    override fun onMapReady(p0: GoogleMap) {

        mMap = p0
        val polyline1 = mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    LatLng(-35.016, 143.321),
                    LatLng(-34.747, 145.592),
                    LatLng(-34.364, 147.891),
                    LatLng(-33.501, 150.217),
                    LatLng(-32.306, 149.248),
                    LatLng(-32.491, 147.309)
                )
        )
    }

    private fun BitmapFromVector(): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable =
            ContextCompat.getDrawable(
                requireContext(),
                com.moveitech.riderapp.R.drawable.current_location
            )

        // below line is use to set bounds to our vector drawable.
        vectorDrawable?.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        var bitmap: Bitmap? = null
        if (vectorDrawable != null) {
            bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }

        // below line is use to add bitmap in our canvas.
        val canvas = bitmap?.let { Canvas(it) }

        // below line is use to draw our
        // vector drawable in canvas.
        if (canvas != null) {
            vectorDrawable?.draw(canvas)
        }

        // after generating our bitmap we are returning our bitmap.
        return if (bitmap != null) {
            BitmapDescriptorFactory.fromBitmap(bitmap)
        } else {
            null
        }
    }


}
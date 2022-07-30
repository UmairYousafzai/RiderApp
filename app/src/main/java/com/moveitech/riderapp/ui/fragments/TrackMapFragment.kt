package com.moveitech.riderapp.ui.fragments

import android.graphics.Bitmap
import com.moveitech.riderapp.utils.showSnackBar

import android.graphics.Canvas
import android.graphics.drawable.Drawable
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
            val id = TrackMapFragmentArgs.fromBundle(it).trackingId
            getTrackData(id)

        }

    }

    private fun getTrackData(id: String) {
        viewModel.getTrackingData(id, true)

    }


    override fun liveDataObserver() {

        with(viewModel)
        {
            trackingResponse.observe(viewLifecycleOwner)
            {
                if ((it.getContentIfNotHandled()?.size ?: 0) != 0) {
                    drawPolyLine(it.getContentIfNotHandled())

                } else {
                    showSnackBar("Track data is empty")
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

        mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .addAll(latLngList)
                .color(
                    ContextCompat.getColor(requireContext(), R.color.blue_text)
                )
        )
        var icon: BitmapDescriptor? = bitmapFromVector(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_start_location
            )
        )

        mMap.addMarker(
            MarkerOptions().position(latLngList[0]).title("Start").icon(icon)
        )
        icon = bitmapFromVector(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_end_location
            )
        )

        mMap.addMarker(
            MarkerOptions().position(latLngList[latLngList.size - 1]).title("End").icon(icon)
        )

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLngList[latLngList.size - 1],
                15.0f
            )
        )

    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTrackingMapBinding.inflate(layoutInflater, container, false)


    override fun btnListener() {
    }

    override fun onMapReady(p0: GoogleMap) {

        mMap = p0

    }

    private fun bitmapFromVector(vectorDrawable: Drawable?): BitmapDescriptor? {

        vectorDrawable?.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        var bitmap: Bitmap? = null
        if (vectorDrawable != null) {
            bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }

        val canvas = bitmap?.let { Canvas(it) }

        if (canvas != null) {
            vectorDrawable?.draw(canvas)
        }

        return if (bitmap != null) {
            BitmapDescriptorFactory.fromBitmap(bitmap)
        } else {
            null
        }
    }


}
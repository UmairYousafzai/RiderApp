package com.moveitech.riderapp.ui.fragments.maps

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.moveitech.riderapp.R
import com.moveitech.riderapp.databinding.FragmentMapBinding
import com.moveitech.riderapp.ui.fragments.BaseFragment
import com.moveitech.riderapp.utils.DataStoreHelper
import com.moveitech.riderapp.utils.getCompleteAddressString
import com.moveitech.riderapp.utils.hideToolbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(),OnMapReadyCallback {

    @Inject
    lateinit var dataStore: DataStoreHelper
    private lateinit var mMap: GoogleMap
    private  lateinit var lat :String
    private  lateinit var long :String

    override fun initViews() {

        hideToolbar()
            val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

        arguments?.let { lat= MapFragmentArgs.fromBundle(it).latitude
            long= MapFragmentArgs.fromBundle(it).longitude
            val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
    }


    override fun liveDataObserver() {
    }

    override fun getFragmentBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMapBinding.inflate(layoutInflater, container, false)


    override fun btnListener() {
    }

    override fun onMapReady(p0: GoogleMap) {

        mMap= p0

        if (lat.isEmpty().not()&& long.isEmpty().not())
         {
            val address=requireContext().getCompleteAddressString(lat.toDouble() ?: 0.0, long.toDouble()
                ?: 0.0)
            val marker = LatLng(lat.toDouble() ?: 0.0, long.toDouble() ?: 0.0)
            mMap.addMarker(
                MarkerOptions()
                .position(marker)
                .title(address))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker,15.0f))
        }

    }


}
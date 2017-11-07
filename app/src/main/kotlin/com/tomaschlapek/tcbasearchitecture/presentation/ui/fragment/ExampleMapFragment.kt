package com.tomaschlapek.tcbasearchitecture.presentation.ui.fragment

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.jetbrains.anko.support.v4.toast

/**
 * Map fragment.
 */
class ExampleMapFragment : SupportMapFragment(), OnMapReadyCallback {

  private lateinit var mMap: GoogleMap

  override fun onMapReady(map: GoogleMap?) {
    mMap = map as GoogleMap;
    toast("OnMapReady end")
  }

}
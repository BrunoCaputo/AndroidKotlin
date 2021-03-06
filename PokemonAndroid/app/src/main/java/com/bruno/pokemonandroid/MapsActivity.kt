package com.bruno.pokemonandroid

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPermission()
        loadPokemon()
    }

    private var accessLocation = 123

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat
                    .checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    accessLocation
                )
                return
            }
        }

        getUserLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        Toast.makeText(this, "User location access on", Toast.LENGTH_LONG).show()

        val myLocation = MyLocationListener()

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3f, myLocation)

        val myThread = MyThread()
        myThread.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            accessLocation -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLocation()
                } else {
                    Toast.makeText(this, "Cannot access to your location", Toast.LENGTH_LONG).show()
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    var location: Location? = null

    inner class MyLocationListener : LocationListener {
        init {
            location = Location("Start")
            location!!.longitude = 0.0
            location!!.latitude = 0.0
        }

        override fun onLocationChanged(loc: Location?) {
            location = loc
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }

    }

    var oldLocation: Location? = null

    inner class MyThread : Thread() {
        init {
            oldLocation = Location("Start")
            oldLocation!!.longitude = 0.0
            oldLocation!!.latitude = 0.0
        }

        override fun run() {

            while (true) {
                try {
                    if (oldLocation!!.distanceTo(location) == 0f) {
                        continue
                    }
                    runOnUiThread {
                        mMap.clear()

                        val sydney = LatLng(location!!.latitude, location!!.longitude)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(sydney)
                                .title("Me")
                                .snippet("Here is my location")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario))
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14f))

                        for (i in 0 until pokemonList.size) {
                            val newPokemon = pokemonList[i]

                            if (newPokemon.isCatch == false) {
                                val pokemonLocation = LatLng(
                                    newPokemon.location!!.latitude,
                                    newPokemon.location!!.longitude
                                )
                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(pokemonLocation)
                                        .title(newPokemon.name)
                                        .snippet(newPokemon.description + ", power: " + newPokemon.power.toString())
                                        .icon(BitmapDescriptorFactory.fromResource(newPokemon.image!!))
                                )

                                if (location!!.distanceTo(newPokemon.location) < 2) {
                                    newPokemon.isCatch = true
                                    pokemonList[i] = newPokemon
                                    playerPower += newPokemon.power!!
                                    Toast.makeText(applicationContext, "You got the pokémon! Your power is: $playerPower", Toast.LENGTH_LONG)
                                }
                            }
                        }
                    }

                    sleep(1000)
                } catch (ex: Exception) {
                }
            }
        }

    }

    var playerPower = 0.0
    var pokemonList = ArrayList<Pokemon>()

    fun loadPokemon() {
        pokemonList.add(Pokemon(R.drawable.charmander, "Charmander", "Fogo", 55.0, 37.77, -122.4))
        pokemonList.add(Pokemon(R.drawable.bulbasaur, "Bulbasaur", "Planta", 90.5, 37.79, -122.41))
        pokemonList.add(Pokemon(R.drawable.squirtle, "Squirtle", "Água", 33.5, 37.78, -122.4123))
    }
}

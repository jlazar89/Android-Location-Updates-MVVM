# Location Updates
Demonstrates how to use the FusedLocationProviderClient API to get updates about a device's location. The Fused Location Provider is part of the Google Play services Location APIs.

# Introduction
This is a simple demo where it showcases getting location updates using Android MVVM architetcure and plotted on the google map.

Steps To get Location updates
- Prepare the map Async    
```
mapFragment.getMapAsync { googleMap ->
            ...
        }
```
- ```onMapReady``` callback ask user for location permission 
ask Location permission 
- on Location Permission granted check if user has Location setting turned on
- Once you are all set, then call ```fusedLocationClient.requestLocationUpdates()``` to get current updates 
- Plot the latlong on the map



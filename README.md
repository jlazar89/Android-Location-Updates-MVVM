# Location Updates
Demonstrates how to use the FusedLocationProviderClient API to get updates about a device's location. The Fused Location Provider is part of the Google Play services Location APIs.

# Introduction
This is a simple demo where it showcases getting location updates using Android MVVM architetcure and plotted on the google map.

Steps To get Location updates
1. Prepare the map Async    
2. onMapReady callback ask user for location permission 
3. ask Location permission 
4. on Location Permission granted check if user has Location setting turned on
5. Once you are all set, then call fusedLocationClient.requestLocationUpdates() to get current updates 
6. Plot the received latlong on the map





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

# External Libraries Used
[RuntimePermission](https://github.com/florent37/RuntimePermission)


This  makes runtime permissions really easy and callback is received inline.

You just have to call askPermission with the list of wanted permissions

In Kotlin:

    askPermission(Manifest.permission.ACCESS_FINE_LOCATION){
       //all of your permissions have been accepted by the user
    }.onDeclined { e -> 
       //at least one permission have been declined by the user 
    }

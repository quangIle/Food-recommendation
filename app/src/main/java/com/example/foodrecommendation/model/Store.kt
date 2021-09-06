package com.example.foodrecommendation.model

import com.google.maps.model.LatLng

class Store {
    var id: Int
    var name: String
    var vicinity: String
    var opening: String
    var rating: String
    var numOfVoter: String
    var latlng: LatLng

    constructor (
        id: Int,
        name: String,
        vicinity: String,
        opening: String,
        rating: String,
        numOfVoter: String,
        latlng: LatLng
    ) {
        this.id = id
        this.name = name
        this.vicinity = vicinity
        this.opening = opening
        this.rating = rating
        this.numOfVoter = numOfVoter
        this.latlng = latlng
    }
}
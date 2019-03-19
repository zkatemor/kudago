package com.zkatemor.kudago.networks

import com.google.gson.annotations.SerializedName

class CitiesResponse(
    @SerializedName("name")
    val name: String
) : ApiResponse
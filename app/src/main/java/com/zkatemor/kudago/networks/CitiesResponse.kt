package com.zkatemor.kudago.networks

import com.google.gson.annotations.SerializedName

class CitiesResponse(
    val cities: ArrayList<Cities>
) : ApiResponse

data class Cities(
    @SerializedName("name")
    val name: String
)
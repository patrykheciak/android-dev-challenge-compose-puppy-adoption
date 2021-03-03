/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.math.absoluteValue

@Parcelize
data class Cat(
    var age: Int,
    var sex: String? = "",
    var name: String? = "",
    var breeds: Array<Breed>,
    var id: String,
    var url: String,
    var width: Int,
    var height: Int
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cat

        if (!breeds.contentEquals(other.breeds)) return false
        if (id != other.id) return false
        if (url != other.url) return false
        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + width
        result = 31 * result + height
        return result
    }

    fun catAge(): Int {
        if (age == 0)
            age = 1 + this.hashCode().rem(9).absoluteValue
        return age
    }

    fun catSex(): String {
        if (sex.isNullOrEmpty())
            sex = CatNames.sex(this)
        return sex!!
    }

    fun catName(): String {
        if (name.isNullOrEmpty())
            name = CatNames.name(this)
        return name!!
    }
}

@Parcelize
data class Breed(
    var weight: Weight,
    var id: String,
    var name: String,
    var cfa_url: String?,
    var vetstreet_url: String?,
    var vcahospitals_url: String?,
    var temperament: String,
    var origin: String,
    var country_codes: String,
    var country_code: String,
    var description: String,
    var life_span: String,
    var indoor: Int,
    var lap: Int,
    var alt_names: String?,
    var adaptability: Int?,
    var affection_level: Int?,
    var child_friendly: Int?,
    var dog_friendly: Int?,
    var energy_level: Int?,
    var grooming: Int?,
    var health_issues: Int?,
    var intelligence: Int?,
    var shedding_level: Int?,
    var social_needs: Int?,
    var stranger_friendly: Int?,
    var vocalisation: Int?,
    var experimental: Int?,
    var hairless: Int?,
    var natural: Int?,
    var rare: Int?,
    var rex: Int?,
    var suppressed_tail: Int?,
    var short_legs: Int?,
    var wikipedia_url: String?,
    var hypoallergenic: Int?,
    var reference_image_id: String?
) : Parcelable

@Parcelize
data class Weight(
    var imperial: String?,
    var metric: String?
) : Parcelable

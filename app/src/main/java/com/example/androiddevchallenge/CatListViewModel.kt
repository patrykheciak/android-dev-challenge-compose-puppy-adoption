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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.api.CatService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatListViewModel : ViewModel() {

    private val _catList = MutableLiveData<List<Cat>>()
    val catList: LiveData<List<Cat>> get() = _catList

    private val _launchCatActivity = SingleLiveEvent<Cat>()
    val launchCatActivity: LiveData<Cat> get() = _launchCatActivity

    fun loadCats() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: CatService = retrofit.create(CatService::class.java)

        service.listRepos(5, 60).enqueue(object : Callback<List<Cat>> {
            override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
                val catsWithBreed = mutableListOf<Cat>()
                response.body()?.map {
                    if (it.breeds.isNullOrEmpty().not()) {
                        catsWithBreed.add(it)
                    }
                }
                catsWithBreed.forEach {
                    it.catAge()
                    it.catSex()
                    it.catName()
                }
                _catList.value = catsWithBreed
            }

            override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
            }
        })
    }

    fun cardClicked(cat: Cat) {
        _launchCatActivity.value = cat
    }
}

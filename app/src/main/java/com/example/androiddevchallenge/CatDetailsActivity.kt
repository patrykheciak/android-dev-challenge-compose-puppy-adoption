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

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.red700

class CatDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CAT = "extra_cat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cat = intent.getParcelableExtra<Cat>(EXTRA_CAT)

        setContent {
            MyTheme {

                Surface(color = Color(0xFFF3F4F6)) {
                    if (cat != null) {
                        CatDetails(cat)
                    }
                }
            }
        }
        fixStatusBarColor(window)
    }
}

@Composable
private fun CatDetails(cat: Cat) {

    var bitmap = remember { mutableStateOf<Bitmap?>(null) }
    Glide.with(LocalContext.current).asBitmap()
        .load(cat.url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    Scaffold(
        backgroundColor = Color(0xFFF3F4F6),
        bottomBar = {
            BottomAppBar(Modifier.height(96.dp)) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = red700)
                    ) {
                        Text(
                            text = "ADOPT ME!",
                            style = MaterialTheme.typography.h4,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
        ) {
            Surface(
                modifier = Modifier
                    .height(240.dp)
                    .requiredHeight(240.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = if (bitmap == null)
                        painterResource(id = R.drawable.ic_launcher_background) else
                        BitmapPainter(bitmap.value!!.asImageBitmap()),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            val name = cat.catName()
            val sex = cat.catSex()
            val age = cat.catAge()
            Column(Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp)) {
                Text(
                    text = "$name, $age $sex",
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = cat.breeds[0].name,
                    style = MaterialTheme.typography.h6
                )
            }

            Card(Modifier.padding(16.dp), elevation = 0.dp, shape = RoundedCornerShape(8.dp)) {
                Column() {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Description",
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold, color = Color.Gray
                    )
                    Text(
                        modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp),
                        text = cat.breeds[0].description
                    )
                    Text(
                        modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp),
                        text = "Perks",
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold, color = Color.Gray
                    )

                    Row(Modifier.padding(bottom = 8.dp)) {
                        Column(
                            Modifier.padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text("Stranger friendly", modifier = Modifier.height(32.dp))
                            Text("Child friendly", modifier = Modifier.height(32.dp))
                            Text("Social needs", modifier = Modifier.height(32.dp))
                            Text("Intelligence", modifier = Modifier.height(32.dp))
                        }
                        Column() {
                            Row(modifier = Modifier.height(32.dp)) {
                                for (i in 0 until cat.breeds[0].stranger_friendly!!)
                                    Icon(Icons.Filled.Star, contentDescription = null)
                            }
                            Row(modifier = Modifier.height(32.dp)) {
                                for (i in 0 until cat.breeds[0].child_friendly!!)
                                    Icon(Icons.Filled.Star, contentDescription = null)
                            }
                            Row(modifier = Modifier.height(32.dp)) {
                                for (i in 0 until cat.breeds[0].social_needs!!)
                                    Icon(Icons.Filled.Star, contentDescription = null)
                            }
                            Row(modifier = Modifier.height(32.dp)) {
                                for (i in 0 until cat.breeds[0].intelligence!!)
                                    Icon(Icons.Filled.Star, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

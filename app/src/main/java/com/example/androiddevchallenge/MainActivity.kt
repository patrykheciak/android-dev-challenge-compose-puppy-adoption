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

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.verticalGradientBrush

class MainActivity : AppCompatActivity() {

    private val viewModel: CatListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp(viewModel)
            }
        }

        // status bar color fix
        fixStatusBarColor(window)

        viewModel.loadCats()
        viewModel.launchCatActivity.observe(
            this,
            {
                val intent = Intent(this, CatDetailsActivity::class.java)
                intent.putExtra(CatDetailsActivity.EXTRA_CAT, it)
                startActivity(intent)
            }
        )
    }
}

fun fixStatusBarColor(window: Window) {
    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
}

// Start building your app here!
@Composable
fun MyApp(model: CatListViewModel) {
    Surface() {
        Scaffold(
            bottomBar = {
                BottomAppBar {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.Home, contentDescription = null)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.AccountBox, contentDescription = null)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.Favorite, contentDescription = null)
                        }
                        IconButton(onClick = {}) {
                            Icon(Icons.Filled.Settings, contentDescription = null)
                        }
                    }
                }
            }
        ) { paddingValues ->

            val lista by model.catList.observeAsState(emptyList())
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                item {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Kittens in your area",
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
                    )
                }
                items(lista) { item ->
                    CatCard(item, model)
                }
            }
        }
    }
}

@Composable
fun CatCard(cat: Cat, model: CatListViewModel) {
    Card(
        Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                model.cardClicked(cat)
            },
        elevation = 8.dp,
        shape = RoundedCornerShape(24.dp, 2.dp, 24.dp, 2.dp)
    ) {
        Column {
            Box(
                contentAlignment = Alignment.BottomCenter
            ) {

                var bitmap by remember { mutableStateOf<Bitmap?>(null) }
                Glide.with(LocalContext.current).asBitmap()
                    .load(cat.url)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            bitmap = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })

                Surface(
                    modifier = Modifier
                        .height(240.dp)
                        .requiredHeight(240.dp)
                ) {
                    if (bitmap != null) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(),
                            painter = BitmapPainter(bitmap!!.asImageBitmap()),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradientBrush)
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    val name = cat.catName()
                    val sex = cat.catSex()
                    val age = cat.catAge()
                    Text(
                        text = "$name, $age $sex",
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                        color = Color.White,
                    )
                    Text(
                        text = cat.breeds[0].name,
                        style = MaterialTheme.typography.h6,
                        color = Color.White
                    )
                }
            }
            Column() {
                Row(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(end = 16.dp)
                ) {
                    val temperamentFeatures =
                        cat.breeds[0].temperament.replace(" ", "").split(",")
                    for (feature in temperamentFeatures) {
                        OutlinedButton(
                            modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp),
                            shape = RoundedCornerShape(50),
                            onClick = { /*TODO*/ }
                        ) {
                            Text(text = feature, color = Color.DarkGray)
                        }
                    }
                }

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = cat.breeds[0].description,
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

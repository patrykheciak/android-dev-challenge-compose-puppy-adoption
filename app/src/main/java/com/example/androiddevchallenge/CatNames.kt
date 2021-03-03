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

import kotlin.math.absoluteValue

class CatNames {
    companion object {
        fun name(cat: Cat): String {
            if (cat != null) {
                val index = cat.hashCode().rem(names.size).absoluteValue
                return names[index]
            } else
                return names[0]
        }

        fun sex(cat: Cat): String {
            val index = cat.hashCode().rem(2).absoluteValue
            return if (index == 0) "♂" else "♀"
        }

        // Source https://github.com/sindresorhus/cat-names
        val names = arrayOf(
            "Abby",
            "Angel",
            "Annie",
            "Baby",
            "Bailey",
            "Bandit",
            "Bear",
            "Bella",
            "Bob",
            "Boo",
            "Boots",
            "Bubba",
            "Buddy",
            "Buster",
            "Cali",
            "Callie",
            "Casper",
            "Charlie",
            "Chester",
            "Chloe",
            "Cleo",
            "Coco",
            "Cookie",
            "Cuddles",
            "Daisy",
            "Dusty",
            "Felix",
            "Fluffy",
            "Garfield",
            "George",
            "Ginger",
            "Gizmo",
            "Gracie",
            "Harley",
            "Jack",
            "Jasmine",
            "Jasper",
            "Kiki",
            "Kitty",
            "Leo",
            "Lilly",
            "Lily",
            "Loki",
            "Lola",
            "Lucky",
            "Lucy",
            "Luna",
            "Maggie",
            "Max",
            "Mia",
            "Midnight",
            "Milo",
            "Mimi",
            "Miss kitty",
            "Missy",
            "Misty",
            "Mittens",
            "Molly",
            "Muffin",
            "Nala",
            "Oliver",
            "Oreo",
            "Oscar",
            "Patches",
            "Peanut",
            "Pepper",
            "Precious",
            "Princess",
            "Pumpkin",
            "Rascal",
            "Rocky",
            "Sadie",
            "Salem",
            "Sam",
            "Samantha",
            "Sammy",
            "Sasha",
            "Sassy",
            "Scooter",
            "Shadow",
            "Sheba",
            "Simba",
            "Simon",
            "Smokey",
            "Snickers",
            "Snowball",
            "Snuggles",
            "Socks",
            "Sophie",
            "Spooky",
            "Sugar",
            "Tiger",
            "Tigger",
            "Tinkerbell",
            "Toby",
            "Trouble",
            "Whiskers",
            "Willow",
            "Zoe",
            "Zoey"
        )
    }
}

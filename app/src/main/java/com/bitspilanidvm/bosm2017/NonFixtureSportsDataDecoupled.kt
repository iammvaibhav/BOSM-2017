package com.bitspilanidvm.bosm2017

data class NonFixtureSportsDataDecoupled(val categoryName: String,
                                         val categoryDescription: String,
                                         val categoryResult: ArrayList<String>,
                                         val date: String,
                                         val time: String,
                                         val venue: String,
                                         val round: String)
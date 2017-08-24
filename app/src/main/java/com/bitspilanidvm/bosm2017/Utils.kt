package com.bitspilanidvm.bosm2017

fun convertToNonFixtureSportsDecoupledList(data: NonFixtureSportsData): List<NonFixtureSportsDataDecoupled>{
    val list = ArrayList<NonFixtureSportsDataDecoupled>()

    for (i in 0..(data.categoryName.size - 1)){
        list.add(NonFixtureSportsDataDecoupled(data.categoryName[i],
                data.categoryDesc[i],
                data.categoryResult[i],
                data.date,
                data.time,
                data.venue,
                data.round))
    }

    return list
}

fun convertListToNonFixtureSportsDecoupledList(list: List<NonFixtureSportsData>): List<NonFixtureSportsDataDecoupled>{
    val finalList = ArrayList<NonFixtureSportsDataDecoupled>()

    for (i in list)
        finalList.addAll(convertToNonFixtureSportsDecoupledList(i))

    return finalList
}

fun getWinnerListFromFixtureSportsDataList(list: List<FixtureSportsData>): List<FixtureSportsData>{
    val finalList = ArrayList<FixtureSportsData>()

    list.forEach { data ->
        if (data.winner != "")
            finalList.add(data)
    }

    return finalList
}

fun getWinnerListFromNonFixtureSportsDataDecoupledList(list: List<NonFixtureSportsDataDecoupled>): List<NonFixtureSportsDataDecoupled>{
    val finalList = ArrayList<NonFixtureSportsDataDecoupled>()

    list.forEach { data ->
        if (!data.categoryResult.isEmpty())
            finalList.add(data)
    }

    return finalList
}

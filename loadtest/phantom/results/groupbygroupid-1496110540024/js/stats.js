var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "417",
        "ok": "84",
        "ko": "333"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "34",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "8491",
        "ok": "8491",
        "ko": "2491"
    },
    "meanResponseTime": {
        "total": "66",
        "ok": "242",
        "ko": "21"
    },
    "standardDeviation": {
        "total": "491",
        "ok": "980",
        "ko": "222"
    },
    "percentiles1": {
        "total": "0",
        "ok": "112",
        "ko": "0"
    },
    "percentiles2": {
        "total": "0",
        "ok": "142",
        "ko": "0"
    },
    "percentiles3": {
        "total": "144",
        "ok": "232",
        "ko": "0"
    },
    "percentiles4": {
        "total": "1919",
        "ok": "4366",
        "ko": "269"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 82,
        "percentage": 20
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 0,
        "percentage": 0
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 2,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 333,
        "percentage": 80
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "2.304",
        "ok": "0.464",
        "ko": "1.84"
    }
},
contents: {
"req_post-03d94": {
        type: "REQUEST",
        name: "Post",
path: "Post",
pathFormatted: "req_post-03d94",
stats: {
    "name": "Post",
    "numberOfRequests": {
        "total": "375",
        "ok": "42",
        "ko": "333"
    },
    "minResponseTime": {
        "total": "0",
        "ok": "101",
        "ko": "0"
    },
    "maxResponseTime": {
        "total": "8491",
        "ok": "8491",
        "ko": "2491"
    },
    "meanResponseTime": {
        "total": "66",
        "ok": "425",
        "ko": "21"
    },
    "standardDeviation": {
        "total": "517",
        "ok": "1361",
        "ko": "222"
    },
    "percentiles1": {
        "total": "0",
        "ok": "129",
        "ko": "0"
    },
    "percentiles2": {
        "total": "0",
        "ok": "191",
        "ko": "0"
    },
    "percentiles3": {
        "total": "142",
        "ok": "244",
        "ko": "0"
    },
    "percentiles4": {
        "total": "2256",
        "ok": "6453",
        "ko": "761"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 40,
        "percentage": 11
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 0,
        "percentage": 0
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 2,
        "percentage": 1
    },
    "group4": {
        "name": "failed",
        "count": 333,
        "percentage": 89
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "2.072",
        "ok": "0.232",
        "ko": "1.84"
    }
}
    },"req_get-groups-cb506": {
        type: "REQUEST",
        name: "Get Groups",
path: "Get Groups",
pathFormatted: "req_get-groups-cb506",
stats: {
    "name": "Get Groups",
    "numberOfRequests": {
        "total": "42",
        "ok": "42",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "34",
        "ok": "34",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "198",
        "ok": "198",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "59",
        "ok": "59",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "39",
        "ok": "39",
        "ko": "-"
    },
    "percentiles1": {
        "total": "43",
        "ok": "43",
        "ko": "-"
    },
    "percentiles2": {
        "total": "51",
        "ok": "51",
        "ko": "-"
    },
    "percentiles3": {
        "total": "144",
        "ok": "144",
        "ko": "-"
    },
    "percentiles4": {
        "total": "181",
        "ok": "181",
        "ko": "-"
    },
    "group1": {
        "name": "t < 800 ms",
        "count": 42,
        "percentage": 100
    },
    "group2": {
        "name": "800 ms < t < 1200 ms",
        "count": 0,
        "percentage": 0
    },
    "group3": {
        "name": "t > 1200 ms",
        "count": 0,
        "percentage": 0
    },
    "group4": {
        "name": "failed",
        "count": 0,
        "percentage": 0
    },
    "meanNumberOfRequestsPerSecond": {
        "total": "0.232",
        "ok": "0.232",
        "ko": "-"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}

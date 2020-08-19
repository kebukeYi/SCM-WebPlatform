var X_PI = 3.14159265358979324 * 3000.0 / 180.0;
var PI = 3.14159265358979324;
var EARTH_RADIUS = 6378137;

function a(d1) {
    var flag = false;
    if (d1 < 0.0) {
        d1 = -d1;
        flag = true;
    }
    var l = Math.floor((d1 / 6.2831853071795862));
    var d2 = d1 - l * 6.2831853071795862;
    if (d2 > 3.1415926535897931) {
        d2 -= 3.1415926535897931;
        flag = !flag;
    }
    var d3 = d1 = d2;
    var d4 = d1;
    d2 *= d2;
    d4 *= d2;
    d3 -= d4 * 0.16666666666666699;
    d4 *= d2;
    d3 += d4 * 0.0083333333333333297;
    d4 *= d2;
    d3 -= d4 * 0.00019841269841269801;
    d4 *= d2;
    d3 += d4 * 2.7557319223985901E-006;
    d4 *= d2;
    d3 -= d4 * 2.50521083854417E-008;
    if (flag) d3 = -d3;
    return d3;
}

function aa(d1, d2) {
    var d3 = 0;
    d3 += 300 + 1.0 * d1 + 2 * d2 + 0.10000000000000001 * d1 * d1 + 0.10000000000000001 * d1 * d2 + 0.10000000000000001 * Math.sqrt(Math.sqrt(d1 * d1));
    d3 += (20 * a(18.849555921538759 * d1) + 20 * a(6.2831853071795862 * d1)) * 0.66669999999999996;
    d3 += (20 * a(3.1415926535897931 * d1) + 40 * a((3.1415926535897931 * d1) / 3)) * 0.66669999999999996;
    d3 += (150 * a((3.1415926535897931 * d1) / 12) + 300 * a((3.1415926535897931 * d1) / 30)) * 0.66669999999999996;
    return d3;
}

function bb(d1, d2) {
    var d3 = 0;
    d3 += -100 + 2 * d1 + 3 * d2 + 0.20000000000000001 * d2 * d2 + 0.10000000000000001 * d1 * d2 + 0.20000000000000001 * Math.sqrt(Math.sqrt(d1 * d1));
    d3 += (20 * a(18.849555921538759 * d1) + 20 * a(6.2831853071795862 * d1)) * 0.66669999999999996;
    d3 += (20 * a(3.1415926535897931 * d2) + 40 * a((3.1415926535897931 * d2) / 3)) * 0.66669999999999996;
    d3 += (160 * a((3.1415926535897931 * d2) / 12) + 320 * a((3.1415926535897931 * d2) / 30)) * 0.66669999999999996;
    return d3;
}

function cc(d1, d2) {
    var d3 = a((d1 * 3.1415926535897931) / 180);
    var d4 = Math.sqrt(1.0 - 0.0066934200000000003 * d3 * d3);
    d4 = (d2 * 180) / ((6378245 / d4) * Math.cos((d1 * 3.1415926535897931) / 180) * 3.1415926535897931);
    return d4;
}

function dd(d1, d2) {
    var d3 = a((d1 * 3.1415926535897931) / 180);
    var d4 = 1.0 - 0.0066934200000000003 * d3 * d3;
    var d5 = 6335552.7273521004 / (d4 * Math.sqrt(d4));
    return (d2 * 180) / (d5 * 3.1415926535897931);
}

function gps2Gcj(d1, d2) {
    const ad = [d1, d2];
    const d3 = aa(ad[0] - 105, ad[1] - 35);
    const d4 = bb(ad[0] - 105, ad[1] - 35);
    let result = ad[0] + cc(ad[1], d3);
    let result1 = ad[1] + dd(ad[1], d4);
    result = Math.floor((result + 0.00000005) * 1000000) / 1000000;
    result1 = Math.floor((result1 + 0.00000005) * 1000000) / 1000000;
    return [result, result1];
}

function gcjToGps(d1, d2) {
    var ad = [d1, d2];
    var d3 = aa(ad[0] - 105, ad[1] - 35);
    var d4 = bb(ad[0] - 105, ad[1] - 35);
    var result = ad[0] - cc(ad[1], d3);
    var result1 = ad[1] - dd(ad[1], d4);
    result = Math.floor((result + 0.00000005) * 1000000) / 1000000;
    result1 = Math.floor((result1 + 0.00000005) * 1000000) / 1000000;
    return [result, result1];
}

function gcjToBaidu(d1, d2) {
    var x = d1;
    var y = d2;
    var z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
    var theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
    var bdLon = z * Math.cos(theta) + 0.0065;
    var bdLat = z * Math.sin(theta) + 0.006;
    var result = Math.floor((bdLon + 0.00000005) * 1000000) / 1000000;
    var result1 = Math.floor((bdLat + 0.00000005) * 1000000) / 1000000;
    console.log("--------------------------")
    console.log(result, result1);
    return [result, result1];
}

function baiduToGcj(d1, d2) {
    var x = d1 - 0.0065;
    var y = d2 - 0.006;
    var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
    var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
    var ggLon = z * Math.cos(theta);
    var ggLat = z * Math.sin(theta);
    var result = Math.floor((ggLon + 0.00000005) * 1000000) / 1000000;
    var result1 = Math.floor((ggLat + 0.00000005) * 1000000) / 1000000;
    return [result, result1];
}

function rad(d) {
    return d * PI / 180.0;
}

//两点之间距离
function distanceOfTwoPoint(lon1, lat1, lon2, lat2) {
    var radLat1 = rad(lat1);
    var radLat2 = rad(lat2);
    var a1 = radLat1 - radLat2;
    var b = rad(lon1) - rad(lon2);
    var s = Math.sqrt(Math.sin(a1 / 2) * Math.sin(a1 / 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.sin(b / 2) * Math.sin(b / 2));
    s = 2 * s * EARTH_RADIUS;
    return s;
}

//Gps 转Gcj
exports.convertGpsToGcj = function (lon, lat) {
    return gps2Gcj(lon, lat);
};
//Gcj 转Gps
exports.convertGcjToGps = function (lon, lat) {
    return gcjToGps(lon, lat);
};

//Gps 转 百度
exports.convertGpsToBaidu = function (lon, lat) {
    var result = gps2Gcj(lon, lat);
    return gcjToBaidu(result[0], result[1]);
};
//百度 转 Gps
exports.convertBaiduToGps = function (lon, lat) {
    var result = baiduToGcj(lon, lat);
    return gcjToGps(result[0], result[1]);
};

exports.distance = function (lon1, lat1, lon2, lat2) {
    return distanceOfTwoPoint(lon1, lat1, lon2, lat2);
};

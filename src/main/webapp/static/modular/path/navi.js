function sin(x) { return Math.sin(x); }
function asin(x) { return Math.asin(x); }
function cos(x) { return Math.cos(x); }
function acos(x) { return Math.acos(x); }
function tan(x) { return Math.tan(x); }
function atan(x) { return Math.atan(x); }
function atan2(x, y) { return Math.atan2(x, y); }
function fabs(x) { return Math.abs(x); }
function sqrt(x) { return Math.sqrt(x); }
function log(x) { return Math.log(x); }
function pow(x, y) { return Math.pow(x, y); }
function ceil(x) { return Math.ceil(x); }
function fmin(x, y) { return Math.min(x, y); }
function fmax(x, y) { return Math.max(x, y); }

function st_norm2(x, y) {
    var a;
    var b;
    a = fabs(x);
    b = fabs(y);
    if (a < b) {
        a /= b;
        a = sqrt((a * a) + 1.0) * b;
    }
    else if (a > b) {
        b /= a;
        a *= sqrt((b * b) + 1.0);
    }
    else {
        a *= 1.4142135623730951;
    }

    x /= a;
    y /= a;

    var result = new Array(2);
    result[0] = x;
    result[1] = y;
    return result;
}

function st_c1f(epsi) {
    var eps2;
    var eps4;
    var d;
    var C1 = new Array(6);
    eps2 = epsi * epsi;
    eps4 = eps2 * eps2;
    C1[0] = (epsi * (((-eps4) + (6.0 * eps2)) - 16.0)) / 32.0;

    d = epsi * epsi;
    C1[1] = (d * (((-9.0 * eps4) + (64.0 * eps2)) - 128.0)) / 2048.0;
    d *= epsi;
    C1[2] = (d * ((9.0 * eps2) - 16.0)) / 768.0;
    d *= epsi;
    C1[3] = (d * ((3.0 * eps2) - 5.0)) / 512.0;
    d *= epsi;
    C1[4] = d * -0.00546875;
    d *= epsi;
    C1[5] = d * -0.00341796875;
    return C1;
}
function ang_norm(u) {
    var w;
    var x;
    w = fabs(u) / 180.0;
    x = u;
    if (u > 0.0) {
        x = 1.0;
    }
    else if (u < 0.0) {
        x = -1.0;
    }
    else {
        if (u == 0.0) {
            x = 0.0;
        }
    }

    w = (180.0 * (w - (2.0 * ceil((w - 1.0) / 2.0)))) * x;
    if (w == 180.0) {
        w = -180.0;
    }

    return w;
}

function st_scs(sinx, cosx, c) {
    var ar;
    var b_y1;
    var b_y0;
    var k;
    var b_k;
    ar = (2.0 * (cosx - sinx)) * (cosx + sinx);
    b_y1 = 0.0;
    b_y0 = 0.0;
    for (k = 0; k < 3; k++) {
        b_k = (k * -2) + 4;
        b_y1 = ((ar * b_y0) - b_y1) + c[b_k + 1];
        b_y0 = ((ar * b_y1) - b_y0) + c[b_k];
    }

    return ((2.0 * sinx) * cosx) * b_y0;
}

function w84ge_ll2sa(lat1, lon1, lat2, lon2) {
    var up;
    var b_lat1;
    var cgam1;
    var lon12;
    var cgam0;
    var a;
    var vpp;
    var sbet1;
    var cbet1;
    var cbet2;
    var sgam1;
    var ssig12;
    var csig12;
    var sgam2;
    var cgam2;
    var C1a;
    var result = new Array(3);
    var s, azi1, azi2;

    up = (-90.0 >= lat1) ? -90.0 : lat1;
    b_lat1 = (90.0 <= up) ? 90.0 : up;
    up = (-90.0 >= lat2) ? -90.0 : lat2;
    cgam1 = (90.0 <= up) ? 90.0 : up;
    lon12 = b_lat1;
    b_lat1 = fabs(b_lat1);
    if (b_lat1 < 0.0625) {
        b_lat1 = 0.0625 - (0.0625 - b_lat1);
    }

    if (lon12 < 0.0) {
        b_lat1 = -b_lat1;
    }

    lon12 = cgam1;
    cgam1 = fabs(cgam1);
    if (cgam1 < 0.0625) {
        cgam1 = 0.0625 - (0.0625 - cgam1);
    }

    if (lon12 < 0.0) {
        cgam1 = -cgam1;
    }

    lon12 = ang_norm(lon1);
    cgam0 = ang_norm(-lon2);
    a = lon12 + cgam0;
    up = a - cgam0;
    vpp = a - up;
    up -= lon12;
    vpp -= cgam0;
    lon12 = -(up + vpp);
    a = -ang_norm(a);
    if ((a == 180.0) && (lon12 < 0.0)) {
        a = -180.0;
    }

    a -= lon12;
    lon12 = fabs(a);
    if (lon12 < 0.0625) {
        lon12 = 0.0625 - (0.0625 - lon12);
    }

    if (a < 0.0) {
        lon12 = -lon12;
    }

    b_lat1 *= 0.017453292519943295;
    sbet1 = 0.99664718933525254 * sin(b_lat1);
    up = cos(b_lat1);
    cbet1 = (1.4916681462400413E-154 >= up) ? 1.4916681462400413E-154 : up;

    var sbet1_xy = st_norm2(sbet1, cbet1);
    sbet1 = sbet1_xy[0];
    cbet1 = sbet1_xy[1];

    cgam1 *= 0.017453292519943295;
    cgam0 = 0.99664718933525254 * sin(cgam1);
    up = cos(cgam1);
    cbet2 = (1.4916681462400413E-154 >= up) ? 1.4916681462400413E-154 : up;


    var cgam0_xy = st_norm2(cgam0, cbet2);
    cgam0 = cgam0_xy[0];
    cbet2 = cgam0_xy[1];

    lon12 *= 0.017453292519943295;
    up = sin(lon12);
    vpp = cos(lon12);
    sgam1 = cbet2 * up;
    cgam1 = (cbet1 * cgam0) - ((sbet1 * cbet2) * vpp);
    a = fabs(sgam1);
    lon12 = fabs(cgam1);
    if (a < lon12) {
        a /= lon12;
        ssig12 = sqrt((a * a) + 1.0) * lon12;
    }
    else if (a > lon12) {
        lon12 /= a;
        ssig12 = sqrt((lon12 * lon12) + 1.0) * a;
    }
    else {
        ssig12 = a * 1.4142135623730951;
    }

    csig12 = (sbet1 * cgam0) + ((cbet1 * cbet2) * vpp);

    var gam1_sc = st_norm2(sgam1, cgam1);
    sgam1 = gam1_sc[0];
    cgam1 = gam1_sc[1];

    sgam2 = cbet1 * up;
    cgam2 = ((-sbet1) * cbet2) + ((cbet1 * cgam0) * vpp);

    var gam2_sc = st_norm2(sgam2, cgam2);
    sgam2 = gam2_sc[0];
    cgam2 = gam2_sc[1];

    a = fabs(cgam1);
    lon12 = fabs(sgam1 * sbet1);
    if (a < lon12) {
        a /= lon12;
        cgam0 = sqrt((a * a) + 1.0) * lon12;
    }
    else if (a > lon12) {
        lon12 /= a;
        cgam0 = sqrt((lon12 * lon12) + 1.0) * a;
    }
    else {
        cgam0 = a * 1.4142135623730951;
    }

    b_lat1 = cbet1 * cgam1;

    var sbet1_xy = st_norm2(sbet1, b_lat1);
    sbet1 = sbet1_xy[0];
    b_lat1 = sbet1_xy[1];

    up = 0.0066943799901413156 * (cgam0 * cgam0);
    vpp = up / ((2.0 * (1.0 + sqrt(1.0 - up))) - up);
    C1a = st_c1f(vpp);
    lon12 = vpp * vpp;
    up = cgam1 * sqrt(1.0 - (0.0066943799901413156 * (cbet1 * cbet1)));
    up = atan2(sgam1, up);
    azi1 = ang_norm(up * 57.295779513082323);
    up = cgam2 * sqrt(1.0 - (0.0066943799901413156 * (cbet2 * cbet2)));
    up = atan2(sgam2, up);
    azi2 = ang_norm(up * 57.295779513082323);
    if ((azi1) < 0.0) {
        azi1 += 360.0;
    }

    if ((azi2) < 0.0) {
        azi2 += 360.0;
    }

    up = atan2(ssig12, csig12);
    s = (((6.378137E+6 * (1.0 + (((((((lon12 * lon12) * lon12) + ((lon12 * lon12)
        * 4.0)) + (lon12 * 64.0)) / 256.0) + vpp) / (1.0 - vpp)))) * (1.0 - vpp)) /
        (1.0 + vpp)) * (up + (st_scs((sbet1 * csig12) + (b_lat1 * ssig12),
        (b_lat1 * csig12) - (sbet1 * ssig12), C1a) - st_scs(sbet1, b_lat1, C1a)));
    azi1 *= 0.017453292519943295;
    azi2 *= 0.017453292519943295;

    result[0] = s;
    result[1] = azi1;
    result[2] = azi2;
    return result;
}

function w84ge_sa2ll(lat1, lon1, s, azi1) {
    //azi1 *= 0.017453292519943295769236907684886;
    var epsi;
    var b;
    var d;
    var sbet1;
    var b_s;
    var sgam0;
    var a;
    var cgam0;
    var slam1;
    var csig1;
    var b_csig1;
    var eps2;
    var dv0 = new Array(6);
    var B11;
    var stau1;
    var ctau1;
    var C1pa = new Array(6);

    var lat2, lon2, azi2;


    azi1 *= 57.295779513082323;
    epsi = (-90.0 >= lat1) ? -90.0 : lat1;
    b = (90.0 <= epsi) ? 90.0 : epsi;
    d = fabs(b);
    if (d < 0.0625) {
        d = 0.0625 - (0.0625 - d);
    }

    if (b < 0.0) {
        d = -d;
    }

    b = d * 0.017453292519943295;
    d = fabs(azi1);
    if (d < 0.0625) {
        d = 0.0625 - (0.0625 - d);
    }

    if (azi1 < 0.0) {
        d = -d;
    }

    d *= 0.017453292519943295;
    sbet1 = 0.99664718933525254 * sin(b);
    epsi = cos(b);
    b_s = (1.4916681462400413E-154 >= epsi) ? 1.4916681462400413E-154 : epsi;
    var sbet1_xy = st_norm2(sbet1, b_s);
    sbet1 = sbet1_xy[0];
    b_s = sbet1_xy[1];

    b = sin(d) * sqrt(1.0 - (0.0066943799901413156 * (b_s * b_s)));
    d = cos(d);
    var bd = st_norm2(b, d);
    b = bd[0];
    d = bd[1];

    sgam0 = b * b_s;
    a = fabs(d);
    b = fabs(b * sbet1);
    if (a < b) {
        a /= b;
        cgam0 = sqrt((a * a) + 1.0) * b;
    }
    else if (a > b) {
        b /= a;
        cgam0 = sqrt((b * b) + 1.0) * a;
    }
    else {
        cgam0 = a * 1.4142135623730951;
    }

    slam1 = sgam0 * sbet1;
    csig1 = b_s * d;
    if ((sbet1 == 0.0) && (d == 0.0)) {
        csig1 = 1.0;
    }

    b_csig1 = csig1;
    var b_csig1_xy = st_norm2(sbet1, b_csig1);
    sbet1 = b_csig1_xy[0];
    b_csig1 = b_csig1_xy[1];

    b = 0.0066943799901413156 * (cgam0 * cgam0);
    epsi = b / ((2.0 * (1.0 + sqrt(1.0 - b))) - b);
    eps2 = epsi * epsi;
    dv0 = st_c1f(epsi);
    B11 = st_scs(sbet1, b_csig1, dv0);
    b_s = sin(B11);
    b = cos(B11);
    stau1 = (sbet1 * b) + (b_csig1 * b_s);
    ctau1 = (b_csig1 * b) - (sbet1 * b_s);
    b = epsi * epsi;
    a = b * b;
    C1pa[0] = (epsi * (((205.0 * a) - (432.0 * b)) + 768.0)) / 1536.0;
    d = epsi * epsi;
    C1pa[1] = (d * (((4005.0 * a) - (4736.0 * b)) + 3840.0)) / 12288.0;
    d *= epsi;
    C1pa[2] = (d * ((-225.0 * b) + 116.0)) / 384.0;
    d *= epsi;
    C1pa[3] = (d * ((-7173.0 * b) + 2695.0)) / 7680.0;
    d *= epsi;
    C1pa[4] = d * 0.45143229166666665;
    d *= epsi;
    C1pa[5] = d * 0.61980794270833328;
    d = s / (((6.378137E+6 * (1.0 + (((((((eps2 * eps2) * eps2) + ((eps2 * eps2) *
        4.0)) + (eps2 * 64.0)) / 256.0) + epsi) / (1.0 - epsi)))) * (1.0 - epsi)) /
        (1.0 + epsi));
    b_s = sin(d);
    b = cos(d);
    d -= (-st_scs((stau1 * b) + (ctau1 * b_s), (ctau1 * b) - (stau1 * b_s), C1pa))
        - B11;
    b_s = sin(d);
    b = cos(d);
    B11 = (sbet1 * b) + (b_csig1 * b_s);
    eps2 = (b_csig1 * b) - (sbet1 * b_s);
    a = fabs(sgam0);
    b = fabs(cgam0 * eps2);
    if (a < b) {
        a /= b;
        b_s = sqrt((a * a) + 1.0) * b;
    }
    else if (a > b) {
        b /= a;
        b_s = sqrt((b * b) + 1.0) * a;
    }
    else {
        b_s = a * 1.4142135623730951;
    }

    if (b_s == 0.0) {
        b_s = 1.4916681462400413E-154;
    }

    a = sgam0 * B11;
    epsi = (cgam0 * eps2) * sqrt(1.0 - (0.0066943799901413156 * (b_s * b_s)));
    d = atan2(sgam0, epsi);
    azi2 = ang_norm(d * 57.295779513082323);
    if ((azi2) < 0.0) {
        azi2 += 360.0;
    }

    b = cgam0 * B11;
    epsi = 0.99664718933525254 * b_s;
    d = atan2(b, epsi);
    lat2 = d * 57.295779513082323;
    b = (a * csig1) - (eps2 * slam1);
    epsi = (eps2 * csig1) + (a * slam1);
    d = atan2(b, epsi);
    lon2 = ang_norm(ang_norm(lon1) + (d * 57.295779513082323));
    azi2 *= 0.017453292519943295;

    var result = new Array(3);
    result[0] = lat2;
    result[1] = lon2;
    result[2] = azi2;

    return result;

}
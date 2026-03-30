static int MOD = 1_000_000_007;

// this calculates x to the power y in log y time
static long fastPower(long x, long y) {
    long res = 1;
    while (y > 0) { // exponent
        if ((y & 1) == 1) // odd check
            res = (res * x) % MOD;
        x = (x * x) % MOD;
        y /= 2;
    }
    return res;
}

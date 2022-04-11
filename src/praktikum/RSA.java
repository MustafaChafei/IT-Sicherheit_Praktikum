package praktikum;

import java.io.*;
import java.util.Random;

public class RSA {
    //large prime numbers for p and q
    //product of p and q should be at least 2048 bits (600 digits)
    //RSA-Modul: N = p*q
    //Euler phi: phi(N) = (p-1) * (q-1)
    //pick e as a relative prime to phi: 1 < e < phi (euclidean algo)
    //multiplicative inverse with extended euclid
    static long N = 0;
    /**
     * Checks if passed value is a prime number
     */
    static boolean isPrime(int p) {
        if (p <= 1) {
            return false;
        }
        if ((p % 2 == 0) || ((p % 3) == 0)) return false;

        int count = 5;

        while (Math.pow(count, 2) <= p) {
            if (p % count == 0 || p % (count + 2) == 0) return false;

            count += 6;
        }
        return true;
    }

    /**
     * <p>Generates a Prime Number, bound is pre-determined for current test</p>
     * Current bound: 3000
     */
    static int rngPrime() {
        Random rndm = new Random();
        int num = rndm.nextInt(300);
        while (!isPrime(num)) {
            num = rndm.nextInt(300);
        }
        return num;
    }

    /**
     * Takes to numbers and calculates the greatest common divisor
     */
    static long ggt(int p, int q) {

        if (p == 0)
            return q;

        return ggt(q % p, p);

    }

    static int extendedEuclid(int x, int y) {
        int u, v;
        int q = 0, r = 0, s, t;
        int phi = y;
        u = t = 1;
        v = s = 0;
        while (y > 0) {
            q = x / y;

            r = x - q * y;
            x = y;
            y = r;

            r = u - q * s;
            u = s;
            s = r;

            r = v - q * t;
            v = t;
            t = r;
        }

        if (u < 0) {
            u = phi + u;
        }

        return u;

    }

    static long expo(long base, long exp) {
        if (exp == 0)
            return 1;

        if (exp == 1)
            return base % N;

        long t = expo(base, exp / 2);
        t = (t * t) % N;

        // if exponent is even value
        if (exp % 2 == 0)
            return t;

        // if exponent is odd value
        else
            return ((base % N) * t) % N;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/toEncrypt.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/encrypt.txt"));
        //pick to random primes
        int p = rngPrime();
        int q = rngPrime();
        //check if the picked prime numbers are equal, reshuffle one of them if they are equal
        while (q == p) {
            q = rngPrime();
        }
        //storing n and phi(n) the RSA modulus in variables
        N = p * q;
        int phi = (p - 1) * (q - 1);
        //randomly pick a number e ( 1 < e < phi ),
        // shuffle until it fulfills the condition to not have common divider with phi
        int e = new Random().nextInt(phi - 1) + 1;
        while (ggt(phi, e) != 1) {
            e = new Random().nextInt(phi - 1) + 1;
        }
        //algorithm to determine the inverse multiplicative of e
        //d and e shall not be equal, if so, program will stop execution and terminate.
        //Odds of Occurrence of Equality decreases, The greater the bound for the random prime numbers gets
        int d = extendedEuclid(e, phi);
        if (d == e) {
            System.out.println("d and e are same number");
            System.exit(0);
        }

        System.out.println("p: " + p);
        System.out.println("q: " + q);
        System.out.println("n: " + N);
        System.out.println("phi: " + phi);
        System.out.println("e: " + e);
        System.out.println("d: " + d);

        String s = "";
        while ((s = br.readLine()) != null) {
            for (int i = 0; i < s.length(); i++) {
                int k = s.charAt(i);
                long secret = expo(k, e);
                bw.write(secret + " ");
            }
            bw.write("\n");
        }
        bw.close();

        br = new BufferedReader(new FileReader("src/encrypt.txt"));

        while ((s = br.readLine()) != null) {
            String[] f = s.split(" ");
            for (int i = 0; i < f.length; i++) {
                int t = Integer.parseInt(f[i]);
                int dec = (int) expo(t, d);
                System.out.print((char) dec);
            }
            System.out.println();

        }
        br.close();


    }
}

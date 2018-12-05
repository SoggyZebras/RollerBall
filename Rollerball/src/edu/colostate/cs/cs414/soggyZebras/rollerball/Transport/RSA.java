package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import java.math.BigInteger;

public class RSA {

    private BigInteger N;
    private BigInteger phiN;
    private BigInteger e;
    private BigInteger d;


    public void generateN(BigInteger p, BigInteger q){
        N = p.multiply(q);
    }

    public void generatephiN(){

    }

    public void generateE(BigInteger d){
        d = d;
        e = modInverse(d,N);
    }

    private BigInteger modInverse(BigInteger d, BigInteger N){
        return new BigInteger(String.valueOf(0));
    }

}

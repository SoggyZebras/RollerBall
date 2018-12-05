package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import sun.nio.cs.US_ASCII;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RSA {

    private BigInteger N;
    private BigInteger phiN;
    private BigInteger theirE;
    private BigInteger theirN;
    private BigInteger myE;
    private BigInteger myD;


    public void generateN(BigInteger p, BigInteger q){
        N = p.multiply(q);
    }

    public void generatephiN(BigInteger p, BigInteger q){
        phiN = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }

    public void setE(BigInteger e){
        myE = e;
    }

    public void setTheirN(BigInteger n){
        theirN = n;
    }

    public void setTheirE(BigInteger e){
        theirE = e;
    }

    public BigInteger getPhiN(){
        return phiN;
    }

    public BigInteger getN(){
        return N;
    }

    public BigInteger getTheirE(){
        return theirE;
    }

    public BigInteger getTheirN(){
        return theirN;
    }

    public void generateD(){
        myD = myE.modInverse(phiN);
    }

    public BigInteger getMyE(){
        return myE;
    }

    public BigInteger encrypt(String s){
        System.out.println("encrypt: " + s);

        BigInteger msg = new BigInteger(s.getBytes());
        System.out.println(msg);
        System.out.println(theirE);
        System.out.println(theirN);
        BigInteger enc = msg.modPow(theirE,theirN);
        System.out.println("enc: " + enc);
        return enc;
    }

    public String decrypt(BigInteger b){
        System.out.println("decrypt: " + b);
        BigInteger msg = b.modPow(myD,N);
        String dec = new String(msg.toByteArray());
        System.out.println("dec: "+ dec);
        return dec;
    }


}

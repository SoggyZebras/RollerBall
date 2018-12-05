package edu.colostate.cs.cs414.soggyZebras.rollerball.Transport;

import java.math.BigInteger;


public class RSA {

    private BigInteger N = null;
    private BigInteger phiN = null;
    private BigInteger theirE = null;
    private BigInteger theirN = null;
    private BigInteger myE = null;
    private BigInteger myD = null;


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

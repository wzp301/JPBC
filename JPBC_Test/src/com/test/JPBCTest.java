package com.test;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.nio.charset.Charset;


public class JPBCTest {

    public static void main(String[] args) {

        Pairing bp = PairingFactory.getPairing("a.properties");

        Field G1 = bp.getG1();
        Field Zr = bp.getZr();

        Element g = G1.newRandomElement();
        Element a = Zr.newRandomElement();
        Element b = Zr.newRandomElement();

        Element g_a = g.duplicate().powZn(a);
        Element g_b = g.duplicate().powZn(b);
        Element egg_ab = bp.pairing(g_a, g_b);

        Element egg = bp.pairing(g, g);
        Element ab = a.duplicate().mul(b);
        Element egg_ab_p = bp.pairing(g_a, g_b);

        if (egg_ab.isEqual(egg_ab_p)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        try (InitTimer it = Utils.timer("Sha256 Test")) {
            String plaintext = "hello";
            byte [] byteString = plaintext.getBytes(Charset.forName("UTF-8"));
            byte [] hash = Utils.sha256(byteString);
            String result = Utils.bytesToHex(hash);

            System.out.printf(result);
        }

    }

}

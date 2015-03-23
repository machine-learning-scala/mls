package ml;

public class Bla {

    public static double classify(Object[] i)
            throws Exception {

        double p = Double.NaN;
        p = Bla.Ned9d0340(i);
        return p;
    }

    static double Ned9d0340(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 2;
        } else if (((Double) i[7]).doubleValue() <= 0.144) {
            p = Bla.N6121c9d61(i);
        } else if (((Double) i[7]).doubleValue() > 0.144) {
            p = Bla.N543c6f6d45(i);
        }
        return p;
    }

    static double N6121c9d61(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() <= 0.22) {
            p = Bla.N87f383f2(i);
        } else if (((Double) i[2]).doubleValue() > 0.22) {
            p = Bla.N1060b4316(i);
        }
        return p;
    }

    static double N87f383f2(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() <= 0.19) {
            p = 0;
        } else if (((Double) i[2]).doubleValue() > 0.19) {
            p = Bla.N4eb7f0033(i);
        }
        return p;
    }

    static double N4eb7f0033(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 0.03) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() > 0.03) {
            p = Bla.Neafc1914(i);
        }
        return p;
    }

    static double Neafc1914(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 0;
        } else if (((Double) i[4]).doubleValue() <= 0.109) {
            p = 0;
        } else if (((Double) i[4]).doubleValue() > 0.109) {
            p = Bla.N612fc6eb5(i);
        }
        return p;
    }

    static double N612fc6eb5(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 0.295) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() > 0.295) {
            p = 0;
        }
        return p;
    }

    static double N1060b4316(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (i[0].equals("F")) {
            p = Bla.N612679d67(i);
        } else if (i[0].equals("I")) {
            p = Bla.N74ad1f1f12(i);
        } else if (i[0].equals("M")) {
            p = Bla.N8e2474338(i);
        }
        return p;
    }

    static double N612679d67(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() <= 0.46) {
            p = Bla.N11758f2a8(i);
        } else if (((Double) i[1]).doubleValue() > 0.46) {
            p = 2;
        }
        return p;
    }

    static double N11758f2a8(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.1965) {
            p = Bla.Ne720b719(i);
        } else if (((Double) i[5]).doubleValue() > 0.1965) {
            p = 5;
        }
        return p;
    }

    static double Ne720b719(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() <= 0.425) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() > 0.425) {
            p = Bla.N1b26f7b210(i);
        }
        return p;
    }

    static double N1b26f7b210(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 0.1245) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() > 0.1245) {
            p = Bla.N491cc5c911(i);
        }
        return p;
    }

    static double N491cc5c911(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 6;
        } else if (((Double) i[6]).doubleValue() <= 0.1) {
            p = 6;
        } else if (((Double) i[6]).doubleValue() > 0.1) {
            p = 8;
        }
        return p;
    }

    static double N74ad1f1f12(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 2;
        } else if (((Double) i[7]).doubleValue() <= 0.103) {
            p = Bla.N6a1aab7813(i);
        } else if (((Double) i[7]).doubleValue() > 0.103) {
            p = Bla.N569cfc3630(i);
        }
        return p;
    }

    static double N6a1aab7813(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 1;
        } else if (((Double) i[3]).doubleValue() <= 0.095) {
            p = Bla.N462d5aee14(i);
        } else if (((Double) i[3]).doubleValue() > 0.095) {
            p = Bla.N2db7a79b22(i);
        }
        return p;
    }

    static double N462d5aee14(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 0.06) {
            p = Bla.N69b0fd6f15(i);
        } else if (((Double) i[7]).doubleValue() > 0.06) {
            p = Bla.N757942a116(i);
        }
        return p;
    }

    static double N69b0fd6f15(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() <= 0.0865) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() > 0.0865) {
            p = 2;
        }
        return p;
    }

    static double N757942a116(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 0.092) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() > 0.092) {
            p = Bla.N4a87761d17(i);
        }
        return p;
    }

    static double N4a87761d17(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 1;
        } else if (((Double) i[7]).doubleValue() <= 0.077) {
            p = Bla.N66d1af8918(i);
        } else if (((Double) i[7]).doubleValue() > 0.077) {
            p = Bla.N37374a5e20(i);
        }
        return p;
    }

    static double N66d1af8918(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 1;
        } else if (((Double) i[1]).doubleValue() <= 0.39) {
            p = Bla.N8646db919(i);
        } else if (((Double) i[1]).doubleValue() > 0.39) {
            p = 1;
        }
        return p;
    }

    static double N8646db919(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() <= 0.251) {
            p = 1;
        } else if (((Double) i[4]).doubleValue() > 0.251) {
            p = 2;
        }
        return p;
    }

    static double N37374a5e20(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() <= 0.09) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() > 0.09) {
            p = Bla.N4671e53b21(i);
        }
        return p;
    }

    static double N4671e53b21(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 0.395) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() > 0.395) {
            p = 1;
        }
        return p;
    }

    static double N2db7a79b22(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 0.365) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() > 0.365) {
            p = Bla.N6950e3123(i);
        }
        return p;
    }

    static double N6950e3123(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 0.425) {
            p = Bla.Nb7dd10724(i);
        } else if (((Double) i[1]).doubleValue() > 0.425) {
            p = Bla.N3ecd23d929(i);
        }
        return p;
    }

    static double Nb7dd10724(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 0.29) {
            p = Bla.N42eca56e25(i);
        } else if (((Double) i[2]).doubleValue() > 0.29) {
            p = Bla.N52f759d726(i);
        }
        return p;
    }

    static double N42eca56e25(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 1;
        } else if (((Double) i[6]).doubleValue() <= 0.0545) {
            p = 1;
        } else if (((Double) i[6]).doubleValue() > 0.0545) {
            p = 2;
        }
        return p;
    }

    static double N52f759d726(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() <= 0.297) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() > 0.297) {
            p = Bla.N7cbd213e27(i);
        }
        return p;
    }

    static double N7cbd213e27(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 1;
        } else if (((Double) i[5]).doubleValue() <= 0.1575) {
            p = Bla.N192d324728(i);
        } else if (((Double) i[5]).doubleValue() > 0.1575) {
            p = 2;
        }
        return p;
    }

    static double N192d324728(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() <= 0.315) {
            p = 1;
        } else if (((Double) i[2]).doubleValue() > 0.315) {
            p = 3;
        }
        return p;
    }

    static double N3ecd23d929(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 2;
        } else if (((Double) i[6]).doubleValue() <= 0.082) {
            p = 2;
        } else if (((Double) i[6]).doubleValue() > 0.082) {
            p = 1;
        }
        return p;
    }

    static double N569cfc3630(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 0.1355) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() > 0.1355) {
            p = Bla.N43bd930a31(i);
        }
        return p;
    }

    static double N43bd930a31(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() <= 0.415) {
            p = 2;
        } else if (((Double) i[1]).doubleValue() > 0.415) {
            p = Bla.N33723e3032(i);
        }
        return p;
    }

    static double N33723e3032(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 3;
        } else if (((Double) i[3]).doubleValue() <= 0.13) {
            p = Bla.N64f6106c33(i);
        } else if (((Double) i[3]).doubleValue() > 0.13) {
            p = 2;
        }
        return p;
    }

    static double N64f6106c33(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 3;
        } else if (((Double) i[5]).doubleValue() <= 0.223) {
            p = Bla.N553a3d8834(i);
        } else if (((Double) i[5]).doubleValue() > 0.223) {
            p = Bla.Ncb0ed2037(i);
        }
        return p;
    }

    static double N553a3d8834(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 3;
        } else if (((Double) i[5]).doubleValue() <= 0.212) {
            p = Bla.N7a30d1e635(i);
        } else if (((Double) i[5]).doubleValue() > 0.212) {
            p = 2;
        }
        return p;
    }

    static double N7a30d1e635(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 2;
        } else if (((Double) i[7]).doubleValue() <= 0.1145) {
            p = Bla.N5891e32e36(i);
        } else if (((Double) i[7]).doubleValue() > 0.1145) {
            p = 3;
        }
        return p;
    }

    static double N5891e32e36(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 0.162) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() > 0.162) {
            p = 3;
        }
        return p;
    }

    static double Ncb0ed2037(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() <= 0.237) {
            p = 2;
        } else if (((Double) i[5]).doubleValue() > 0.237) {
            p = 3;
        }
        return p;
    }

    static double N8e2474338(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() <= 0.1695) {
            p = Bla.N74a1085839(i);
        } else if (((Double) i[5]).doubleValue() > 0.1695) {
            p = Bla.N4e71820743(i);
        }
        return p;
    }

    static double N74a1085839(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 0.11) {
            p = Bla.N23fe1d7140(i);
        } else if (((Double) i[7]).doubleValue() > 0.11) {
            p = 5;
        }
        return p;
    }

    static double N23fe1d7140(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() <= 0.27) {
            p = Bla.N28ac3dc341(i);
        } else if (((Double) i[2]).doubleValue() > 0.27) {
            p = 4;
        }
        return p;
    }

    static double N28ac3dc341(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= 0.2135) {
            p = Bla.N32eebfca42(i);
        } else if (((Double) i[4]).doubleValue() > 0.2135) {
            p = 1;
        }
        return p;
    }

    static double N32eebfca42(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() <= 0.25) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() > 0.25) {
            p = 4;
        }
        return p;
    }

    static double N4e71820743(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 2;
        } else if (((Double) i[3]).doubleValue() <= 0.11) {
            p = Bla.N1d371b2d44(i);
        } else if (((Double) i[3]).doubleValue() > 0.11) {
            p = 3;
        }
        return p;
    }

    static double N1d371b2d44(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() <= 0.4385) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() > 0.4385) {
            p = 3;
        }
        return p;
    }

    static double N543c6f6d45(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 0.29) {
            p = Bla.N13eb8acf46(i);
        } else if (((Double) i[7]).doubleValue() > 0.29) {
            p = Bla.N31f924f5116(i);
        }
        return p;
    }

    static double N13eb8acf46(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 3;
        } else if (((Double) i[7]).doubleValue() <= 0.192) {
            p = Bla.N51c8530f47(i);
        } else if (((Double) i[7]).doubleValue() > 0.192) {
            p = Bla.N1990a65e69(i);
        }
        return p;
    }

    static double N51c8530f47(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 6;
        } else if (((Double) i[2]).doubleValue() <= 0.35) {
            p = Bla.N7403c46848(i);
        } else if (((Double) i[2]).doubleValue() > 0.35) {
            p = Bla.N43738a8249(i);
        }
        return p;
    }

    static double N7403c46848(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() <= 0.34) {
            p = 2;
        } else if (((Double) i[2]).doubleValue() > 0.34) {
            p = 6;
        }
        return p;
    }

    static double N43738a8249(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 4;
        } else if (i[0].equals("F")) {
            p = Bla.Nc81cdd150(i);
        } else if (i[0].equals("I")) {
            p = Bla.N289d1c0254(i);
        } else if (i[0].equals("M")) {
            p = Bla.N396e2f3960(i);
        }
        return p;
    }

    static double Nc81cdd150(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() <= 0.27) {
            p = Bla.N1fc2b76551(i);
        } else if (((Double) i[5]).doubleValue() > 0.27) {
            p = 3;
        }
        return p;
    }

    static double N1fc2b76551(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= 0.5905) {
            p = Bla.N7588107152(i);
        } else if (((Double) i[4]).doubleValue() > 0.5905) {
            p = 6;
        }
        return p;
    }

    static double N7588107152(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() <= 0.125) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() > 0.125) {
            p = Bla.N2a70a3d853(i);
        }
        return p;
    }

    static double N2a70a3d853(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 7;
        } else if (((Double) i[1]).doubleValue() <= 0.485) {
            p = 7;
        } else if (((Double) i[1]).doubleValue() > 0.485) {
            p = 4;
        }
        return p;
    }

    static double N289d1c0254(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() <= 0.1595) {
            p = Bla.N22eeefeb55(i);
        } else if (((Double) i[6]).doubleValue() > 0.1595) {
            p = 2;
        }
        return p;
    }

    static double N22eeefeb55(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 3;
        } else if (((Double) i[5]).doubleValue() <= 0.217) {
            p = Bla.N17d0685f56(i);
        } else if (((Double) i[5]).doubleValue() > 0.217) {
            p = Bla.N78ac110258(i);
        }
        return p;
    }

    static double N17d0685f56(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() <= 0.37) {
            p = Bla.N3891771e57(i);
        } else if (((Double) i[2]).doubleValue() > 0.37) {
            p = 3;
        }
        return p;
    }

    static double N3891771e57(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() <= 0.47) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() > 0.47) {
            p = 3;
        }
        return p;
    }

    static double N78ac110258(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() <= 0.515) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() > 0.515) {
            p = Bla.N2de8284b59(i);
        }
        return p;
    }

    static double N2de8284b59(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() <= 0.39) {
            p = 3;
        } else if (((Double) i[2]).doubleValue() > 0.39) {
            p = 4;
        }
        return p;
    }

    static double N396e2f3960(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.365) {
            p = Bla.Na74868d61(i);
        } else if (((Double) i[2]).doubleValue() > 0.365) {
            p = Bla.N12c8a2c062(i);
        }
        return p;
    }

    static double Na74868d61(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.2115) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() > 0.2115) {
            p = 3;
        }
        return p;
    }

    static double N12c8a2c062(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() <= 0.495) {
            p = Bla.N7e0e6aa263(i);
        } else if (((Double) i[1]).doubleValue() > 0.495) {
            p = Bla.N365185bd64(i);
        }
        return p;
    }

    static double N7e0e6aa263(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 3;
        } else if (((Double) i[7]).doubleValue() <= 0.1695) {
            p = 3;
        } else if (((Double) i[7]).doubleValue() > 0.1695) {
            p = 5;
        }
        return p;
    }

    static double N365185bd64(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() <= 0.3075) {
            p = Bla.N18bf3d1465(i);
        } else if (((Double) i[5]).doubleValue() > 0.3075) {
            p = Bla.N42607a4f67(i);
        }
        return p;
    }

    static double N18bf3d1465(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 0.1775) {
            p = Bla.N4fb6426166(i);
        } else if (((Double) i[7]).doubleValue() > 0.1775) {
            p = 5;
        }
        return p;
    }

    static double N4fb6426166(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 3;
        } else if (((Double) i[5]).doubleValue() <= 0.2655) {
            p = 3;
        } else if (((Double) i[5]).doubleValue() > 0.2655) {
            p = 4;
        }
        return p;
    }

    static double N42607a4f67(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() <= 0.7175) {
            p = 2;
        } else if (((Double) i[4]).doubleValue() > 0.7175) {
            p = Bla.N782663d368(i);
        }
        return p;
    }

    static double N782663d368(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 3;
        } else if (((Double) i[4]).doubleValue() <= 0.772) {
            p = 3;
        } else if (((Double) i[4]).doubleValue() > 0.772) {
            p = 2;
        }
        return p;
    }

    static double N1990a65e69(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() <= 0.3075) {
            p = Bla.N64485a4770(i);
        } else if (((Double) i[5]).doubleValue() > 0.3075) {
            p = Bla.N43a0cee982(i);
        }
        return p;
    }

    static double N64485a4770(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 8;
        } else if (((Double) i[5]).doubleValue() <= 0.2395) {
            p = Bla.N25bbf68371(i);
        } else if (((Double) i[5]).doubleValue() > 0.2395) {
            p = Bla.N7276c8cd73(i);
        }
        return p;
    }

    static double N25bbf68371(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 8;
        } else if (i[0].equals("F")) {
            p = 8;
        } else if (i[0].equals("I")) {
            p = 3;
        } else if (i[0].equals("M")) {
            p = Bla.N6ec8211c72(i);
        }
        return p;
    }

    static double N6ec8211c72(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 9;
        } else if (((Double) i[6]).doubleValue() <= 0.14) {
            p = 9;
        } else if (((Double) i[6]).doubleValue() > 0.14) {
            p = 8;
        }
        return p;
    }

    static double N7276c8cd73(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= 0.7565) {
            p = Bla.N544a2ea674(i);
        } else if (((Double) i[4]).doubleValue() > 0.7565) {
            p = Bla.N10dba09779(i);
        }
        return p;
    }

    static double N544a2ea674(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (i[0].equals("F")) {
            p = Bla.N2e3fc54275(i);
        } else if (i[0].equals("I")) {
            p = 4;
        } else if (i[0].equals("M")) {
            p = Bla.N4524411f77(i);
        }
        return p;
    }

    static double N2e3fc54275(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() <= 0.2075) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() > 0.2075) {
            p = Bla.N150c15876(i);
        }
        return p;
    }

    static double N150c15876(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 6;
        } else if (((Double) i[3]).doubleValue() <= 0.15) {
            p = 6;
        } else if (((Double) i[3]).doubleValue() > 0.15) {
            p = 7;
        }
        return p;
    }

    static double N4524411f77(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 8;
        } else if (((Double) i[1]).doubleValue() <= 0.5) {
            p = 8;
        } else if (((Double) i[1]).doubleValue() > 0.5) {
            p = Bla.N401e780378(i);
        }
        return p;
    }

    static double N401e780378(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 7;
        } else if (((Double) i[3]).doubleValue() <= 0.15) {
            p = 7;
        } else if (((Double) i[3]).doubleValue() > 0.15) {
            p = 6;
        }
        return p;
    }

    static double N10dba09779(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 8;
        } else if (((Double) i[4]).doubleValue() <= 0.77) {
            p = 8;
        } else if (((Double) i[4]).doubleValue() > 0.77) {
            p = Bla.N1786f9d580(i);
        }
        return p;
    }

    static double N1786f9d580(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 7;
        } else if (((Double) i[2]).doubleValue() <= 0.415) {
            p = 7;
        } else if (((Double) i[2]).doubleValue() > 0.415) {
            p = Bla.N704d6e8381(i);
        }
        return p;
    }

    static double N704d6e8381(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 6;
        } else if (((Double) i[4]).doubleValue() <= 0.8035) {
            p = 6;
        } else if (((Double) i[4]).doubleValue() > 0.8035) {
            p = 5;
        }
        return p;
    }

    static double N43a0cee982(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 0.2345) {
            p = Bla.Neb2111283(i);
        } else if (((Double) i[7]).doubleValue() > 0.2345) {
            p = Bla.N149494d894(i);
        }
        return p;
    }

    static double Neb2111283(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 3;
        } else if (((Double) i[3]).doubleValue() <= 0.165) {
            p = Bla.N2eda094084(i);
        } else if (((Double) i[3]).doubleValue() > 0.165) {
            p = 4;
        }
        return p;
    }

    static double N2eda094084(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() <= 0.525) {
            p = Bla.N3578436e85(i);
        } else if (((Double) i[1]).doubleValue() > 0.525) {
            p = Bla.N706a04ae86(i);
        }
        return p;
    }

    static double N3578436e85(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (i[0].equals("F")) {
            p = 5;
        } else if (i[0].equals("I")) {
            p = 3;
        } else if (i[0].equals("M")) {
            p = 3;
        }
        return p;
    }

    static double N706a04ae86(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() <= 0.445) {
            p = Bla.N6eceb13087(i);
        } else if (((Double) i[2]).doubleValue() > 0.445) {
            p = Bla.N954b04f93(i);
        }
        return p;
    }

    static double N6eceb13087(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() <= 0.1945) {
            p = Bla.N10a035a088(i);
        } else if (((Double) i[6]).doubleValue() > 0.1945) {
            p = 3;
        }
        return p;
    }

    static double N10a035a088(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 4;
        } else if (i[0].equals("F")) {
            p = Bla.N67b467e989(i);
        } else if (i[0].equals("I")) {
            p = Bla.N47db50c590(i);
        } else if (i[0].equals("M")) {
            p = Bla.N5c072e3f91(i);
        }
        return p;
    }

    static double N67b467e989(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() <= 0.4165) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() > 0.4165) {
            p = 3;
        }
        return p;
    }

    static double N47db50c590(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() <= 0.1625) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() > 0.1625) {
            p = 4;
        }
        return p;
    }

    static double N5c072e3f91(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() <= 0.545) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() > 0.545) {
            p = Bla.N4d1b0d2a92(i);
        }
        return p;
    }

    static double N4d1b0d2a92(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() <= 0.177) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() > 0.177) {
            p = 4;
        }
        return p;
    }

    static double N954b04f93(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 3;
        } else if (i[0].equals("F")) {
            p = 3;
        } else if (i[0].equals("I")) {
            p = 5;
        } else if (i[0].equals("M")) {
            p = 5;
        }
        return p;
    }

    static double N149494d894(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.3965) {
            p = Bla.N710726a395(i);
        } else if (((Double) i[5]).doubleValue() > 0.3965) {
            p = Bla.N4c762604104(i);
        }
        return p;
    }

    static double N710726a395(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() <= 0.9535) {
            p = Bla.N646007f496(i);
        } else if (((Double) i[4]).doubleValue() > 0.9535) {
            p = 7;
        }
        return p;
    }

    static double N646007f496(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.3195) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() > 0.3195) {
            p = Bla.N481a15ff97(i);
        }
        return p;
    }

    static double N481a15ff97(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() <= 0.525) {
            p = 3;
        } else if (((Double) i[1]).doubleValue() > 0.525) {
            p = Bla.N78186a7098(i);
        }
        return p;
    }

    static double N78186a7098(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.435) {
            p = Bla.N306279ee99(i);
        } else if (((Double) i[2]).doubleValue() > 0.435) {
            p = Bla.N4cf4d528101(i);
        }
        return p;
    }

    static double N306279ee99(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 4;
        } else if (i[0].equals("F")) {
            p = 4;
        } else if (i[0].equals("I")) {
            p = Bla.N545997b1100(i);
        } else if (i[0].equals("M")) {
            p = 5;
        }
        return p;
    }

    static double N545997b1100(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= 0.8105) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() > 0.8105) {
            p = 6;
        }
        return p;
    }

    static double N4cf4d528101(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.47) {
            p = Bla.N77846d2c102(i);
        } else if (((Double) i[2]).doubleValue() > 0.47) {
            p = 3;
        }
        return p;
    }

    static double N77846d2c102(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() <= 0.337) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() > 0.337) {
            p = Bla.N548ad73b103(i);
        }
        return p;
    }

    static double N548ad73b103(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() <= 0.2785) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() > 0.2785) {
            p = 4;
        }
        return p;
    }

    static double N4c762604104(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 0.2565) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() > 0.2565) {
            p = Bla.N2641e737105(i);
        }
        return p;
    }

    static double N2641e737105(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.48) {
            p = Bla.N727803de106(i);
        } else if (((Double) i[2]).doubleValue() > 0.48) {
            p = Bla.N5c18298f115(i);
        }
        return p;
    }

    static double N727803de106(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.47) {
            p = Bla.N704921a5107(i);
        } else if (((Double) i[2]).doubleValue() > 0.47) {
            p = Bla.Nfcd6521113(i);
        }
        return p;
    }

    static double N704921a5107(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 6;
        } else if (((Double) i[5]).doubleValue() <= 0.476) {
            p = Bla.Ndf27fae108(i);
        } else if (((Double) i[5]).doubleValue() > 0.476) {
            p = Bla.N2f0a87b3111(i);
        }
        return p;
    }

    static double Ndf27fae108(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 4;
        } else if (((Double) i[7]).doubleValue() <= 0.2835) {
            p = Bla.N24a35978109(i);
        } else if (((Double) i[7]).doubleValue() > 0.2835) {
            p = 6;
        }
        return p;
    }

    static double N24a35978109(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 4;
        } else if (i[0].equals("F")) {
            p = 4;
        } else if (i[0].equals("I")) {
            p = 5;
        } else if (i[0].equals("M")) {
            p = Bla.N16f7c8c1110(i);
        }
        return p;
    }

    static double N16f7c8c1110(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() <= 0.2025) {
            p = 3;
        } else if (((Double) i[6]).doubleValue() > 0.2025) {
            p = 5;
        }
        return p;
    }

    static double N2f0a87b3111(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (i[0].equals("F")) {
            p = Bla.N319b92f3112(i);
        } else if (i[0].equals("I")) {
            p = 6;
        } else if (i[0].equals("M")) {
            p = 4;
        }
        return p;
    }

    static double N319b92f3112(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() <= 0.2745) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() > 0.2745) {
            p = 3;
        }
        return p;
    }

    static double Nfcd6521113(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.475) {
            p = Bla.N27d415d9114(i);
        } else if (((Double) i[2]).doubleValue() > 0.475) {
            p = 3;
        }
        return p;
    }

    static double N27d415d9114(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() <= 0.2305) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() > 0.2305) {
            p = 4;
        }
        return p;
    }

    static double N5c18298f115(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 6;
        } else if (((Double) i[5]).doubleValue() <= 0.5265) {
            p = 6;
        } else if (((Double) i[5]).doubleValue() > 0.5265) {
            p = 4;
        }
        return p;
    }

    static double N31f924f5116(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 8;
        } else if (((Double) i[5]).doubleValue() <= 0.4225) {
            p = Bla.N5579bb86117(i);
        } else if (((Double) i[5]).doubleValue() > 0.4225) {
            p = Bla.N7a36aefa123(i);
        }
        return p;
    }

    static double N5579bb86117(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 8;
        } else if (((Double) i[2]).doubleValue() <= 0.43) {
            p = 8;
        } else if (((Double) i[2]).doubleValue() > 0.43) {
            p = Bla.N5204062d118(i);
        }
        return p;
    }

    static double N5204062d118(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() <= 0.358) {
            p = Bla.N4fcd19b3119(i);
        } else if (((Double) i[7]).doubleValue() > 0.358) {
            p = Bla.N5d11346a122(i);
        }
        return p;
    }

    static double N4fcd19b3119(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() <= 0.555) {
            p = 5;
        } else if (((Double) i[1]).doubleValue() > 0.555) {
            p = Bla.N376b4233120(i);
        }
        return p;
    }

    static double N376b4233120(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 7;
        } else if (((Double) i[1]).doubleValue() <= 0.57) {
            p = 7;
        } else if (((Double) i[1]).doubleValue() > 0.57) {
            p = Bla.N2fd66ad3121(i);
        }
        return p;
    }

    static double N2fd66ad3121(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() <= 0.2455) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() > 0.2455) {
            p = 8;
        }
        return p;
    }

    static double N5d11346a122(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 9;
        } else if (((Double) i[6]).doubleValue() <= 0.2215) {
            p = 9;
        } else if (((Double) i[6]).doubleValue() > 0.2215) {
            p = 7;
        }
        return p;
    }

    static double N7a36aefa123(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() <= 0.389) {
            p = Bla.N17211155124(i);
        } else if (((Double) i[7]).doubleValue() > 0.389) {
            p = Bla.N2f8f5f62158(i);
        }
        return p;
    }

    static double N17211155124(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() <= 0.3325) {
            p = Bla.Nb3d7190125(i);
        } else if (((Double) i[6]).doubleValue() > 0.3325) {
            p = Bla.N134593bf155(i);
        }
        return p;
    }

    static double Nb3d7190125(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.581) {
            p = Bla.N5fdba6f9126(i);
        } else if (((Double) i[5]).doubleValue() > 0.581) {
            p = Bla.N16022d9d146(i);
        }
        return p;
    }

    static double N5fdba6f9126(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() <= 0.185) {
            p = Bla.N10d59286127(i);
        } else if (((Double) i[3]).doubleValue() > 0.185) {
            p = 6;
        }
        return p;
    }

    static double N10d59286127(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() <= 0.175) {
            p = Bla.Nfe18270128(i);
        } else if (((Double) i[3]).doubleValue() > 0.175) {
            p = 5;
        }
        return p;
    }

    static double Nfe18270128(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.5675) {
            p = Bla.N6fb0d3ed129(i);
        } else if (((Double) i[5]).doubleValue() > 0.5675) {
            p = 4;
        }
        return p;
    }

    static double N6fb0d3ed129(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 5;
        } else if (i[0].equals("F")) {
            p = Bla.N6dde5c8c130(i);
        } else if (i[0].equals("I")) {
            p = 5;
        } else if (i[0].equals("M")) {
            p = Bla.N1f021e6c138(i);
        }
        return p;
    }

    static double N6dde5c8c130(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.523) {
            p = Bla.N5123a213131(i);
        } else if (((Double) i[5]).doubleValue() > 0.523) {
            p = Bla.N281e3708136(i);
        }
        return p;
    }

    static double N5123a213131(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.495) {
            p = Bla.N52525845132(i);
        } else if (((Double) i[2]).doubleValue() > 0.495) {
            p = Bla.N68ceda24135(i);
        }
        return p;
    }

    static double N52525845132(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.48) {
            p = Bla.N3b94d659133(i);
        } else if (((Double) i[2]).doubleValue() > 0.48) {
            p = Bla.N24b1d79b134(i);
        }
        return p;
    }

    static double N3b94d659133(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() <= 0.233) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() > 0.233) {
            p = 5;
        }
        return p;
    }

    static double N24b1d79b134(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 6;
        } else if (((Double) i[4]).doubleValue() <= 1.113) {
            p = 6;
        } else if (((Double) i[4]).doubleValue() > 1.113) {
            p = 4;
        }
        return p;
    }

    static double N68ceda24135(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 6;
        } else if (((Double) i[1]).doubleValue() <= 0.63) {
            p = 6;
        } else if (((Double) i[1]).doubleValue() > 0.63) {
            p = 5;
        }
        return p;
    }

    static double N281e3708136(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.5515) {
            p = Bla.N35a50a4c137(i);
        } else if (((Double) i[5]).doubleValue() > 0.5515) {
            p = 6;
        }
        return p;
    }

    static double N35a50a4c137(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.485) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() > 0.485) {
            p = 4;
        }
        return p;
    }

    static double N1f021e6c138(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() <= 0.165) {
            p = Bla.N103f852139(i);
        } else if (((Double) i[3]).doubleValue() > 0.165) {
            p = Bla.N6d3af739144(i);
        }
        return p;
    }

    static double N103f852139(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 4;
        } else if (((Double) i[5]).doubleValue() <= 0.5075) {
            p = Bla.N587c290d140(i);
        } else if (((Double) i[5]).doubleValue() > 0.5075) {
            p = 5;
        }
        return p;
    }

    static double N587c290d140(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() <= 0.595) {
            p = 4;
        } else if (((Double) i[1]).doubleValue() > 0.595) {
            p = Bla.N4516af24141(i);
        }
        return p;
    }

    static double N4516af24141(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 4;
        } else if (((Double) i[3]).doubleValue() <= 0.16) {
            p = Bla.N4ae82894142(i);
        } else if (((Double) i[3]).doubleValue() > 0.16) {
            p = 5;
        }
        return p;
    }

    static double N4ae82894142(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 5;
        } else if (((Double) i[4]).doubleValue() <= 1.12) {
            p = Bla.N543788f3143(i);
        } else if (((Double) i[4]).doubleValue() > 1.12) {
            p = 4;
        }
        return p;
    }

    static double N543788f3143(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 7;
        } else if (((Double) i[4]).doubleValue() <= 1.0675) {
            p = 7;
        } else if (((Double) i[4]).doubleValue() > 1.0675) {
            p = 5;
        }
        return p;
    }

    static double N6d3af739144(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() <= 0.17) {
            p = 5;
        } else if (((Double) i[3]).doubleValue() > 0.17) {
            p = Bla.N1da51a35145(i);
        }
        return p;
    }

    static double N1da51a35145(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() <= 0.344) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() > 0.344) {
            p = 6;
        }
        return p;
    }

    static double N16022d9d146(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 4;
        } else if (i[0].equals("F")) {
            p = Bla.N7e9a5fbe147(i);
        } else if (i[0].equals("I")) {
            p = 6;
        } else if (i[0].equals("M")) {
            p = Bla.N768b970c150(i);
        }
        return p;
    }

    static double N7e9a5fbe147(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() <= 1.265) {
            p = 4;
        } else if (((Double) i[4]).doubleValue() > 1.265) {
            p = Bla.N44a3ec6b148(i);
        }
        return p;
    }

    static double N44a3ec6b148(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() <= 0.3015) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() > 0.3015) {
            p = Bla.N71623278149(i);
        }
        return p;
    }

    static double N71623278149(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() <= 0.312) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() > 0.312) {
            p = 6;
        }
        return p;
    }

    static double N768b970c150(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.52) {
            p = Bla.N5a4041cc151(i);
        } else if (((Double) i[2]).doubleValue() > 0.52) {
            p = 5;
        }
        return p;
    }

    static double N5a4041cc151(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.485) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() > 0.485) {
            p = Bla.N15b3e5b152(i);
        }
        return p;
    }

    static double N15b3e5b152(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 6;
        } else if (((Double) i[2]).doubleValue() <= 0.495) {
            p = 6;
        } else if (((Double) i[2]).doubleValue() > 0.495) {
            p = Bla.N61ca2dfa153(i);
        }
        return p;
    }

    static double N61ca2dfa153(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() <= 0.267) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() > 0.267) {
            p = Bla.N4b53f538154(i);
        }
        return p;
    }

    static double N4b53f538154(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() <= 0.51) {
            p = 4;
        } else if (((Double) i[2]).doubleValue() > 0.51) {
            p = 6;
        }
        return p;
    }

    static double N134593bf155(Object[] i) {
        double p = Double.NaN;
        if (i[0] == null) {
            p = 6;
        } else if (i[0].equals("F")) {
            p = Bla.N4bb4de6a156(i);
        } else if (i[0].equals("I")) {
            p = 8;
        } else if (i[0].equals("M")) {
            p = Bla.N7ba18f1b157(i);
        }
        return p;
    }

    static double N4bb4de6a156(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() <= 0.352) {
            p = 4;
        } else if (((Double) i[6]).doubleValue() > 0.352) {
            p = 5;
        }
        return p;
    }

    static double N7ba18f1b157(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.672) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() > 0.672) {
            p = 6;
        }
        return p;
    }

    static double N2f8f5f62158(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 6;
        } else if (((Double) i[3]).doubleValue() <= 0.22) {
            p = Bla.N1068e947159(i);
        } else if (((Double) i[3]).doubleValue() > 0.22) {
            p = 5;
        }
        return p;
    }

    static double N1068e947159(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 5;
        } else if (((Double) i[5]).doubleValue() <= 0.618) {
            p = Bla.N7dc222ae160(i);
        } else if (((Double) i[5]).doubleValue() > 0.618) {
            p = Bla.Nb9afc07165(i);
        }
        return p;
    }

    static double N7dc222ae160(Object[] i) {
        double p = Double.NaN;
        if (i[4] == null) {
            p = 8;
        } else if (((Double) i[4]).doubleValue() <= 1.202) {
            p = 8;
        } else if (((Double) i[4]).doubleValue() > 1.202) {
            p = Bla.Naecb35a161(i);
        }
        return p;
    }

    static double Naecb35a161(Object[] i) {
        double p = Double.NaN;
        if (i[2] == null) {
            p = 5;
        } else if (((Double) i[2]).doubleValue() <= 0.525) {
            p = Bla.N5fcd892a162(i);
        } else if (((Double) i[2]).doubleValue() > 0.525) {
            p = Bla.N6483f5ae164(i);
        }
        return p;
    }

    static double N5fcd892a162(Object[] i) {
        double p = Double.NaN;
        if (i[6] == null) {
            p = 5;
        } else if (((Double) i[6]).doubleValue() <= 0.303) {
            p = Bla.N8b87145163(i);
        } else if (((Double) i[6]).doubleValue() > 0.303) {
            p = 5;
        }
        return p;
    }

    static double N8b87145163(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() <= 0.4085) {
            p = 5;
        } else if (((Double) i[7]).doubleValue() > 0.4085) {
            p = 6;
        }
        return p;
    }

    static double N6483f5ae164(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 10;
        } else if (((Double) i[3]).doubleValue() <= 0.19) {
            p = 10;
        } else if (((Double) i[3]).doubleValue() > 0.19) {
            p = 9;
        }
        return p;
    }

    static double Nb9afc07165(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 6;
        } else if (((Double) i[7]).doubleValue() <= 0.449) {
            p = 6;
        } else if (((Double) i[7]).doubleValue() > 0.449) {
            p = Bla.N382db087166(i);
        }
        return p;
    }

    static double N382db087166(Object[] i) {
        double p = Double.NaN;
        if (i[1] == null) {
            p = 7;
        } else if (((Double) i[1]).doubleValue() <= 0.65) {
            p = 7;
        } else if (((Double) i[1]).doubleValue() > 0.65) {
            p = Bla.N73d4cc9e167(i);
        }
        return p;
    }

    static double N73d4cc9e167(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 6;
        } else if (((Double) i[3]).doubleValue() <= 0.205) {
            p = Bla.N80169cf168(i);
        } else if (((Double) i[3]).doubleValue() > 0.205) {
            p = 6;
        }
        return p;
    }

    static double N80169cf168(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 6;
        } else if (((Double) i[7]).doubleValue() <= 0.602) {
            p = Bla.N5427c60c169(i);
        } else if (((Double) i[7]).doubleValue() > 0.602) {
            p = 7;
        }
        return p;
    }

    static double N5427c60c169(Object[] i) {
        double p = Double.NaN;
        if (i[3] == null) {
            p = 6;
        } else if (((Double) i[3]).doubleValue() <= 0.2) {
            p = Bla.N15bfd87170(i);
        } else if (((Double) i[3]).doubleValue() > 0.2) {
            p = 8;
        }
        return p;
    }

    static double N15bfd87170(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 6;
        } else if (((Double) i[5]).doubleValue() <= 0.8345) {
            p = 6;
        } else if (((Double) i[5]).doubleValue() > 0.8345) {
            p = Bla.N543e710e171(i);
        }
        return p;
    }

    static double N543e710e171(Object[] i) {
        double p = Double.NaN;
        if (i[7] == null) {
            p = 6;
        } else if (((Double) i[7]).doubleValue() <= 0.508) {
            p = Bla.N57f23557172(i);
        } else if (((Double) i[7]).doubleValue() > 0.508) {
            p = 5;
        }
        return p;
    }

    static double N57f23557172(Object[] i) {
        double p = Double.NaN;
        if (i[5] == null) {
            p = 6;
        } else if (((Double) i[5]).doubleValue() <= 0.913) {
            p = 6;
        } else if (((Double) i[5]).doubleValue() > 0.913) {
            p = 7;
        }
        return p;
    }
}

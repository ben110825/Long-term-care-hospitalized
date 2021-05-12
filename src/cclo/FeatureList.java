package cclo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.Comparator;

/**
 *
 * @author panda
 */
public class FeatureList {

    ArrayList<Feature> ftList = new ArrayList<Feature>();
}

class Feature implements Serializable {

    ArrayList<KeyVal> kvList = new ArrayList<KeyVal>();
    int count = 0;
    int KVno;
    int type;

    public void getFeature(double a[]) {

        for (int i = 0; i < 128; i++) {
            if (a[i] > 2.0) {
                KeyVal kv = new KeyVal(i, a[i]);
                kvList.add(kv);
            }
        }
        KVno = kvList.size();
        java.util.Collections.sort(kvList, new KVcomp());
        java.util.Collections.reverse(kvList);

        if (KVno > 20) {
            KVno = 20;
        }
        while (kvList.size() > KVno) {
            kvList.remove(KVno);
        }
    }

    public boolean match(Feature pt_, boolean mod_) {
        int matched = 0;
        if (Math.abs(KVno - pt_.KVno) > 5) {
            return false;
        }
        for (KeyVal kv1 : pt_.kvList) {
            for (KeyVal kv2 : kvList) {
                if (kv1.idx == kv2.idx) {
                    matched++;
                    break;
                }
            }
        }
        int minMatch = KVno * 2 / 3;
        if (matched > minMatch) {
            if (mod_) {
                mix(pt_);
            }
            return true;
        } else {
            return false;
        }
    }

    public void mix(Feature pt_) {
        for (KeyVal kv1 : pt_.kvList) {
            boolean inList = false;
            for (KeyVal kv2 : kvList) {
                if (kv1.idx == kv2.idx) {
                    kv2.cnt++;
                    kv2.value = kv2.value * 0.95 + kv1.value * 0.05;
                    inList = true;
                    break;
                }
            }
            if (!inList) {
                kvList.add(kv1);
            }
        }
        java.util.Collections.sort(kvList, new KVcomp());
        java.util.Collections.reverse(kvList);

        while (kvList.size() > KVno) {
            kvList.remove(KVno);
        }

    }

    public String toString() {
        String result = "";
        for (KeyVal kv: kvList) {
            result += kv.toString() + " ";
        }
        return result;
    }
}

class KeyVal implements Serializable {

    DecimalFormat df = new DecimalFormat("0.0");
    public int idx;
    public double value;
    public int cnt = 0;

    public KeyVal(int x, double val) {
        idx = x;
        value = val;
    }

    public String toString() {
        return "[" + idx + "," + df.format(value) + "," + cnt + "]";
    }
}

class KVcomp implements Comparator {

    @Override
    public int compare(Object t, Object t1) {
        KeyVal x = (KeyVal) t;
        KeyVal y = (KeyVal) t1;
        if (x.cnt < y.cnt) {
            return -1;
        } else if (x.cnt > y.cnt) {
            return 1;
        } else if (x.value < y.value) {
            return -1;
        } else {
            return 1;
        }
    }

}

import java.util.*;

public class Main_1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String inputOne = in.nextLine();
        String inputSecond = in.nextLine();
        String inputK = in.nextLine();
        if (inputOne.trim().length() <= 0 || inputSecond.trim().length() <= 0 || inputK.trim().length() <= 0) {
            return;
        }

        String[] inputStrArr = inputOne.split(" ");
        String[] inputSecondArr = inputSecond.split(" ");
        Integer k = Integer.valueOf(inputK);
        int[] first = strToInt(inputStrArr);
        int[] second = strToInt(inputSecondArr);

        int ft = first[0], st = second[0];
        if (ft <= 0 || ft > 100 || st <= 0 || st > 100) {
            return;
        }

        if (k <= 0 || k > ((first.length - 1) * (second.length - 1))) {
            return;
        }

        for (int i = 1, len = first.length; i < len; i++) {
            if (first[i] < 0 || first[i] > 1000) {

                return;
            }
        }
        for (int i = 1, len = second.length; i < len; i++) {
            if (second[i] < 0 || second[i] > 1000) {
                return;
            }
        }
        Map<Integer, Integer> keyPairs = new HashMap<>();
        int c = 0;
//        first = sort(first);
//        second = sort(sort(second));


        int[] f = new int[k];
        int[] s = new int[k];
        for (int j = 0; j < k; j++) {
            f[j] = first[j + 1];
            s[j] = second[j + 1];
        }

        Integer pow = (int) Math.pow(2, k);
        Set<Node> ns = new HashSet<>();
        Node[] nsArr = new Node[pow];
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < s.length; j++) {
                ns.add(new Node(f[i], s[j]));
            }
        }
        // 找到最小的 k 对
        int cna = 0;
        for (Node n : ns) {
            nsArr[cna] = n;
            cna++;
        }
        for (int i = 0; i < nsArr.length; i++) {
            for (int j = 0; j < nsArr.length - i - 1; j++) {
                Node tmpSwitch = null;
                Node fn = nsArr[j];
                Node fns = nsArr[j + 1];
                int jn = fn.key + fn.value;
                int jns = fns.key + fn.value;
                if (jn > jns) {
                    tmpSwitch = nsArr[j];
                    nsArr[j] = nsArr[j + 1];
                    nsArr[j + 1] = tmpSwitch;
                }
            }
        }
        int result = 0;
        for (int i = 0; i < k; i++) {
            int v = nsArr[i].key + nsArr[i].value;
            result += v;
        }
        System.out.println(result);

        //...

    }

    public static int[] sort(int[] arrObj) {
        for (int i = 0; i < arrObj.length; i++) {
            for (int j = 0; j < arrObj.length - i - 1; j++) {
                int tmpSwitch = 0;
                if (arrObj[j] > arrObj[j + 1]) {
                    tmpSwitch = arrObj[j];
                    arrObj[j] = arrObj[j + 1];
                    arrObj[j + 1] = tmpSwitch;
                }
            }
        }
        return arrObj;
    }

    public static int[] strToInt(String[] strArr) {
        int[] intArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            intArr[i] = Integer.valueOf(strArr[i]);
        }
        return intArr;
    }

    static class Node {
        public Node() {
        }

        public Node(Integer k, Integer v) {
            this.key = k;
            this.value = v;
        }

        private Integer key;
        private Integer value;

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }


}


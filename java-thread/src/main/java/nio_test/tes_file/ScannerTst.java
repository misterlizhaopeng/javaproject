package nio_test.tes_file;

import java.util.Scanner;

/**
 * @ClassName test_del.C
 * @Deacription TODO
 * @Author LP
 * @Date 2021/1/29 21:53
 * @Version 1.0
 **/
public class ScannerTst {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String content = scanner.nextLine();
            if ("Done".equals(content)){
                break;
            }
            System.out.println(content);
        }
    }
}


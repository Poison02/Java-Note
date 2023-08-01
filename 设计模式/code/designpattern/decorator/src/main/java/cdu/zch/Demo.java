package cdu.zch;

import cdu.zch.decorators.*;

/**
 * @author Zch
 * @date 2023/8/1
 **/
public class Demo {

    public static void main(String[] args) {
        String salaryRecords = "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000";
        DataSourceDecorator encoded = new CompressionDecorator(
                new EncryptionDecorator(
                        new FileDataSource("D:\\笔记\\Java-Note\\设计模式\\code\\designpattern\\decorator\\out\\OutputDemo.txt")));
        encoded.writeData(salaryRecords);
        DataSource plain = new FileDataSource("D:\\笔记\\Java-Note\\设计模式\\code\\designpattern\\decorator\\out\\OutputDemo.txt");

        System.out.println("- Input ----------------");
        System.out.println(salaryRecords);
        System.out.println("- Encoded --------------");
        System.out.println(plain.readData());
        System.out.println("- Decoded --------------");
        System.out.println(encoded.readData());
    }

}

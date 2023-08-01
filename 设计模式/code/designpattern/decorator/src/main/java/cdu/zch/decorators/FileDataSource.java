package cdu.zch.decorators;

import java.io.*;

/**
 * 简单数据读写器
 * @author Zch
 * @date 2023/8/1
 **/
public class FileDataSource implements DataSource{

    private String name;

    public FileDataSource(String name) {
        this.name = name;
    }

    @Override
    public void writeData(String data) {
        File file = new File(name);
        try (OutputStream fos = new FileOutputStream(file)) {
            fos.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String readData() {
        char[] buffer = null;
        File file = new File(name);
        try (FileReader reader = new FileReader(file)) {
            buffer = new char[(int) file.length()];
            reader.read(buffer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new String(buffer);
    }

}

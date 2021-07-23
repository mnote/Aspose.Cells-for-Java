import com.aspose.cells.License;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * https://gitee.com/evilrule/crack-aspose/tree/master/
 */
public class CrackAspose {

    public static void main(String[] args) throws Exception {

        String filename = "/Users/maoling/.m2/repository/com/aspose/aspose-cells/20.10/aspose-cells-20.10.jar";

        //FileUtils.copyFile(new File(filename + ".bak"), new File(filename ));

        if(!new File(filename + ".bak").exists()) {
            FileUtils.copyFile(new File(filename), new File(filename + ".bak"));
        }

        try{
            crack(filename);
            System.out.println("Success!");
        }catch (Exception e){
            e.printStackTrace();
        }

        jarFile(new File(filename));


        boolean auth = license();
        if (!auth) {
            System.out.println("aspose 许可无效！");
            return;
        }


        System.out.println("aspose 已就绪！");


        try{
            Workbook wb = new Workbook();
            Worksheet ws = wb.getWorksheets().get(0);
            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    ws.getCells().get(i,j).setValue((i + 1) + "*" + (j + 1) + "=" + (i + 1) * (j + 1));
                }
            }

            File dir = new File(System.getProperty("user.dir"));
            File xls = new File(dir, "test.xlsx");

            wb.save(xls.getAbsolutePath());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static boolean license() {
        boolean result = false;
        try {
            InputStream is = com.aspose.cells.License.class.getResourceAsStream("/Aspose.Cells.Java.lic");
            License asposeLicense = new License();
            asposeLicense.setLicense(is);
            is.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void jarFile(File jarfile) throws IOException {

        JarFile jarFile = new JarFile(jarfile);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10240);

        JarOutputStream jarOutputStream = new JarOutputStream(byteArrayOutputStream);

        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {

            JarEntry nextEntry = entries.nextElement();

            if(nextEntry.getName().startsWith("META-INF/") && nextEntry.getName().endsWith(".RSA")) {
                System.out.println(nextEntry.getName());
                continue;
            }

            if(nextEntry.getName().startsWith("META-INF/") && nextEntry.getName().endsWith(".SF")) {
                System.out.println(nextEntry.getName());
                continue;
            }

            if(nextEntry.getName().startsWith("META-INF/") && nextEntry.getName().endsWith(".MF")) {
                System.out.println(nextEntry.getName());
                continue;
            }

            if(nextEntry.getName().equals("Aspose.Cells.Java.lic")) {
                System.out.println(nextEntry.getName());
                continue;
            }

            JarEntry entryCopy = new JarEntry(nextEntry.getName());
            jarOutputStream.putNextEntry(entryCopy);

            InputStream inputStream = jarFile.getInputStream(nextEntry);
            byte[] data = IOUtils.toByteArray(inputStream);
            IOUtils.closeQuietly(inputStream);

            if (nextEntry.getName().equals("com/aspose/cells/License.class")) {
                File dir = new File(System.getProperty("user.dir"));
                dir = new File(dir, "com");
                dir = new File(dir, "aspose");
                dir = new File(dir, "cells");
                File file = new File(dir, "License.class");
                InputStream fileInputStream = new FileInputStream(file);
                data = IOUtils.toByteArray(fileInputStream);
                IOUtils.closeQuietly(fileInputStream);
            }

            jarOutputStream.write(data);
            jarOutputStream.closeEntry();

        }

        jarFile.close();

        ////
        JarEntry licEntry = new JarEntry("Aspose.Cells.Java.lic");
        jarOutputStream.putNextEntry(licEntry);
        File dir = new File(System.getProperty("user.dir"));
        File lic = new File(dir, "Aspose.Cells.Java.lic");
        InputStream licInputStream = new FileInputStream(lic);
        jarOutputStream.write(IOUtils.toByteArray(licInputStream));
        jarOutputStream.closeEntry();
        IOUtils.closeQuietly(licInputStream);

        jarOutputStream.close();

        FileUtils.forceDelete(jarfile);

        FileUtils.writeByteArrayToFile(jarfile, byteArrayOutputStream.toByteArray());

    }

    public static void crack(String JarPath) throws Exception {
        ClassPool pool = ClassPool.getDefault();

        pool.insertClassPath(JarPath);

        CtClass cc_License = pool.get("com.aspose.cells.License");

        CtConstructor[] ctConstructors = cc_License.getConstructors();
        for(int i=0;i<ctConstructors.length;i++){
            ctConstructors[i].setBody("{    a = this;\n" +
                    "    com.aspose.cells.zbjc.a();}");
        }

        CtMethod method_isLicenseSet = cc_License.getDeclaredMethod("isLicenseSet");
        method_isLicenseSet.setBody("return true;");

        CtClass cc_License2 = pool.get("com.aspose.cells.License");
//        CtMethod method_setLicense = cc_License2.getDeclaredMethod("setLicense");
//        method_setLicense.setBody("{    a = new com.aspose.cells.License();\n" +
//                "    com.aspose.cells.zbjc.a();}");

        CtMethod[] method_setLicenses = cc_License2.getDeclaredMethods("setLicense");
        for(int i=0;i<method_setLicenses.length;i++){
            method_setLicenses[i].setBody("{    a = new com.aspose.cells.License();\n" +
                    "    com.aspose.cells.zbjc.a();}");
        }

        CtMethod method_k = cc_License.getDeclaredMethod("k");
        method_k.setBody("return new java.util.Date(Long.MAX_VALUE);");

        System.out.println(new File(System.getProperty("user.dir")).toString());

        cc_License.writeFile(new File(System.getProperty("user.dir")).toString());
    }

}

package demo.ypc.cx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinpengcheng on 2017/8/30.
 */

public class XXXXX {

    public static void main(String[] args){
        File file = new File("/Users/yinpengcheng/Desktop/shelters.txt");//文件对象
        File file2 = new File("/Users/yinpengcheng/Desktop/petsFile.txt");
        List<petsEntity> petList = new ArrayList<petsEntity>();//存储文件对象的容器
        List<shelterEntity> shelterList;
        try {
            shelterList = readShelter(file);
            petList = readPets(file2);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Do you want to search a pet or a shelter?");
        String petOrShelter = null;
        try {
            petOrShelter = getInput();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (petOrShelter.equals("pet")){
            System.out.println("Enter the type of pet:Dog or Cat?");
            String typeOfAnimal = null;
            try {
                typeOfAnimal = getInput();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("The following breeds are available:");
            for (petsEntity pet : petList){
                if (pet.type.equals(typeOfAnimal)){
                    System.out.println(pet.breed);
                }
            }
        }


    }
    private static List<shelterEntity> readShelter(File file) throws Exception{
        FileReader fileReader1 = new FileReader(file);//读取文件内容
        BufferedReader br = new BufferedReader(fileReader1);//缓冲区
        String line;
        List<shelterEntity> shelterList = new ArrayList<>();
        while(br.ready()){
            line = br.readLine();//逐行读取
//            System.out.println(line);//此为打印工具
            //以下是数据解析
            String[] newLine = line.split("; ");
            String[] citys = newLine[1].split(", ");
//            System.out.println(citys[1]);
            shelterEntity shelter = new shelterEntity();
            shelter.setName(newLine[0]);
            shelter.setStreetAdress(citys[0]);
            shelter.setCity(citys[1]);
            shelter.setState(citys[2]);
            shelter.setZipcode(citys[3]);
            shelter.setPhoneNumber(newLine[2]);
            shelter.setDescription(newLine[3]);
            shelterList.add(shelter);//添加到容器
        }
        return shelterList;
    }
    private static List<petsEntity> readPets(File file) throws Exception{
        FileReader fileReader = new FileReader(file);
        BufferedReader br = new BufferedReader(fileReader);
        String line;
        List<petsEntity> petsList = new ArrayList<>();
        while (br.ready()){
            line = br.readLine();
//            System.out.println(line);
            String[] newLine = line.split(", ");
            petsEntity pets = new petsEntity();
            pets.setType(newLine[0]);
            pets.setBreed(newLine[1]);
            pets.setName(newLine[2]);
            pets.setSize(newLine[3]);
            pets.setAge(newLine[4]);
            pets.setGender(newLine[5]);
            pets.setValue(newLine[6].equals("true")? true:false);
            pets.setCity(newLine[7]);
            pets.setShelter(newLine[8]);
//            System.out.println(pets.shelter);
            petsList.add(pets);
        }
        return petsList;
    }
    private static String getInput()throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        str = br.readLine();
//        System.out.println(str);
        return str;
    }

}

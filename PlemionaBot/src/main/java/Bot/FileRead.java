package Bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileRead {

	public static void main(String[] args) {
		
        String sciezkaDoPliku = ".\\src\\main\\resources\\kordynaty.txt";
        List<String> cords = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(new File(sciezkaDoPliku));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String linia;
            // Odczyt linia po linii z pliku
            while ((linia = bufferedReader.readLine()) != null) {
                String[] splittedCords = linia.split(" ");
                cords.addAll(Arrays.asList(splittedCords));
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        
//        for (String cord : cords) {
//            System.out.println(cord);
//        }
//        
//        System.out.println(cords.get(2));

	}

}

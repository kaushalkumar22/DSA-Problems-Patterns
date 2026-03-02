package code.arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvFolderReader {

    static Map<String, Set<String>> allCompany = new HashMap<>();
    static Map<String, Set<String>> all = new HashMap<>();
    static Map<String, Set<String>> morethansixmonths = new HashMap<>();
    static Map<String, Set<String>>  sixmonths = new HashMap<>();
    static Map<String, Set<String>> threemonths = new HashMap<>();
    static Map<String, Set<String>> thirtydays = new HashMap<>();


    public static void main(String[] args) {
        String parentDir = "/Users/kaushalkumar/Documents/pl_merged_csv.csv"; // 🔹 Update with your path


        //csvName:[all, more-than-six-months, thirty-days, three-months, six-months]
        File folder = new File(parentDir);
        List<String> rows =  readCsv( folder);
        Map<Integer,Integer> freq = new HashMap<>();
        Map<Integer,String> keyVsRows = new HashMap<>();
        for(String row : rows) {
            String[] cols = row.split(",");
            int key = Integer.valueOf(cols[0]);
            freq.put(key, freq.getOrDefault(key, 0) + 1);
           if(keyVsRows.containsKey(key)){
               String rowString = keyVsRows.get(key);
               String[] countColm = rowString.split(",");
               if( Integer.valueOf(countColm[4])<Integer.valueOf(cols[4])){
                   keyVsRows.put(Integer.valueOf(cols[0]),row);
               }
           }else{
               keyVsRows.put(Integer.valueOf(cols[0]),row);
           }
        }

        List[] bucket = new List[5];
        for(Map.Entry<Integer,Integer> entry : freq.entrySet()){
            if(bucket[entry.getValue()]==null){
                bucket[entry.getValue()] = new ArrayList<>();
            }
            bucket[entry.getValue()].add(entry.getKey());
        }
        List<Integer> ql = bucket[4];
        for(int id : ql){
            System.out.println(keyVsRows.get(id));
        }

    }

    private static List<String> readCsv(File csvFile) {
        List<String> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("ID,URL")) {
                    continue; // skip header
                }
                rows.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + csvFile.getName());
            e.printStackTrace();
        }
        return rows;
    }

}

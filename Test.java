import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
    static String pkg = "mathandgeometry";
    public static void main(String[] args) {

        String[] mathAndGeometryFiles = {
                "RotateImage.java",
                "SpiralMatrix.java",
                "SetMatrixZeroes.java",
                "HappyNumber.java",
                "PlusOne.java",
                "PowXN.java",
                "MultiplyStrings.java",
                "DetectSquares.java"
        };

        for (String fileName : mathAndGeometryFiles){
           String path = "C:\\Users\\kaush\\OneDrive\\Desktop\\MD\\"+fileName;
           // Create file and write content
           createJavaFile(path, createFileContent(fileName));
       }


        String[] oneDDynamicProgrammingFiles = {
                "ClimbingStairs.java",
                "MinCostClimbingStairs.java",
                "HouseRobber.java",
                "HouseRobberII.java",
                "LongestPalindromicSubstring.java",
                "PalindromicSubstrings.java",
                "DecodeWays.java",
                "CoinChange.java",
                "MaximumProductSubarray.java",
                "WordBreak.java",
                "LongestIncreasingSubsequence.java",
                "PartitionEqualSubsetSum.java"
        };
        String[] twoDDynamicProgrammingFiles = {
                "UniquePaths.java",
                "LongestCommonSubsequence.java",
                "BestTimeToBuyAndSellStockWithCooldown.java",
                "CoinChangeII.java",
                "TargetSum.java",
                "InterleavingString.java",
                "LongestIncreasingPathInMatrix.java",
                "DistinctSubsequences.java",
                "EditDistance.java",
                "BurstBalloons.java",
                "RegularExpressionMatching.java"
        };

    }
     private static String createFileContent(String fileName){
         // Content of the file
         fileName = fileName.replace(".java","");
         String fileContent =
                 "package code."+pkg+";\n" +
                 "public class "+fileName+" {\n" +
                         "    public static void main(String[] args) {\n" +
                         "    }\n" +
                         "}";
         return fileContent;
    }
    public static void createJavaFile(String fileName, String fileContent) {
        try {
            // Create a new file object
            File file = new File(fileName);

            // Create the file if it doesn't exist
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            // Write the content to the file
            FileWriter writer = new FileWriter(file);
            writer.write(fileContent);
            writer.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }
}

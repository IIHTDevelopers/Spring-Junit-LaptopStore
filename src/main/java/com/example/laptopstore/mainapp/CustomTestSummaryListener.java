package com.example.laptopstore.mainapp;

import java.io.PrintWriter;

import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import static com.example.laptopstore.mainapp.testutils.TestUtils.businessTestFile;
import static com.example.laptopstore.mainapp.testutils.TestUtils.yakshaAssert;




public class CustomTestSummaryListener extends SummaryGeneratingListener {

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        super.testPlanExecutionFinished(testPlan);
        // At this point, the summary is available. You can print it, log it, or store it as needed.
        
        System.out.println("Test execution summary:");
        getSummary().printTo(new PrintWriter(System.out)); // Print summary to console
        // For more customized handling, use getSummary() and its methods to get detailed results.
        String sourcePath = "src/main/java/com/example/laptopstore/test/LaptopStoreTests.java";
        String destPath = "src/test/java/com/example/laptopstore/test/LaptopStoreTests.java";
      copyFile(sourcePath, destPath);

      
      long success = getSummary().getTestsSucceededCount();
            double successPer= ((double)success/getSummary().getTestsFoundCount()) * 100;
      System.out.println("Success % : " + successPer);
      //yakshaAssert("TestCasesSuccess" + successPer + "%", true, businessTestFile);
     try{
      for(int i=10;i<=100;i+=10){
          if(i<=successPer)
              yakshaAssert("TestCasesSuccess " + i + "%", true, businessTestFile);
          else
              yakshaAssert("TestCasesSuccess " + i + "%", false, businessTestFile);
      }
    }catch(IOException ex){
        ex.printStackTrace();
    }

      
      try {
          String command = "mvn test";
          // For Windows, use something like: String command = "cmd /c echo Hello, World!";
          Process process = Runtime.getRuntime().exec(command);

          BufferedReader reader = 
              new BufferedReader(new InputStreamReader(process.getInputStream()));

          String line;
          while ((line = reader.readLine()) != null) {
              System.out.println(line);
          }

          int exitVal = process.waitFor();
          
      } catch (IOException | InterruptedException e) {
          e.printStackTrace();
      }



      String csvFile = "target/site/jacoco/jacoco.csv"; // Specify the path to your CSV file
      String linen = "";
      String cvsSplitBy = ","; // CSV delimiter, assuming it's a comma
      int rowNumber1 = 0;

      try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

          while ((linen = br.readLine()) != null) {
              // Increment row number
              rowNumber1++;

              // Use comma as separator
              String[] columns = linen.split(cvsSplitBy);

              // Check if the third column exists and contains "Ebillservice"
              if (columns.length > 2 && columns[2].contains("LaptopController")) {
                  //System.out.println("Text 'Ebillservice' found in row number: " + rowNumber);
                  break;
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }

      linen = "";
      
      int rowNumber2 = 0;
      try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

        while ((linen = br.readLine()) != null) {
            // Increment row number
            rowNumber2++;

            // Use comma as separator
            String[] columns = linen.split(cvsSplitBy);

            // Check if the third column exists and contains "Ebillservice"
            if (columns.length > 2 && columns[2].contains("LaptopServiceImpl")) {
                //System.out.println("Text 'Ebillservice' found in row number: " + rowNumber);
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    int missed = 0;
          int covered = 0;
      if(rowNumber1 > 0 && rowNumber2 > 0){
      String[] values = new String[11]; // Array to store values from columns 4 to 14
          int targetRowNumber = rowNumber1; // The specific row number you want to read from
          int currentRowNumber = 0;
          linen="";
      try (BufferedReader br1 = new BufferedReader(new FileReader(csvFile))) {

          while ((linen = br1.readLine()) != null) {
              // Increment the current row number
              currentRowNumber++;

              // When the current row is the target row
              if (currentRowNumber == targetRowNumber) {
                  // Split the line into columns
                  String[] columns = linen.split(cvsSplitBy);

                  // Assuming columns start at 0, so column 4 is index 3, and column 14 is index 13
                  for (int i = 3; i <= 13; i++) {
                      // Check if the column exists in this row
                      if (i < columns.length) {
                          values[i - 3] = columns[i];
                      } else {
                          // If the column does not exist, assign a default value or leave it null
                          values[i - 3] = ""; // Here assigning an empty string for missing columns
                      }
                  }

                  // After storing values, break the loop as we've found the target row
                  break;
              }
          }

          // Output the values for demonstration
          //System.out.println("Values from columns 4 to 14 of row " + targetRowNumber + ": " + Arrays.toString(values));
          
          for(int i=0;i<10;i+=2){
              missed += Integer.parseInt(values[i]);
              covered += Integer.parseInt(values[i+1]);
          }
        } catch (IOException e) {
            e.printStackTrace();
        }
          values = new String[11]; // Array to store values from columns 4 to 14
          targetRowNumber = rowNumber2; // The specific row number you want to read from
          currentRowNumber = 0;
          linen="";
      try (BufferedReader br1 = new BufferedReader(new FileReader(csvFile))) {

          while ((linen = br1.readLine()) != null) {
              // Increment the current row number
              currentRowNumber++;

              // When the current row is the target row
              if (currentRowNumber == targetRowNumber) {
                  // Split the line into columns
                  String[] columns = linen.split(cvsSplitBy);

                  // Assuming columns start at 0, so column 4 is index 3, and column 14 is index 13
                  for (int i = 3; i <= 13; i++) {
                      // Check if the column exists in this row
                      if (i < columns.length) {
                          values[i - 3] = columns[i];
                      } else {
                          // If the column does not exist, assign a default value or leave it null
                          values[i - 3] = ""; // Here assigning an empty string for missing columns
                      }
                  }

                  // After storing values, break the loop as we've found the target row
                  break;
              }
          }
          // Output the values for demonstration
          //System.out.println("Values from columns 4 to 14 of row " + targetRowNumber + ": " + Arrays.toString(values));
          
          for(int i=0;i<10;i+=2){
            missed += Integer.parseInt(values[i]);
            covered += Integer.parseInt(values[i+1]);
        }
      } catch (IOException e) {
          e.printStackTrace();
      }


          int total = missed + covered;
         
          double coverage = ((double)covered / total) * 100;
          System.out.println("Coverage% : " + coverage) ;
          try{
          for(int i=10;i<=100;i+=10){
              if(i<=coverage)
                  yakshaAssert("TestSuccessfullCoverage " + i + "%", true, businessTestFile);
              else
                  yakshaAssert("TestSuccessfullCoverage " + i + "%", false, businessTestFile);
          }
        
    }catch(IOException ex){
        
    }
      
  }else{
    try{
      for(int i=10;i<=100;i+=10){
                       
              yakshaAssert("TestSuccessfullCoverage " + i + "%", false, businessTestFile);
      } 
    }catch(IOException ex){
        
    }
  }
    }

    public static void copyFile(String sourcePathStr, String destPathStr) {
        // Convert string paths to Path objects
        Path sourcePath = Paths.get(sourcePathStr);
        Path destPath = Paths.get(destPathStr);
    
        try {
            // Copy the file from source to destination
            Files.copy(sourcePath, destPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            //System.out.println("File copied successfully.");
        } catch (IOException e) {
            //System.err.println("Error occurred while copying the file.");
            e.printStackTrace();
        }
    }
}
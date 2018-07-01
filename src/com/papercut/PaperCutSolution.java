package com.papercut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Ishwarya Sridharan calculate total cost of sample file
 *
 */
public class PaperCutSolution {

	/**
	 * @param to
	 *            read each job from file (eg: csv file)
	 * @return
	 */
	public static double readJobsFromFile(String fileLocation) {
		String csvFile = fileLocation;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		double totalJobCost = 0.0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				double totalcost = calculteForEachJOB(line, cvsSplitBy);
				totalJobCost = totalJobCost + totalcost;
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found " + fileLocation);
		} catch (IOException e) {
			System.out.println("ERROR: Could not read " + fileLocation);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("ERROR: Could not read " + fileLocation);
				}
			}
		}
		return totalJobCost;

	}

	/**
	 * calculate cost of each JOB (Single and Double side)
	 */
	public static double calculteForEachJOB(String line, String cvsSplitBy) {

		int colorpageCount = 0;
		int bwCount = 0;
		double jobcostBW = 0.0;
		double jobcostColour = 0.0;
		double totalcost = 0.0;
		// use comma as separator
		// check single side job if line contains false
		// single sided ; 15cents for balck&white ; 25 cents fo colored
		if (line.contains(PaperCutConstants.FALSE)) {
			String jobType = PaperCutConstants.JOBTYPESINGLE;
			colorpageCount = Integer.parseInt(line.split(cvsSplitBy)[1].trim());
			bwCount = Integer.parseInt(line.split(cvsSplitBy)[0]) - Integer.parseInt(line.split(cvsSplitBy)[1].trim());
			jobcostBW = bwCount * PaperCutConstants.BWSINGLECOST;
			jobcostColour = colorpageCount * PaperCutConstants.COLOURSINGLECOST;
			totalcost = jobcostBW + jobcostColour;
			printOutputWithTotalCost(jobType, colorpageCount, bwCount, jobcostColour, jobcostBW,totalcost);
			return totalcost;
		}

		// check double side job if line contains false
		// Double sided ; 10cents for balck&white ; 20 cents fo colored
		else if (line.contains(PaperCutConstants.TRUE)) {
			String jobType = PaperCutConstants.JOBTYPEDOUBLE;
			colorpageCount = Integer.parseInt(line.split(cvsSplitBy)[1].trim());
			bwCount = Integer.parseInt(line.split(cvsSplitBy)[0]) - Integer.parseInt(line.split(cvsSplitBy)[1].trim());
			jobcostBW = bwCount * PaperCutConstants.BWDOUBLECOST;
			jobcostColour = colorpageCount * PaperCutConstants.COLOURDOUBLECOST;
			totalcost = jobcostBW + jobcostColour;
			totalcost = Math.round(totalcost*100.0)/100.0;
			printOutputWithTotalCost(jobType, colorpageCount, bwCount, jobcostColour,jobcostBW ,totalcost);
			return totalcost;
		}
		return totalcost;
	}

	public static void printOutputWithTotalCost(String jobType, int colorpageCount, int bwCount, double jobcostBW,
			double jobcostColour, double totalcost) {

		System.out.format("%10s %10s %20s %10s %20s %20s", jobType, colorpageCount, "$"+jobcostColour , bwCount, "$"+jobcostBW,  "$"+totalcost);
		System.out.println();
		System.out.println();
		

	}
	

	public static void main(String[] args) {

		// Specify the file Location
		String fileLocation = "C:/Users/Ishwarya/Documents/input.csv";
		// Print the list objects in tabular format.
		System.out.println(
				"------------------------------------------------------------------------------------------------------");
		System.out.printf("%10s %20s %12s %10s %10s %20s", "JOB_TYPE", "COLOUR_PAGES", "COLOUR_COST", "BLACK&WHITE_PAGES", 
				"BLACK&WHITE_COST", "TOTAL_JOB_COST");
		System.out.println();
		System.out.println(
				"------------------------------------------------------------------------------------------------------");

		double totalJobCost = readJobsFromFile(fileLocation);
		
		System.out.println();
		System.out.printf("%10s","OVERALL JOB COST");
		System.out.println();
		System.out.println(
				"----------------");

		System.out.format("%10s","$"+Math.round(totalJobCost*100.0)/100.0);

		

	}
}

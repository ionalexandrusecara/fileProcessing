
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class P01FP extends Application {

	// Extension: Saving variables for spectrometer usage statistics
	static ArrayList<Integer> specsNo = new ArrayList<Integer>();
	static ArrayList<String> specsNames = new ArrayList<String>();

	// Saving variables for solvent usage statistics
	static ArrayList<Integer> solventNo = new ArrayList<Integer>();
	static ArrayList<String> solventNames = new ArrayList<String>();

	// Saving variables for solvent usage statistics
	static ArrayList<Integer> researchNo = new ArrayList<Integer>();
	static ArrayList<String> researchNames = new ArrayList<String>();

	// Boolean variables check whether given spectrometers, research groups have
	// already been added to arrayList
	static boolean okSpec, okResearch, okSolvent;

	// Index that counts the number of samples
	static int i = 0;

	public static void main(String[] args) throws IOException, FileNotFoundException {
		// File Not Found Exception
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data-large.csv"));
			try {
				PrintWriter writer = new PrintWriter("test.html");
				// Making changes to HTML file
				writer.write("<html><head><title>CS1003</title></head></html>");
				writer.println("<html><center>Main Task</center><br></br></html>");
				try {
					// Assigning first record values to shortest and longest
					// records
					String shortRec, longRec, record;
					String contents = reader.readLine();

					contents = reader.readLine();
					int noOfCommas;
					String str = contents;
					str = cutContentsNineTimes(str);
					shortRec = str.substring(0, str.indexOf(","));
					longRec = str.substring(0, str.indexOf(","));

					// Defining needed variables: i counts the number of
					// samples, sampleSpec the no of samples run with
					// spectrometer Alec
					int sampleSpec = 0, sampleExp = 0; // sampleExp counts
														// the no. of
														// samples run with
														// experiment
														// proton.a.and
					String sampleID, holderNo, spectrometer, resGroup, solvent, expName;

					// Going line by line in the file
					while (contents != null) {
						noOfCommas = 0;
						for (int k = 0; k < contents.length(); k++) {
							if (contents.charAt(k) == ',') {
								noOfCommas++;
							}
						}
						//System.out.println(noOfCommas); //Used for testing the the FileIsCorrupt exception
						if (noOfCommas != 35) {
							throw new FileIsCorrupt();
						}
						i++;
						okSpec = true;
						okResearch = true;
						okSolvent = true;

						// ID
						sampleID = contents.substring(0, contents.indexOf(","));
						contents = cutContentsThrice(contents);// String is
																// an
																// object,
																// hence no
																// need for
																// returning
																// value
						holderNo = contents.substring(0, contents.indexOf(","));

						// Extension: Shortest Time and Longest Time
						contents = cutContentsSixTimes(contents);
						record = contents.substring(0, contents.indexOf(","));
						if (shortRec.compareTo(record) > 0) {
							shortRec = record;
						}
						if (longRec.compareTo(record) < 0) {
							longRec = record;
						}

						// Experiment Name
						contents = cutContentsTwice(contents);
						expName = contents.substring(0, contents.indexOf(","));
						if (expName.equals("proton.a.and")) {
							sampleExp++;
						}

						// Research Group
						contents = cutContentsTwelveTimes(contents);
						resGroup = contents.substring(0, contents.indexOf(","));
						// Extension: Calculating Usage of a Research Group
						researchUsage(resGroup);

						// Solvent
						contents = cutContentsFiveTimes(contents);
						solvent = contents.substring(0, contents.indexOf(","));
						// Extension: Calculating Usage of a Solvent
						solventUsage(solvent);

						// Spectrometer
						contents = cutContentsSixTimes(contents);
						spectrometer = contents.substring(0, contents.indexOf(","));
						if (spectrometer.equals("Alec")) {
							sampleSpec++;
						}
						// Extension: Calculating Usage of a Spectrometer
						spectrometerUsage(spectrometer);

						// Printing on different lines
						writer.println("[Sample " + sampleID + "]");
						writer.println("<html><br></br></html>");
						writer.println("Holder Number: " + holderNo);
						writer.println("<html><br></br></html>");
						writer.println("Spectrometer: " + spectrometer);
						writer.println("<html><br></br></html>");
						writer.println("Research Group: " + resGroup);
						writer.println("<html><br></br></html>");
						writer.println("Solvent: " + solvent);
						writer.println("<html><br></br></html>");
						writer.println("Experiment Name: " + expName);
						writer.println("<html><br></br></html>");
						writer.println("-----------------------");
						writer.println("<html><br></br></html>");

						// Reading next line
						contents = reader.readLine();
					}

					writer.println("Number of samples run with spectrometer Alec: " + sampleSpec);
					writer.println("<html><br></br></html>");
					writer.println("Percentage of samples that were tested using experiment proton.a.and: "
							+ (sampleExp * 100.0 / i));
					writer.println("<html><br></br></html>");
					writer.println("Shortest record: " + shortRec);
					writer.println("<html><br></br></html>");
					writer.println("Longest record: " + longRec);
					writer.println("<html><br></br></html>");

					writer.println("<html><center>Extensions</center><br></html>");
					writer.println("<html><br></br></html>");
					writer.println("Number of samples: " + i);
					writer.println("<html><br></br></html>");
					writer.println("Window with Spectrometer, Solvent and Research Group Usage");

					writer.close();

					// Launch Chart Window
					try {
						launch(args);
					} catch (Exception e) {
						System.err.println("Couldn't launch chart!");
					}
				} catch (FileIsCorrupt e) {
					System.err.println(e.getMessage());
				}

				catch (NullPointerException e) {
					System.err.println("File is empty:" + e.getMessage());
				}
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't create file");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't find file: " + e.getMessage());
		}
	}

	public static String cutContentsOnce(String str) {
		str = str.substring(str.indexOf(",") + 1);
		return str;
	}

	public static String cutContentsTwice(String str) {
		str = str.substring(str.indexOf(",") + 1);
		str = str.substring(str.indexOf(",") + 1);
		return str;
	}

	public static String cutContentsThrice(String str) {
		str = cutContentsOnce(str);
		str = cutContentsTwice(str);
		return str;
	}

	public static String cutContentsFiveTimes(String str) {
		str = cutContentsThrice(str);
		str = cutContentsTwice(str);
		return str;
	}

	public static String cutContentsSixTimes(String str) {
		str = cutContentsThrice(str);
		str = cutContentsThrice(str);
		return str;
	}

	public static String cutContentsEightTimes(String str) {
		str = cutContentsTwice(str);
		str = cutContentsSixTimes(str);
		return str;
	}

	public static String cutContentsNineTimes(String str) {
		str = cutContentsThrice(str);
		str = cutContentsSixTimes(str);
		return str;
	}

	public static String cutContentsTenTimes(String str) {
		str = cutContentsEightTimes(str);
		str = cutContentsTwice(str);
		return str;
	}

	public static String cutContentsTwelveTimes(String str) {
		str = cutContentsSixTimes(str);
		str = cutContentsSixTimes(str);
		return str;
	}

	// Extension: Creation of chart for Spectrometer, Solvent and Research Group
	// Usage
	@Override
	public void start(Stage stage) {
		stage.setTitle("CS1003");
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
		bc.setTitle("Spectrometer, Solvent and Research Group Usage");
		yAxis.setLabel("Number of samples run");

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Spectrometer Usage");
		for (int g = 0; g < specsNames.size(); g++) {
			series1.getData().add(new XYChart.Data(specsNames.get(g), specsNo.get(g)));
		}

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Solvent Usage");
		for (int g = 0; g < solventNames.size(); g++) {
			series2.getData().add(new XYChart.Data(solventNames.get(g), solventNo.get(g)));
		}

		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Research Group Usage");
		for (int g = 0; g < researchNames.size(); g++) {
			series3.getData().add(new XYChart.Data(researchNames.get(g), researchNo.get(g)));
		}

		Scene scene = new Scene(bc, 3000, 1500);
		bc.getData().addAll(series1, series2, series3);
		stage.setScene(scene);
		stage.show();
	}

	public static void researchUsage(String resGroup) {
		// Extension: Checking for researchNames usage
		for (int j = 0; j < researchNames.size(); j++) {
			if (researchNames.get(j).equals(resGroup) && okResearch == true) {
				researchNo.set(j, researchNo.get(j) + 1);
				okResearch = false;
			}
		}

		if (okResearch) {
			researchNo.add(1);
			researchNames.add(resGroup);
		}
	}

	// Extension: Checking for solvent usage
	public static void solventUsage(String solvent) {
		for (int j = 0; j < solventNames.size(); j++) {
			if (solventNames.get(j).equals(solvent) && okSolvent == true) {
				solventNo.set(j, solventNo.get(j) + 1);
				okSolvent = false;
			}
		}

		if (okSolvent) {
			solventNo.add(1);
			solventNames.add(solvent);
		}
	}

	// Extension: Checking for spectrometer usage
	public static void spectrometerUsage(String spectrometer) {
		for (int j = 0; j < specsNames.size(); j++) {
			if (specsNames.get(j).equals(spectrometer) && okSpec == true) {
				specsNo.set(j, specsNo.get(j) + 1);
				okSpec = false;
			}
		}

		if (okSpec) {
			specsNo.add(1);
			specsNames.add(spectrometer);
		}
	}
}

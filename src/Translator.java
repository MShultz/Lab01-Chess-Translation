import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
	OutputFormatter format;
	DirectiveFinder finder;
	boolean movementBegun = false;
	BufferedReader file = null;
	BufferedWriter results = null;

	public Translator(String fileName) {
		initializeReader(fileName);
		initializeWriter();
		format = new OutputFormatter();
		finder = new DirectiveFinder();
	}

	public void translate() {
		String currentLine = getCurrentLine();
		if (finder.containsComment(currentLine)) {
			currentLine = finder.removeComment(currentLine);
		}
		if (finder.isPlacement(currentLine)) {
			processPlacement(currentLine);
		}
		else if(finder.isMovement(currentLine)){
			
		}

	}

	private void initializeReader(String fileName) {
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(fileName);
			file = new BufferedReader(new InputStreamReader(inputStream));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void initializeWriter() {
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream("TranslationResults" + timeStamp + ".txt");
			results = new BufferedWriter(new OutputStreamWriter(outputStream));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String getCurrentLine() {
		String currentLine = null;
		try {
			currentLine = file.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return currentLine;
	}

	private void writeToFile(String log) {
		try {
			results.write(log);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processPlacement(String currentLine) {
		if (movementBegun) {
			writeToFile("Warning: Skipping [" + currentLine + "]. Movement has begun.");
		} else {
			ArrayList<String> placements = finder.getPlacementDirective(currentLine);
			String placement1 = "Process: Adding [" + placements.get(0) + "] "
					+ format.formatPlacement(placements.get(0));
			writeToFile(placement1);
			if (placements.get(1) != null) {
				writeToFile(
						"Warning: Skipping [ " + placements.get(1) + "]. There can only be one placement per line.");
			}
		}
	}
	
	private void processMovement(String currentLine){
		if (!movementBegun){
			movementBegun = false;
		}
		ArrayList<String> movements = finder.getMovementDirectives(currentLine);
		
	}
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Translator {
	File resultFile;
	OutputFormatter format;
	DirectiveFinder finder;
	boolean movementBegun = false;
	BufferedReader file = null;
	BufferedWriter results = null;
	FileWriter innerWriter = null;

	public Translator(String fileName) {
		initializeReader(fileName);
		createFile();
		initializeWriter();
		writeToFile("Process: Sucessfully opened file [" + fileName + "]");
		writeToFile("Process: Files Initialized.");
		format = new OutputFormatter();
		finder = new DirectiveFinder();
	}

	public void translate() {
		try {
			while (file.ready()) {
				String currentLine = getCurrentLine().trim();
				if (finder.containsComment(currentLine)) {
					currentLine = finder.removeComment(currentLine).trim();
				}
				if (currentLine.trim().length() > 0) {
					if (finder.isPlacement(currentLine)) {
						processPlacement(currentLine);
					} else if (finder.isMovement(currentLine)) {
						processMovement(currentLine);
					} else if (finder.containsCastle(currentLine)) {
						processCastling(currentLine);
						
					} else {
						writeToFile(format.getIncorrect(currentLine));
					}
				}
			}
			shutdown();
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			innerWriter = new FileWriter(resultFile);
			results = new BufferedWriter(innerWriter);
		} catch (Exception e) {
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
			results.flush();
			results.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processPlacement(String currentLine) {
		if (movementBegun) {
			writeToFile("Warning: Skipping [" + currentLine + "]. Movement has begun.");
		} else {
			String placement = finder.getPlacementDirective(currentLine);
			String placement1 = "Placement: Adding [" + placement + "] " + format.formatPlacement(placement);
			writeToFile(placement1);
		}
	}

	private void processMovement(String currentLine) {
		if (!movementBegun) {
			movementBegun = true;
		}
		ArrayList<String> movements = finder.getMovementDirectives(currentLine);
		writeToFile(format.formatMovement(movements.get(0), true));
		writeToFile(format.formatMovement(movements.get(1), false));
	}
	private void processCastling(String currentLine){
		ArrayList<String> lineAction = finder.getLineAction(currentLine);
		if(finder.containsSingleMovement(currentLine)){	
			if(lineAction.size() == 2){
				if(finder.isCastle(lineAction.get(0))){
					writeToFile(format.formatCastle(lineAction.get(0), true));
				}else{
					writeToFile(format.formatMovement(lineAction.get(0), true));
				}
				if(finder.isCastle(lineAction.get(1))){
					writeToFile(format.formatCastle(lineAction.get(1), false));
				}else{
					writeToFile(format.formatMovement(lineAction.get(1), false));
				}	
			}
		}else{
			writeToFile(format.formatCastle(lineAction.get(0), true));
			writeToFile(format.formatCastle(lineAction.get(1), false));
		}
	}

	public void shutdown() {
		try {
			writeToFile("Process: Closing Files.");
			file.close();
			results.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void createFile() {
		String timeStamp = new SimpleDateFormat("MM-dd_HH_mm_ss").format(new Date());
		try {
			resultFile = new File("src/TranslationResults" + timeStamp + ".log");
			resultFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

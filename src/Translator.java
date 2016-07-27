import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translator {
	boolean movementBegun = false;
	BufferedReader file = null;
	BufferedWriter results = null;
	

	public Translator(String fileName) {
		initializeReader(fileName);
		initializeWriter();
		
	}

	public void translate() {

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

}

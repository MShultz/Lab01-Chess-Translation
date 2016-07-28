import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DirectiveFinder {
	Pattern placementPattern;
	Pattern commentPattern;
	Pattern movementPattern;

	public DirectiveFinder() {
		initializePatterns();
	}

	private void initializePatterns() {
		String placement = "\\s*(?<Pattern1>[KRNQBP][ld][a-h][1-8])\\s*";
		placementPattern = Pattern.compile(placement);
		String movement = "[^KRNQBPa-h]*(?<Movement1>[KRNQBP]?[a-h][1-8][\\-x][a-h][1-8][#\\+]?)\\s+(?<Movement2>[KRNQBP]?[a-h][1-8][\\-x][a-h][1-8][#\\+]?)";
		movementPattern = Pattern.compile(movement);
	}

	public boolean isPlacement(String currentLine) {
		Matcher placementMatcher = placementPattern.matcher(currentLine);
		return placementMatcher.find();
	}

	public boolean isMovement(String currentLine) {
		Matcher movementMatcher = movementPattern.matcher(currentLine);
		return movementMatcher.find();
	}

	public String getPlacementDirective(String currentLine) {
		Matcher placementMatcher = placementPattern.matcher(currentLine);
		placementMatcher.find();
		return placementMatcher.group("Pattern1");
	}

	public ArrayList<String> getMovementDirectives(String currentLine) {
		Matcher movementMatcher = movementPattern.matcher(currentLine);
		movementMatcher.find();
		ArrayList<String> movementDirectives = new ArrayList<String>();
		movementDirectives.add(movementMatcher.group("Movement1"));
		movementDirectives.add(movementMatcher.group("Movement2"));
		return movementDirectives;
	}

	public String removeComment(String currentLine) {
		return currentLine.substring(0, currentLine.indexOf('/'));
	}

	public boolean containsComment(String currentLine) {
		return (currentLine.contains("//"));
	}

	public boolean containsCastle(String currentLine) {
		return (currentLine.contains(" O-O-O ") || currentLine.contains(" O-O "));
	}
}

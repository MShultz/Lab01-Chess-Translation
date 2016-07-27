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
		String placement = "[^KRNQBP]*(?<Pattern1>[KRNQBP][LlDd][a-h][1-8])\\s*(?<Pattern2>[KRNQBP][LlDd][a-h][1-8])";
		placementPattern = Pattern.compile(placement);
		String movement = "[^KRNQBPa-h]*(?<Movement1>[KRNQBP]?[a-h][1-8][\\-x][KRNQBP]?[a-h][1-8]#?\\+?(O-O)?(O-O-O)?\\s+(?<Movement2>[KRNQBP]?[a-h][1-8][\\-x][KRNQBP]?[a-h][1-8]#?\\+?(O-O)?(O-O-O)?))";
		movementPattern = Pattern.compile(movement);
		String comment = "(/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*+/)|(//.*)";
		commentPattern = Pattern.compile(comment);
	}

	public boolean isPlacement(String currentLine) {
		Matcher placementMatcher = placementPattern.matcher(currentLine);
		return placementMatcher.find();
	}

	public boolean isMovement(String currentLine) {
		Matcher movementMatcher = movementPattern.matcher(currentLine);
		return movementMatcher.find();
	}

	public ArrayList<String> getPlacementDirective(String currentLine) {
		Matcher placementMatcher = placementPattern.matcher(currentLine);
		ArrayList<String> placementDirectives = new ArrayList<String>();
		placementDirectives.add(placementMatcher.group("Pattern1"));
		if (placementMatcher.group("Pattern2") != null) {
			placementDirectives.add(placementMatcher.group("Pattern2"));
		}
		return placementDirectives;
	}

	public ArrayList<String> getMovementDirectives(String currentLine) {
		Matcher movementMatcher = movementPattern.matcher(currentLine);
		ArrayList<String> movementDirectives = new ArrayList<String>();
		movementDirectives.add(movementMatcher.group("Pattern1"));
		movementDirectives.add(movementMatcher.group("Pattern2"));
		return movementDirectives;
	}

	public String removeComment(String currentLine) {
		Matcher commentMatcher = commentPattern.matcher(currentLine);
		return commentMatcher.replaceAll("");
	}

	public boolean containsComment(String currentLine) {
		Matcher commentMatcher = commentPattern.matcher(currentLine);
		return commentMatcher.find();
	}
}

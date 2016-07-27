
public class OutputFormatter {
	public String formatPlacement(String placement) {
		String formatted = getColor(placement.charAt(1)) + " " + getPiece(placement.charAt(0)) + "was placed at "
				+ placement.substring(2, 4);
		return formatted;
	}

	public String formatMovement(String movement, boolean isWhite){
		String piece = getPiece(movement.charAt(0));
		String formatted = (isWhite? "White" : "Black") + " moves " + piece + "at ";
		if(piece.equals("Pawn")){
			formatted += movement.substring(0,2) + " to " + movement.substring(3, 5);
			if(movement.charAt(2) == 'x'){
				formatted += ", Caputuring a piece."
			}
		}else{
			formatted += movement.substring(1,3) + " to " + movement.substring(4, 6);
		}
	}

	private String getPiece(char piece) {
		String pieceString;
		switch (piece) {
		case 'K':
			pieceString = "King";
			break;
		case 'Q':
			pieceString = "Queen";
			break;
		case 'R':
			pieceString = "Rook";
			break;
		case 'B':
			pieceString = "Bishop";
			break;
		case 'N':
			pieceString = "Knight";
			break;
		case 'P':
		default:
			pieceString = "Pawn";
			break;
		}
		return pieceString;
	}

	private String getColor(char color) {
		return (color == 'l' ? "Light" : "Dark");
	}

	private String getEnding(char end) {
		String ending = "";
		if (end == '+' || end == '#') {
			ending = (end == '+'? "Check!": "Checkmate!");
		}
		return ending;
	}
}

}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class MastermindController {

    //Mastermind has 4 pegs, eahc of which can contain one of six different colours

    public static void main(String[] args) {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        Colours[] target = new Colours[4];

        target = generateRandomBoard();
        System.out.println("Lets play mastermind");
        System.out.println("Enter four colours separated by a comma to make a guess");
        System.out.println("The colours are: red, blue, green, yellow, black, white");
        String guessString = "";
        EvaluationResult result = new EvaluationResult();

        while (result.getCorrectColoursInTheRightPlace() < 4) {
            try {
                guessString = in.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Colours[] guess = guessToColourArray(guessString.split(","));
            if (guess == null) {
                System.out.println("Invalid guess, try again");
            } else {
                result = evaluate(target, guess);

                System.out.println(result.getCorrectColoursInTheRightPlace() + " correct colours in the right place");
                System.out.println(result.getCorrectColoursInTheWrongPlace() + " correct colours in the wrong place");
            }
        }
        System.out.println("Hey you did it well done!");
    }

    private static Colours[] guessToColourArray(String[] guess) {
        if (guess == null || guess.length != 4) {
            return null;
        }

        Colours[] colourArray = new Colours[4];

        for (int i = 0; i < guess.length; i++) {
            colourArray[i] = stringToColour(guess[i].trim());
            if (colourArray[i] == null) {
                return null;
            }
        }
        return colourArray;
    }

    private static Colours stringToColour(String guess) {
        if ("blue".equalsIgnoreCase(guess)) {
            return Colours.BLUE;
        } else if ("red".equalsIgnoreCase(guess)) {
            return Colours.RED;
        } else if ("yellow".equalsIgnoreCase(guess)) {
            return Colours.YELLOW;
        } else if ("green".equalsIgnoreCase(guess)) {
            return Colours.GREEN;
        } else if ("white".equalsIgnoreCase(guess)) {
            return Colours.WHITE;
        } else if ("black".equalsIgnoreCase(guess)) {
            return Colours.BLACK;
        } else {
            return null;
        }
    }

    private static Colours[] generateRandomBoard() {
        Colours[] board = new Colours[4];

        for (int i = 0; i < board.length; i++) {
            Random rand = new Random();
            board[i] = colourFromNumber(rand.nextInt(6));
        }

        return board;
    }

    private static Colours colourFromNumber(int number) {
        switch (number) {
            case 0:
                return Colours.BLUE;
            case 1:
                return Colours.RED;
            case 2:
                return Colours.GREEN;
            case 3:
                return Colours.YELLOW;
            case 4:
                return Colours.BLACK;
            case 5:
                return Colours.WHITE;

        }
        return null;
    }

    private static EvaluationResult evaluate(Colours[] target, Colours[] guess) {
        EvaluationResult result = new EvaluationResult();
        boolean[] guessedColours = new boolean[4];
        for (int i = 0; i < target.length; i++) {
            if (target[i].equals(guess[i])) {
                result.incrementColoursInTheRightPlace();
                guessedColours[i] = true;
            }
        }

        for (int i = 0; i < guess.length; i++) {
            for (int j = 0; j < target.length; j++) {
                if (!guessedColours[j]) {
                    if (guess[i].equals(target[j])) {
                        result.incrementColoursInTheWrongPlace();
                        guessedColours[j] = true;
                        break;
                    }
                }
            }
        }
        return result;
    }
}

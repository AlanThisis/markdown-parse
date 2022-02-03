// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        int pastCloseParen = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            
            // System.out.println("Value of current index before loop: " + currentIndex);
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            int openParen = markdown.indexOf("(", nextCloseBracket);
            int closeParen = markdown.indexOf(")", openParen);

            if(pastCloseParen == closeParen || nextOpenBracket == -1 || nextCloseBracket == -1 || openParen == -1 || closeParen == -1) {
                break;
            }
            pastCloseParen = closeParen;
            
            // System.out.println("Index of next open bracket: " + nextOpenBracket);
            // System.out.println("Index of next open bracket - 1: " + (nextOpenBracket - 1));
            if(nextOpenBracket != 0) {
                if(!markdown.substring(nextOpenBracket-1, nextOpenBracket).equals("!")){
                    toReturn.add(markdown.substring(openParen + 1, closeParen));
                }
                currentIndex = closeParen + 1;
            } else {
                toReturn.add(markdown.substring(openParen + 1, closeParen));
                currentIndex = closeParen + 1;
            }
            // System.out.println("Value of current index after loop: " + currentIndex);
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}
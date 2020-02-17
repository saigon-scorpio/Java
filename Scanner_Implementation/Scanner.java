import java.util.HashMap;
import java.util.Map;
import java.io.*;

class Scanner {
    private int count = 0;
    private int track = 0;
    private Map<Integer,String> dictionary = new HashMap<Integer,String>();
    private Core[] placeholder;
    
    
    // Initialize the scanner
    Scanner(String filename) throws IOException {
        takingInput(filename);
        // Now assign Core after all string is added
        placeholder = new Core[count+1]; // Since we start with 0;
        assignCore(placeholder);
    }
    // Advance to the next token
    public Core nextToken() {
        // Returning a value for demonstration purposes
        track++;
        return placeholder[track];
    }
    
    // Return the current token
    public Core currentToken() {
        // Returning a value for demonstration purposes
        return placeholder[track];
    }
    
    public String getID() {
        // Returning a value for demonstration purposes
        return dictionary.get(track);
    }
    
    public int getCONST() {
        // Returning a value for demonstration purposes
        return Integer.parseInt(dictionary.get(track));
    }
    
    // MY HELPER FUNCTION
    
    
    private boolean checkCha(int i) {
        return (i >= 'a' && i <= 'z')||(i>='A' && i <= 'Z');
    }
    private boolean checkDig(int i) {
        return (i>='0' && i <= '9');
    }
    private boolean checkSign(int i) {
        return ";=*-+(),!:<".contains(""+(char)i);
    }
    private boolean checkBlank(int i){
        return "\n\t ".contains(""+(char)i);
    }
    private boolean checkStringDigit(String check) {
        for (int i = 0; i < check.length(); i++) {
            if(check.charAt(i) < '0' || check.charAt(i) > '9')
                return false;
        }
        return true;
    }
    private int digitValue(String value) {
        int result = 0;
        for (int i = 0; i < value.length(); i++) {
            result = (result * 10) + (value.charAt(i) - '0');
            if (result > 1023) {
                return -1;
            }
        }
        return result;
    }
    private void takingInput(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        boolean pastCha = false;
        boolean pastDig = false;
        boolean pastSig = false;
        boolean newToken = true;
        
        int i;
        while ((i = fr.read()) != -1){
            /*
             if ((i >= 'a' && i <= 'z')||(i>='A' && i <= 'Z')) {
             dictionary.put(track, dictionary.get(track)+(char)i);
             }
             */
            if(newToken) {
                if (checkCha(i)||checkDig(i)||checkSign(i)||checkBlank(i)) {
                    dictionary.put(count,""+(char)i);
                    // These characters stand alone on its own
                    if (";=*-+(),!".contains(""+(char)i)||checkBlank(i)) {
                        count++;
                    }
                    else {
                        // set up for adding up.
                        newToken = false;
                        if (checkCha(i))
                            pastCha = true;
                        else if (checkDig(i))
                            pastDig = true;
                        else
                            pastSig = true;
                    }
                }
                else {
                    dictionary.put(count,"ERROR!");
                    break;
                    
                }
            }
            else {
                if (checkCha(i)) {
                    if(pastCha) {
                        dictionary.put(count, dictionary.get(count)+(char)i);
                    }
                    else if (pastDig) {
                        count++;
                        dictionary.put(count,""+(char)i);
                    }
                    else if (pastSig) { //Since it would be : or <
                        if (dictionary.get(count).equals(":"))
                            dictionary.put(count,""+(char)i);
                        else { // case <
                            count++;
                            dictionary.put(count,""+(char)i);
                        }
                    }
                    pastCha = true;
                    pastDig = false;
                    pastSig = false;
                }
                else if (checkDig(i)) {
                    if (pastCha) {
                        dictionary.put(count, dictionary.get(count)+(char)i);
                    }
                    else if (pastDig) {
                        dictionary.put(count, dictionary.get(count)+(char)i);
                    }
                    else if (pastSig) {
                        if (dictionary.get(count).equals(":"))
                            dictionary.put(count,""+(char)i);
                        else { // case <
                            count++;
                            dictionary.put(count,""+(char)i);
                        }
                    }
                    pastCha = false;
                    pastDig = true;
                    pastSig = false;
                }
                else if (checkSign(i)) {
                    if (pastCha) {
                        count++;
                        if (";=*-+(),!".contains(""+(char)i)) {
                            dictionary.put(count,""+(char)i);
                            newToken = true;
                            pastCha = false;
                            count++;
                            continue; //Get to the new loop
                        }
                        else {
                            dictionary.put(count,""+(char)i);
                        }
                    }
                    else if (pastDig) {
                        count++;
                        if (";=*-+(),!".contains(""+(char)i)) {
                            dictionary.put(count,""+(char)i);
                            newToken = true;
                            pastDig = false;
                            count++;
                            continue; //Get to the new loop
                        }
                        else {
                            dictionary.put(count,""+(char)i);
                        }
                    }
                    else if (pastSig) {
                        if (dictionary.get(count).equals(":")) {
                            if(":<".contains(""+(char)i)) {
                                dictionary.put(count, ""+(char)i);
                            }
                            else {
                                if ((char)i != '=')
                                    dictionary.put(count,""+(char)i);
                                else {
                                    dictionary.put(count, dictionary.get(count)+(char)i);
                                }
                                newToken = true;
                                pastSig = false;
                                count++;
                                continue;
                            }
                        }
                        else { //case <
                            if(":<".contains(""+(char)i)) {
                                count++;
                                dictionary.put(count, ""+(char)i);
                            }
                            else {
                                if ((char)i != '='){
                                    count++;
                                    dictionary.put(count,""+(char)i);
                                }
                                else {
                                    dictionary.put(count, dictionary.get(count)+(char)i);
                                }
                                newToken = true;
                                pastSig = false;
                                count++;
                                continue;
                            }
                        }
                    }
                    pastCha = false;
                    pastDig = false;
                    pastSig = true;
                }
                else if(checkBlank(i)){
                    newToken = true;
                    pastCha = false;
                    pastDig = false;
                    pastSig = false;
                    if (!dictionary.get(count).equals(":")) { //Case gone wrong
                        count++;
                    }
                    dictionary.put(count,""+(char)i);
                }
                else {
                    if (!dictionary.get(count).equals(":")) { //Case gone wrong
                        count++;
                    }
                    dictionary.put(count,"ERROR!");
                    break;
                }
            }
        }
        if (dictionary.containsKey(count) && !dictionary.get(count).equals(":")){
            count++;
        }
        // ADD EOS at the end
        dictionary.put(count, "EOS");
    }
    
    private void assignCore (Core[] placeholder) {
        for (int j = 0; j <= count; j++) {
            Core t;
            String current = dictionary.get(j);
            if ("\n\t ".contains(current)){
                current = "#BLANK";
            }
            // 2 error cases
            if(checkStringDigit(current)) {
                if (digitValue(current) == -1) {
                    current = "##ERROR";
                    dictionary.put(j, "Invalid Integer Value!");
                }
                else {
                    current = "#CONSTANT";
                }
            }
            if (current.equals("ERROR!")) {
                current = "##ERROR";
                dictionary.put(j, "Invalid Syntax!");
            }
            if (j < count && current.equals("EOS")){
                current = "##ERROR";
                dictionary.put(j,"End of documents early!");
            }
            switch (current) {
                case "EOS" : t = Core.EOS; break;
                    
                case "program": t = Core.PROGRAM; break;
                case "begin": t = Core.BEGIN; break;
                case "end": t = Core.END; break;
                    
                case "int": t = Core.INT; break;
                case "endfunc": t = Core.ENDFUNC; break;
                    
                case "if": t = Core.IF; break;
                case "then": t = Core.THEN; break;
                case "else": t = Core.ELSE; break;
                case "while": t = Core.WHILE; break;
                case "endwhile": t = Core.ENDWHILE; break;
                case "endif": t = Core.ENDIF; break;
                    
                case ";": t = Core.SEMICOLON; break;
                case "(": t = Core.LPAREN; break;
                case ")": t = Core.RPAREN; break;
                case ",": t = Core.COMMA; break;
                case ":=": t = Core.ASSIGN; break;
                case "!": t = Core.NEGATION; break;
                case "or": t = Core.OR; break;
                case "=": t = Core.EQUAL; break;
                case "<": t = Core.LESS; break;
                case "<=": t = Core.LESSEQUAL; break;
                case "+": t = Core.ADD; break;
                case "-": t = Core.SUB; break;
                case "*": t = Core.MULT; break;
                    
                case "input": t = Core.INPUT; break;
                case "output": t = Core.OUTPUT; break;
                
                case "#CONSTANT" : t = Core.CONST; break;
                    
                case "#BLANK" : t = Core.BLANK; break;
                    
                case "##ERROR" : t = Core.ERROR; break;
                    
                default : t = Core.ID;
            }
            placeholder[j] = t;
            
            if (current.equals("##ERROR")){
                placeholder[j+1] = Core.EOS;
                break;
            }
        }
    }
}

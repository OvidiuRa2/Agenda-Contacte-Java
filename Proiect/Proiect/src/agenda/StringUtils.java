package agenda;

/* @author radoi ovidiu*/
public class StringUtils {

    public static boolean onlyLetters(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetter(s.charAt(i))) {
                return false;
            }

        }
        return true;
    }

    
    public static boolean onlyDigits(String s){
    for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }

        }
        return true;
    
    }
    
    public static int zilePerLuna(int an, int luna) {
        int zimax = 0;
        switch (luna) {
            case 1:
                zimax = 31;
                break;
            case 2:
                if (an % 4 == 0) {
                    zimax = 29;
                    break;
                } else {
                    zimax = 28;
                    break;
                }
            case 3:
                zimax = 31;
                break;
            case 4:
                zimax = 30;
                break;
            case 5:
                zimax = 31;
                break;
            case 6:
                zimax = 30;
                break;
            case 7:
                zimax = 31;
                break;
            case 8:
                zimax = 31;
                break;
            case 9:
                zimax = 30;
                break;
            case 10:
                zimax = 31;
                break;
            case 11:
                zimax = 30;
                break;
            case 12:
                zimax = 31;
                break;

        }
        return zimax;
    }
}

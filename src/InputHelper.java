import java.util.Scanner;
public class InputHelper {
  private static Scanner input = new Scanner(System.in);
  public static String getStringInput(){
     return input.nextLine();
  }

  public static int getIntInput(){
    // Loop until valid number to return
    while (true) {
            String str = input.nextLine();
            int res = 0;
            try {
                res = Integer.parseInt(str);
                return res;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please try again:");
            }
        }
  }
}

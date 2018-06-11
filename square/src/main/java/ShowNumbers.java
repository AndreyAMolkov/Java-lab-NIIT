 public class ShowNumbers {
     public static void main(String[] args) {
         String line = args[0];
         String[] result;
         BigNumbers BigNumbers = new BigNumbers(line);
         result = BigNumbers.create();
         for (String i : result) {
             System.out.println(i);
         }
     }
 }
     class BigNumbers {
        String[] result = {"","","","","","",""};
        String line;

        BigNumbers(String line) {
            this.line = line;
        }

        public  String[] create() {
            char[] buf = line.toCharArray();
            int length = buf.length;
            String[] number=result;
            for (int i = 0; i < length; i++) {
                number = choice(buf[i]);
                    for(int j=0;j<7;j++)
                        result[j]+=number[j];
            }
            return result;
        }

        String[] choice(char symbol) {

            switch (symbol) {
                case '1':
                    return one();
                case '2':
                    return two();
                case '3':
                    return three();
                case '4':
                    return four();
                case '5':
                    return five();
                case '6':
                    return six();
                case '7':
                    return seven();
                case '8':
                    return eight();
                case '9':
                    return nine();
                case '0':
                    return zero();

            }
            return result;
        }

        String[] one() {
            String[] one = {
                    "  *  ",
                    " **  ",
                    "  *  ",
                    "  *  ",
                    "  *  ",
                    "  *  ",
                    " *** "
            };
            return one;
        }

        String[] two() {
            String[] two = new String[]{
                    "  *** ",
                    " *   *",
                    " *  * ",
                    "   *  ",
                    "  *   ",
                    " *    ",
                    " *****"
            };
            return two;
        }

        String[] three() {
            String[] three;
            three = new String[]{
                    "  *** ",
                    " *   *",
                    "     *",
                    "   ** ",
                    "     *",
                    " *   *",
                    "  *** "
            };
            return three;
        }
        String[] four() {
            String[] four = {
                    "    *  ",
                    "   **  ",
                    "  * *  ",
                    " *  *  ",
                    " ******",
                    "   *   ",
                    "   *   "
            };
            return four;
        } String[] five() {
            String[] five = {
                    " *****",
                    " *    ",
                    " *    ",
                    " **** ",
                    "     *",
                    "     *",
                    " **** "
            };
            return five;
        } String[] six() {
            String[] six = {
                    " *    ",
                    " *    ",
                    " *    ",
                    " **** ",
                    " *   *",
                    " *   *",
                    " **** "
            };
            return six;
        }
        String[] seven() {
            String[] seven = {
                    " *****",
                    "     *",
                    "    * ",
                    "   *  ",
                    "  *   ",
                    " *    ",
                    " *    "
            };
            return seven;
        } String[] eight() {
            String[] eight = {
                    "  *** ",
                    " *   *",
                    " *   *",
                    "  *** ",
                    " *   *",
                    " *   *",
                    "  *** "
            };
            return eight;
        } String[] nine() {
            String[] nine = {
                    "  ****",
                    " *   *",
                    " *   *",
                    "  ****",
                    "     *",
                    "     *",
                    "     *"
            };
            return nine;
        } String[] zero() {
            String[] zero = {
                    "   ***  ",
                    "  *   * ",
                    " *     *",
                    " *     *",
                    " *     *",
                    "  *   * ",
                    "   ***  "
            };
            return zero;
        }


    }



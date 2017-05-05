import java.io.*;

/**
 * Created by abhishek on 5/5/17.
 */
public class MainKmean {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws java.io.IOException {
        System.out.println("Enter the file name");
        String fileName = br.readLine();
        File f = new File("/home/abhishek/Desktop/Projects/DataScience", fileName);
        readTable(f);
    }


    public static void readTable(File fr) {
        try {
            FileReader r = new FileReader(fr);
            BufferedReader br1 = new BufferedReader(r);
            String line = br1.readLine();
            System.out.println("enter no of variable");
            int validVariable = Integer.parseInt(br.readLine());
       label1: while (line != null) {
                if(line.charAt(0)=='@'||line.charAt(0)=='%')
                {
                    line = br1.readLine();
                    continue label1;
                }
                int count = 0;
                int j = 0;
                for (int i = 0; (i < validVariable) && (j < line.length()); i++) {
                    String var = "";
                    for (; (j < line.length()) &&
                            (line.charAt(j) != ','); j++) {
                        var = var + line.charAt(j);
                        //System.out.print(var);
                    }

                    double val = Double.parseDouble(var);
                    System.out.print(val);
                    System.out.print('\t');
                    j++;
                }
                System.out.println("");
                line = br1.readLine();

           }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}


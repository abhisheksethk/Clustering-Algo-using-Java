import java.io.*;

/**
 * Created by abhishek on 5/5/17.
 */
class Point
{
    int clusterVector;
    int varSize;
    double[] variable;
    public Point(double[] v)
    {
        variable=new double[v.length] ;
        for (int i = 0; i < v.length; i++)
        {
           variable[i] =v[i];
        }
       varSize=v.length;
    }

}
public class MainKmean {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws java.io.IOException {
        System.out.println("Enter the file name");
        String fileName = br.readLine();
        File f = new File("/home/abhishek/Desktop/Projects/DataScience", fileName);
        System.out.println("enter the size of dataset");
        int size=Integer.parseInt(br.readLine());
        Point[] p=getPointArray(size);
        readTable(f,p);
        printTable(p);
    }
    public static Point[] getPointArray(int number)
    {
        Point[] p=new Point[number];
        return p;
    }
    public static void printTable(Point[] p)
    {
        for (Point i:p)
        {

            for (int j = 0; j <i.varSize ; j++)
            {
                System.out.print(i.variable[j]);
                System.out.print('\t');
            }
            System.out.println("");
        }
    }

    public static void readTable(File fr,Point[] p) {
        try {
            FileReader r = new FileReader(fr);
            BufferedReader br1 = new BufferedReader(r);
            String line = br1.readLine();
            System.out.println("enter no of variable");
            int validVariable = Integer.parseInt(br.readLine());
            int count = 0;
            label1: while (line != null) {
                if(line.charAt(0)=='@'||line.charAt(0)=='%')
                {
                    line = br1.readLine();
                    continue label1;
                }

                int j = 0;
                double[] d=new double[validVariable];
                for (int i = 0; (i < validVariable) && (j < line.length()); i++) {
                    String var = "";
                    for (; (j < line.length()) &&
                            (line.charAt(j) != ','); j++) {
                        var = var + line.charAt(j);
                        //System.out.print(var);
                    }

                    double val = Double.parseDouble(var);
                    d[i]=val;
                    //System.out.print(val);
                    //System.out.print('\t');
                    j++;
                }
                p[count]=new Point(d);
                count++;

                System.out.println("");
                line = br1.readLine();

            }
            System.out.println(count);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}


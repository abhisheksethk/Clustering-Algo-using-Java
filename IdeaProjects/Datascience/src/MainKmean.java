import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by abhishek on 5/5/17.
 */
class Kmean
{
    private int[] centPoint;
    private Point[] p1;
    private int size;
    private int totalClusters;
    private LinkedList<Point>[] cluster;
    private Map<Integer,Integer> kmap=new HashMap<Integer,Integer>();
    public void clusterNo(int k)
    {
        cluster=new LinkedList[k];
    }
    public void centroids(Point[] p,int k,int max)
    {
        final int[] ints = new Random().ints(0, max).distinct().limit(k).toArray();
        centPoint=ints;
        p1=p;
        size=max;
        totalClusters=k;
    }
    public void printCentroids()
    {    int no=-1;
        for (int i:centPoint )
        {
            System.out.print("centroid-No:-"+" "+"("+(++no)+")"+" ");
            p1[i].printPoint();
        }
    }
    public void createCluster()
    {

        int k=0;

        for (int i:centPoint)
        {
            kmap.put(i,k);
            k++;
        }

        for (int i = 0; i < totalClusters; i++)
        {
             cluster[i]=new LinkedList<Point>();
        }
     label2:   for (int j = 0; j < size; j++)
        {   int flag=0;
            for (int i:centPoint)
            {
                if (j==i)
                {
                    flag=1;
                    break;
                }
            }
            if (flag==1)
                continue label2;

            Map<Integer,Double> dist=new HashMap<Integer,Double>();
            for (int i:centPoint)
            {
                dist.put(i,eculidDistance(p1[i],p1[j]));
            }
            int key=0;
            Double min = Collections.min(dist.values());
            for (int i:centPoint)
            {
                if (dist.get(i)==min)
                {
                    key=i;
                    break ;
                }
            }
            //System.out.println(key);
            //System.out.println(kmap);
            int clusterNo= kmap.get(key);
            cluster[clusterNo].add(p1[j]);
            p1[j].clusterVector=clusterNo;

          //System.out.println(dist);//distance of all cluster centroid from a point
        }
     }
    public void printClusters()
    {
        for (int i = 0; i <totalClusters ; i++)
        {
            System.out.print("Cluster No:- "+i+"  ");
            for (Point j:cluster[i])
            {
                System.out.print(j.id+" ");
            }
           System.out.println("");
        }
    }
    public double eculidDistance(Point p1,Point p2)
    {
        double temp=0.0;
        for (int i = 0; i < p1.varSize; i++)
        {
            temp +=Math.pow((p1.variable[i]-p2.variable[i]),2);
        }
        double dist=Math.sqrt(temp);
        return dist;
    }
}
class Point
{
    int clusterVector;
    int varSize;
    double[] variable;
    int id;
    static int count=-1;
    public Point(double[] v)
    {
        variable=new double[v.length] ;
        for (int i = 0; i < v.length; i++)
        {
           variable[i] =v[i];
        }
       varSize=v.length;
        id= (++count);
    }
    void printPoint()
    {
        for (double d:variable)
        {
            System.out.print(d);
            System.out.print(" ");
        }
        System.out.println("");
    }

}
public class MainKmean {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws java.io.IOException {
        System.out.println("Enter the file name");
        String fileName = br.readLine();
        File f = new File("/home/abhishek/Desktop/Projects/DataScience", fileName);
        System.out.println("enter the length of dataset");
        int size=Integer.parseInt(br.readLine());
        Point[] p=getPointArray(size);
        readTable(f,p);
        printTable(p);
        System.out.println("Enter no of cluster");
        int k=Integer.parseInt(br.readLine());
        Kmean obj=new Kmean();
        obj.centroids(p,k,size);
        obj.printCentroids();
        obj.clusterNo(k);
        obj.createCluster();
        obj.printClusters();
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
                if((line.charAt(0)=='@'||line.charAt(0)=='%'))
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


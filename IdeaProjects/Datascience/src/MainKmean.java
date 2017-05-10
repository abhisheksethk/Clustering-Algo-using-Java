import java.io.*;
import java.util.*;

/**
 * Created by abhishek on 5/5/17.
 */
class DBIndex extends  DunneIndex//need some improvement regarding interface
{
    public void db(int k)
    {
        double sum=0.0;
        for (int i=0;i<k;i++)
        {
            LinkedList<Double> res=new LinkedList<Double>();
            for (int j = 0; j < k; j++)
            {
                if (i!=j)
                {
                    double temp1=diam(cluster[i],i)+diam(cluster[j],j);
                    double temp2=diss(cluster[i],cluster[j]);
                    double temp3=temp1/temp2;
                    res.add(temp3);
                }
            }
           sum +=Collections.max(res);
        }
        System.out.println("DB Index: "+sum/k);

    }
    public double diam(LinkedList<Point> cluster,int n )
    {
        double sum=0.0;
        int key=0;
        for (int i:centPoint)
        {
            if (kmap.get(i)==n)
            {
                key=i;
                break;
            }
        }
        for (Point p:cluster)
        {
            sum +=eculidDistance(p, p1[key]);
        }
        return sum/cluster.size();
    }
}
class DunneIndex extends Kmean
{
    public void dune(int k)
    {
        double diameter=diam(k);
        //System.out.println(diameter);
        LinkedList<Double> res1=new LinkedList<Double>();
        for (int i = 0; i < k; i++)
        {
            LinkedList<Double> res=new LinkedList<Double>();

            for (int j = 1; j <k ; j++)
            {   if (i!=j)
                {
                    double temp=diss(cluster[i],cluster[j]);
                    double temp2=temp/diameter;
                    res.add(temp2);
                }
            }
           res1.add(Collections.min(res)) ;
        }
        System.out.print("Dunne Index "+" : "+ Collections.min(res1));
        System.out.println("");
    }
    public double diss(LinkedList<Point> ci,LinkedList<Point> cj)
    {
        LinkedList<Double> dist=new LinkedList<>();
        for (Point i :ci)
        {
            for (Point j:cj)
            {
                dist.add(eculidDistance(i,j));
            }
        }
        Double min = Collections.min(dist);
        return min;
    }
    public double diam(int k)
    {
        LinkedList<Double> ClusDiam=new LinkedList<Double>();
        for (int i = 0; i < k; i++)
        {
            LinkedList<Double> sinClusDiam=new LinkedList<Double>();
           // Iterator itr1 = cluster[i].iterator();
           for (Point x:cluster[i])//some optimization here we can do
            {   //Object temp=itr1.next();
                //Iterator itr2 = itr1.
                //while (itr2.hasNext())
                for (Point y:cluster[i])
                {
                    sinClusDiam.add(eculidDistance(x,y));
                }
            }
            //System.out.println(sinClusDiam);
            Double maxDiamSingCluster=Collections.max(sinClusDiam);
            //System.out.println(maxDiamSingCluster);
           ClusDiam.add(maxDiamSingCluster);
        }
       // System.out.println(ClusDiam);
        //System.out.println(ClusDiam);
        return Collections.max(ClusDiam);

    }
}
class Kmean
{
    public int[] centPoint;
    public Point[] p1;
    public int size;
    public int totalClusters;
    public LinkedList<Point>[] cluster;
    public Map<Integer,Integer> kmap=new HashMap<Integer,Integer>();//adding cluster no with its centroid points
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
        DBIndex obj=new DBIndex();
        obj.centroids(p,k,size);
        obj.printCentroids();
        obj.clusterNo(k);
        obj.createCluster();
        obj.printClusters();
        obj.dune(k);
        obj.db(k);
    }
    public static Point[] getPointArray(int number)
    {
        Point[] p=new Point[number];
        return p;
    }
    public static void printTable(Point[] p)throws java.io.IOException
    {
        System.out.println("Enter the Output file name");
        String fileOutpout=br.readLine();
        File f1=new File("/home/abhishek/Desktop/Projects/DataScience",fileOutpout);
        try {
            f1.createNewFile();
            PrintWriter pw = new PrintWriter(f1);


            for (Point i : p) {

                for (int j = 0; j < i.varSize; j++) {
                    pw.print(i.variable[j]);
                    System.out.print(i.variable[j]);
                    System.out.print('\t');
                    pw.print('\t');
                }
                pw.println("");
                System.out.println("");
            }
            pw.flush();
            pw.close();

        }
        catch (Exception e)
        {

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


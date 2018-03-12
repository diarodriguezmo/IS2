import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cant,n1,n2;
        long time_start, time_end;
        cant=sc.nextInt();
        time_start = System.currentTimeMillis();
        n1=1;
        n2=1; 
        for(int i=2;i<=cant;i++){
             n2 = n1 + n2;
             n1 = n2 - n1;
        }
	time_end = System.currentTimeMillis();
	System.out.print("Tiempo:" + ( time_end - time_start )+" milésimas de segundo.");
    }
}
#include<iostream>
using namespace std;


int main()
{	
    unsigned t0, t1;
    int i, cant, n1, n2 ;
    cin>> cant;
    n1=1;
    n2=1; 
    t0=clock();
    for(i=2; i<cant +1 ; i++)
    {

        n2 = n1 + n2;
        n1 = n2 - n1;
      
    }
    t1 = clock();
    double time = (double(t1-t0)/CLOCKS_PER_SEC);
    cout << "Tiempo: " << time << endl;
    system("pause");
    
    return 0;
}
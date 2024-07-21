#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Structura pentru a reprezenta o țară
struct Tara {
    char nume[50];
    int numarVecini;
    char vecini[5][50];
    char culoare[20];
};


int main() {
    int numarTari;

    printf("Introduceti numarul de tari: ");
    scanf("%d", &numarTari);

    // Alocare dinamica pentru vectorul de tari
    struct Tara* tari = (struct Tara*)malloc(numarTari * sizeof(struct Tara));

    // Citirea datelor pentru fiecare tara
    for (int i = 0; i < numarTari; i++) 
    {
        printf("Introduceti numele tarii %d: ", i + 1);
        scanf("%s", tari[i].nume);

        printf("Introduceti numarul de vecini pentru tara %s: ", tari[i].nume);
        scanf("%d", &tari[i].numarVecini);

        for(int j=0; j<tari[i].numarVecini; j++)
        {
        printf("Introduceti numele vecinului %d pentru tara %s ",j+1,tari[i].nume);
            scanf("%s",tari[i].vecini[j]);
        }
    }

        //calculul nr total de culori
        int numarCulori=0;
        for(int i=0; i<numarTari; ++i)
        {
            numarCulori+=tari[i].numarVecini+1;
        }

      //alocare dinamica pt vector culori
      char**culori=(char **)malloc(numarCulori *sizeof(char*));
      for(int i=0;i<numarCulori;++i)
      {
      culori[i]=(char*)malloc(20*sizeof(char));
      }
           //optiuni de culori

           printf("\nIntroduceti optiunile de culori (separat prin spatiu):");

           for(int i=0;i<numarCulori;++i)
           {
            scanf("%s",culori[i]);
           }
    
    //atribuire culori pt tara si vecin

    int culoareCurenta=0;
for(int i=0;i<numarTari;++i)
{
    strcpy(tari[i].culoare , culori[culoareCurenta]);

for(int j=0;j<tari[i].numarVecini;++j)
{
    strcpy(tari[i].vecini[j],culori[++culoareCurenta]);
}
++culoareCurenta;
}

//afisare tari,vecini +culoare

for(int i=0;i<numarTari;++i)
{
printf("\nTara: %s, Culoarea: %s  \nVecini:",tari[i].nume,tari[i].culoare);
for(int j=0;j<tari[i].numarVecini;++j)
{
    printf("%s",tari[i].vecini[j]);
}
printf("\n");
}
    

    // Eliberarea memoriei alocate dinamic
    for (int i = 0; i < numarCulori; ++i) {
        free(culori[i]);
    }
    free(culori);
    free(tari);

    return 0;
}


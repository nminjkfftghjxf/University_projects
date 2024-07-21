
// Structura pentru a stoca informații despre țări
struct Tara {
    char nume[50];
    char vecini[5][50];  // Se presupune că o țară are maxim 5 vecini, sa fie o dimensiune acolo
    int numarVecini;
    char culoare[20];
};

int main() {
    int numarTari;

    printf("Introduceti numarul de tari: ");
    scanf("%d", &numarTari);

    // Alocare dinamica pentru vectorul de tari
    struct Tara *tari = (struct Tara *)malloc(numarTari * sizeof(struct Tara));

    // Introducerea datelor despre tari și vecinii lor
    for (int i = 0; i < numarTari; ++i)  //in cazul acesta mergea si i++, nu afecteaza diferit codul
    {
        printf("\nIntroduceti numele tarii %d: ", i + 1); //incep numaratoarea de la 0 si asa prima tara nu o sa fie "0" , ci "1"
        scanf("%s", tari[i].nume);

        printf("Introduceti numarul de vecini pentru tara %s: ", tari[i].nume);
        scanf("%d", &tari[i].numarVecini);

        for (int j = 0; j < tari[i].numarVecini; ++j) {
            printf("Introduceti numele vecinului %d pentru tara %s: ", j + 1, tari[i].nume);
            scanf("%s", tari[i].vecini[j]);
        }
    }

    // Calculul numărului total de culori necesare
    int numarCulori = 0;
    for (int i = 0; i < numarTari; ++i)  
    {
        numarCulori += tari[i].numarVecini + 1; // Numarul de vecini + 1 pentru propria tara
    }

    // Alocare dinamica pentru vectorul de culori
    char **culori = (char **)malloc(numarCulori * sizeof(char *));
    for (int i = 0; i < numarCulori; ++i) {
        culori[i] = (char *)malloc(20 * sizeof(char));
    }

    // Introducerea optiunilor de culori de la tastatura
    printf("\nIntroduceti optiunile de culori (separate prin spatiu): ");
    for (int i = 0; i < numarCulori; ++i) 
    {
        scanf("%s", culori[i]);
    }

    // Atribuirea culorilor pentru fiecare tara și vecin
    int culoareCurenta = 0;
    for (int i = 0; i < numarTari; ++i) 
    {
        strcpy(tari[i].culoare, culori[culoareCurenta]); //atribuie culoarea curente, tarii curente; 
                                                        //pentru conditia ca sa nnu se repete nicio culoare
        for (int j = 0; j < tari[i].numarVecini; ++j) 
        {
            strcpy(tari[i].vecini[j], culori[++culoareCurenta]);
        }        //numele vecinului curent,   culoarea curenta

        ++culoareCurenta;
    }

    // Afisarea tarii, vecinilor si culorii corespondente
    for (int i = 0; i < numarTari; ++i) 
    {
        printf("\nTara: %s, Culoare: %s\nVecini: ", tari[i].nume, tari[i].culoare);

        for (int j = 0; j < tari[i].numarVecini; ++j) {
            printf("%s ", tari[i].vecini[j]);
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




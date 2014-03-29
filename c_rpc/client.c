#include "include.h"

int main (int argc, char **argv) 
{
    //~ char *host = argv[1];
    char *host = "127.0.0.1";
    enum clnt_stat stat;
    Matrix res;
    mats2 donnees;
    int i, j;
    
    if (argc == 2)
    {
        donnees.op = argv[1][0] != '+' ? '*' : '+';
    }
    else
    {
        printf("Aucun argument spécifié: l'operation par defaut est l'addition\n");
        donnees.op = '+';
    }
    
    srand(time(NULL));

    donnees.m1.size = 2;
    donnees.m2.size = 2;
    donnees.m1.mat = malloc(sizeof(E)*donnees.m1.size*donnees.m1.size);
    donnees.m2.mat = malloc(sizeof(E)*donnees.m2.size*donnees.m2.size);
    
    for (i = 0; i < donnees.m1.size; i++)
    {
        for (j = 0; j < donnees.m1.size; j++)
        {
            setElt(&donnees.m1, mRand(1.0f,10.0f), i, j);
            setElt(&donnees.m2, mRand(1.0f,10.0f), i, j);
        }
    }

    res.size = 2;
    res.mat = malloc(sizeof(int)*res.size*res.size);

    stat = callrpc(/* host */ host,
         /* prognum */ PROGNUM,
         /* versnum */ VERSNUM,
         /* procnum */ PROCNUM,
         /* encodage argument */ (xdrproc_t) xdr_mats2,
         /* argument */ (char *)&donnees,
         /* decodage retour */ (xdrproc_t)xdr_Matrix,
         /* retour de la fonction distante */(char *)&res);

    if (stat != RPC_SUCCESS) 
    { 
        fprintf(stderr, "Echec de l'appel distant\n");
        clnt_perrno(stat);      fprintf(stderr, "\n");
    } 
    else
    {
        printf("Donnees\n");
        printm(&donnees.m1);
        printf("\n");
        printm(&donnees.m2);
        printf("\n\n");
        
        printf("Resultat:\n");
        printm(&res);
        printf("\n");
    }
    return(0);
}

#include "include.h"

Matrix* OperationMatrice(mats2 *m) 
{ 
    static Matrix res;

    if (res.mat == NULL)
    {
        res.size = m->m1.size;
        res.mat = malloc(sizeof(E)*res.size*res.size);
        fprintf(stderr, "OperationMatrice(): Matrice resultat non allouée\n");
    }
    
    fprintf(stderr, "Operation choisie : %c\n", m->op);
    
    // on fait une addition
    switch (m->op)
    {
        case '+':
            addMatricis(&res, &m->m1, &m->m2);
        break;

        case '*':
            mulMatricis(&res, &m->m1, &m->m2);
        break;
    }
    
    return &res;
}
int main (void) 
{
    bool_t stat;
    stat = registerrpc(/* prognum */ PROGNUM,
             /* versnum */ VERSNUM,
             /* procnum */ PROCNUM,
             /* pointeur sur fonction */  OperationMatrice,
             /* decodage arguments */ (xdrproc_t)xdr_mats2,
             /* encodage retour de fonction */ (xdrproc_t)xdr_Matrix);
    if (stat != 0) 
    {
        fprintf(stderr,"Echec de l'enregistrement\n");
        exit(1);
    }
    svc_run(); /* le serveur est en attente de clients eventuels */
    return(0); /* on y passe jamais ! */
}



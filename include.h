#ifndef INCRPC
#define INCRPC  

#include <rpc/types.h>
#include <rpc/xdr.h>
#include <rpc/rpc.h>
#define PROGNUM 0x20000100
#define VERSNUM 1
#define PROCNUM 1

#include <time.h>

typedef float E;

typedef struct
{
    E* mat;
    int size;
} Matrix;

typedef struct 
{ 
    Matrix m1;
    Matrix m2;
    int op;
} mats2;

E getElt(Matrix *m, int r, int c);
void setElt(Matrix *m, E e, int r, int c);
void addMatricis(Matrix *res, Matrix *m1, Matrix *m2);
void mulMatricis(Matrix *res, Matrix *m1, Matrix *m2);

void printm(Matrix *m);

bool_t xdr_Matrix(XDR *, Matrix *);
bool_t xdr_mats2(XDR *, mats2 *);

E mRand(E min, E max);

#endif

#include "include.h"

E getElt(Matrix *m, int r, int c)
{
	return m->mat[m->size*r+c];
}

void setElt(Matrix *m, E e, int r, int c)
{
	m->mat[m->size*r+c] = e;
}

void addMatricis(Matrix *res, Matrix *m1, Matrix *m2)
{
	int i, j;

	for (i = 0; i < res->size; i++)
	{
		for (j = 0; j < res->size; j++)
		{
			setElt(res, getElt(m1,i,j)+getElt(m2,i,j), i, j);
		}
	}
}

void mulMatricis(Matrix *res, Matrix *m1, Matrix *m2)
{
	int i, j, k;
	E a, b, c, r;

	for (i = 0; i < m1->size; i++)
	{
		for (j = 0; j < m1->size; j++)
		{
			c = 0;

			for (k = 0; k < m1->size; k++)
			{
				a = getElt(m1,i,k);
				b = getElt(m2,k,j);
				r = a*b;
				c += r;
			}

			setElt(res, c, i, j);
		}
	}
}

bool_t xdr_Matrix(XDR *xdrs, Matrix *m)
{    
	if (!xdr_int(xdrs, &(m->size)))
	{
        fprintf(stderr, "xdr_mat2(): xdr_int() failed\n");
		return FALSE;
	}
    
    int size = m->size*m->size;
    unsigned int maxsize = 100;
    if (!xdr_array(xdrs, (char**)&m->mat, (unsigned int*)&size,
          maxsize, sizeof (float), (xdrproc_t)xdr_float))
    {
        fprintf(stderr, "xdr_mat2(): xdr_array() failed\n");
        return FALSE;
    }
    
	return TRUE;
}

bool_t xdr_mats2(XDR *xdrs, mats2 *m)
{
	if (!xdr_Matrix(xdrs, &(m->m1)))
	{
        fprintf(stderr, "xdr_mats2(): xdr_Matrix() failed\n");
		return FALSE;
	}
    
	if (!xdr_Matrix(xdrs, &(m->m2)))
    {
        fprintf(stderr, "xdr_mats2(): xdr_Matrix() failed\n");
    	return FALSE;
	}
    
	if (!xdr_int(xdrs, &(m->op)))
	{
        fprintf(stderr, "xdr_mats2(): xdr_int() failed\n");
		return FALSE;
	}
    
	return TRUE;
}

void printm(Matrix *m)
{
    int i,j;
    
    for (i = 0; i < m->size; i++)
    {
        printf("[\t");
        for (j = 0; j < m->size; j++)
        {
            printf("%f\t", m->mat[m->size*i+j]);
        }
        printf("]\n");
    }
}

E mRand(E min, E max) 
{
   return (E)((max-min)*((E)rand()/RAND_MAX))+min;
}

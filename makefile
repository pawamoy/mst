LINUX= -lnsl
CC=gcc
CFLAGS=-Wall
#----------------------------------------------
all : serveur client 
#----------------------------------------------
serveur : serveur.o xdr_mat.o
	gcc -o serveur serveur.o xdr_mat.o ${LINUX}
#----------------------------------------------
client : client.o xdr_mat.o
	gcc -o client client.o xdr_mat.o ${LINUX}
#----------------------------------------------
clean :
	/bin/rm -f *.o serveur client *~ core* pok* *.ps

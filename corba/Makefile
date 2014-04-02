JAVA  = $(wildcard *.java)
CLASS = $(JAVA:.java=.class)
IDL   = $(wildcard *.idl)

mst: $(IDL) class
	
%.idl:
	idlj -fall -OldImplBase $@
	
class:
	javac *.java

#~ %.class: %.java
	#~ javac $< 

clean:
	/bin/rm *.class
	/bin/rm *ImplBase.java
	/bin/rm *Stub.java

	
	
	
	
	





	
# RAPPELS BANDE DE BATARD



#~ # règles de compilation
#~ ifeq ($(DEBUG), yes)
	#~ DBG = -g
#~ else
	#~ DBG =
#~ endif
#~ 
#~ CXX      = /usr/bin/g++
#~ EXEC     = drawtri
#~ CXXFLAGS = `/usr/bin/wx-config --cxxflags` $(DBG)
#~ LDFLAGS  = `/usr/bin/wx-config --libs --gl-libs` -lGL
#~ 
#~ # sources et objets
#~ SRC      = $(wildcard *.cpp)
#~ OBJ      = $(SRC:.cpp=.o)
#~ 
#~ all : $(EXEC)
#~ 
#~ # compilation main
#~ drawtri : $(OBJ)
	#~ $(CXX) -o $@ $^ $(LDFLAGS) 
#~ 
#~ # dépendances
#~ mainframe.o : mainframe.h
#~ version_dialog.o : version_dialog.h
#~ thickness_dialog.o : thickness_dialog.h
#~ color_dialog.o : color_dialog.h
#~ manage_dialog.o : manage_dialog.h
#~ 
#~ # compilation objets
#~ %.o : %.cpp
	#~ $(CXX) $(CXXFLAGS) -c $< -o $@
#~ 
#~ # nettoyage
#~ clean : 
	#~ /bin/rm -f *.o
#~ mrproper : clean
	#~ /bin/rm $(EXEC)






#~ ifeq ($(DEBUG),yes)
	#~ CFLAGS = -g
#~ else
	#~ CFLAGS =
#~ endif
#~ 
#~ CFLAGS += -std=gnu99 -pedantic -Werror -W -Wall -Wextra -Wmissing-declarations -Wmissing-prototypes -Wredundant-decls -Wshadow -Wbad-function-cast -Wcast-qual
#~ 
#~ OPATH = obj/
#~ 
#~ vpath %.c src/
#~ vpath %.h include/
#~ vpath %.o obj/
#~ vpath main_% bin/
#~ vpath main bin/
#~ 
#~ 
#~ main : main.o netanalyser.o traceroute.o ping.o common.o log.o | bin
	#~ /usr/bin/gcc $(CFLAGS) -o $@ $(OPATH)main.o $(OPATH)netanalyser.o $(OPATH)traceroute.o $(OPATH)ping.o $(OPATH)common.o $(OPATH)log.o
	#~ /bin/mv $@ bin/
#~ 
#~ main_ping : main_ping.o ping.o common.o log.o | bin
	#~ /usr/bin/gcc $(CFLAGS) -o $@ $(OPATH)main_ping.o $(OPATH)ping.o $(OPATH)common.o $(OPATH)log.o
	#~ /bin/mv $@ bin/
#~ 
#~ main_traceroute : main_traceroute.o traceroute.o common.o log.o | bin
	#~ /usr/bin/gcc $(CFLAGS) -o $@ $(OPATH)main_traceroute.o $(OPATH)traceroute.o $(OPATH)common.o $(OPATH)log.o
	#~ /bin/mv $@ bin/
#~ 
#~ all : main main_ping main_traceroute
	#~ 
#~ 
#~ main.o : main.c netanalyser.h traceroute.h ping.h common.h log.h
#~ main_traceroute.o : main_traceroute.c traceroute.h common.h
#~ main_ping.o : main_ping.c ping.h common.h
#~ netanalyser.o : netanalyser.c netanalyser.h traceroute.h ping.h common.h log.h
#~ traceroute.o : traceroute.c traceroute.h common.h
#~ ping.o : ping.c ping.h common.h
#~ common.o : common.c common.h
#~ log.o : log.c log.h
#~ 
#~ %.o : | obj
	#~ /usr/bin/gcc $(CFLAGS) -c $<
	#~ /bin/mv $@ $(OPATH)
#~ 
#~ doc :
	#~ /usr/bin/doxygen Doxyfile
#~ 
#~ obj : 
	#~ /bin/mkdir obj
#~ 
#~ bin : 
	#~ /bin/mkdir bin
#~ 
#~ clean : 
	#~ /bin/rm -rf obj bin
	#~ 
#~ cleanlog :
	#~ /bin/rm Log*
	#~ 
#~ cleanall : clean
	#~ /bin/rm -rf doc
#~ 
#~ archive : 
	#~ /bin/tar -f projetRP.tar.gz -cvz src/*.c include/*.h Makefile Doxyfile netanalyser ping traceroute Rapport.pdf

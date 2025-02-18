#------------------------------------------------------------------------------
# File:   Makefile
# Author: Programación II-Unizar
# Date:   febrero 2022
# Coms:   Ejemplo de Makefile (Make_pruebas01) para la práctica 2 de Programación II
#------------------------------------------------------------------------------

#---------------------------------------------------------
# Definición de macros
CC = g++
CPPFLAGS = -I. -std=c++11      #opciones de compilación
LDFLAGS =                      #opciones de linkado
RM = rm -f                     #comando para borrar ficheros

all: pruebas01
#-----------------------------------------------------------
# Compilacion
main.o: main.cpp
	${CC} -c main.cpp ${CPPFLAGS}
# Linkado
noticia: main.o Publisher.o
	${CC} main.o Publisher.o -o noticia ${LDFLAGS}
main.o: main.cpp Publisher.hpp Noticia.hpp Observer.hpp
	${CC} -c main.cpp ${CPPFLAGS}
Publisher.o: Publisher.cpp Publisher.hpp 
	${CC} -c Publisher.cpp ${CPPFLAGS}
#-----------------------------------------------------------	
# LIMPIEZA de todo lo generado. Ojo: se borrarán sin preguntar
clean:
	${RM} *.o noticia
#include "Observer.hpp"
#include "Publisher.hpp"
#include <iostream>

const string listaTemas[5] = {"Deportes", "Politica", "Tecnologia", "Actualidad", "Corazon"};

const string nombreFichero = "fichero.txt";

int main() {
    Publisher escritor(nombreFichero);

    escritor.publicarNoticia(listaTemas[0], "Vinicius es sorprendido en el club Petalos");
    escritor.publicarNoticia(listaTemas[1], "Se aprueba la ley Enda");
    escritor.publicarNoticia(listaTemas[2], "El desarrollo de esta aplicacion va por buen camino");
    escritor.publicarNoticia(listaTemas[2], "Aprobados gratis en Arquitectura Software");

    Observer suscriptor1("Manuel", &escritor);
    suscriptor1.suscribirse(listaTemas[0]);
    suscriptor1.suscribirse(listaTemas[1]);

    Observer suscriptor2("Jesus", &escritor);
    suscriptor2.suscribirse(listaTemas[0]);
    suscriptor2.suscribirse(listaTemas[2]);

    Observer suscriptor3("Izan", &escritor);
    suscriptor3.suscribirse(listaTemas[2]);
    suscriptor3.suscribirse(listaTemas[3]);

    suscriptor1.refresh();
    suscriptor2.refresh();
    suscriptor3.refresh();

    suscriptor3.suscribirse(listaTemas[1]);

    escritor.publicarNoticia(listaTemas[1], "Otro robo mas");

    suscriptor1.refresh();
    suscriptor3.refresh();
}
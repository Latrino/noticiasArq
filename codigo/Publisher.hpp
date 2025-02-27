#ifndef PUBLISHER_HPP
#define PUBLISHER_HPP

#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <set>
#include "Noticia.hpp"

using namespace std;

class Publisher {
    private:
        string nombreFichero;
        vector<Noticia> listaNoticias; // ahora mismo no se usa, puede ser util para funcionalidades futuras
    
    public:
        Publisher(const string& _nombreFichero) : nombreFichero(_nombreFichero) {};
        void publicarNoticia(const string& tema, const string& noticia);
        vector<Noticia> mostrarNoticiasPorTema(const set <string>& tema);

};

#endif // PUBLISHER_HPP
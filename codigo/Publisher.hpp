#include <iostream>
#include <vector>
#include <string>
#include <map>
#include <set>
#include <Noticia.hpp>

using namespace std;

class Publisher {
    private:
        string nombreFichero;
        vector<Noticia> listaNoticias;
    
    public:
        Publisher(const string& _nombreFichero) : nombreFichero(_nombreFichero) {};
        void publicarNoticia(const string& tema, const string& noticia);
        vector<Noticia> mostrarNoticiasPorTema(const set <string>& tema);

};
#include <iostream>
#include <string>
#include <set>

#include "Publisher.hpp"
#include "Noticia.hpp"

using namespace std;

class Observer {
    private:
    string nombre;
    set<string> temasSeguidos;
    Publisher* publisher; // esto es para manejar el pull, necesitamos guardar el publisher para pedirle las noticias a el en el refresh

    public:
    Observer(const string& _nombre, Publisher* _publisher) : nombre(_nombre), publisher(_publisher) {};

    void suscribirse(const string& tema) {
        temasSeguidos.insert(tema);
    }

    void refresh() {
        cout << "Noticias para " << nombre << endl;
        for (auto tema : temasSeguidos) {
            cout << " - " << tema << endl;
        }
        vector<Noticia> noticias = publisher->mostrarNoticiasPorTema(temasSeguidos);
        for (const Noticia& n : noticias) {
            cout << "-------------------------------------" << endl;
            cout << " - " << n.getTema() << ": " << n.getNoticia() << endl;
            cout << "-------------------------------------" << endl;
        }
    }
};
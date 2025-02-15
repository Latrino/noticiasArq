#include "Publisher.hpp"
#include <fstream>

void Publisher::publicarNoticia(const string& tema, const string& noticia) {
    Noticia n(tema, noticia);
    ofstream fichero(nombreFichero, ios::app);
    if (fichero.is_open()) {
        fichero << n.toString() << endl;
        fichero.close();
        listaNoticias.push_back(n);
    }
    else {
        cout << "Error al abrir el fichero" << endl;
    }
}

vector<Noticia> Publisher::mostrarNoticiasPorTema(const set <string>& temas) {
    vector<Noticia> resultado;
    ifstream fichero(nombreFichero);
    string linea, temaNoticia, contenidoNoticia;
    bool leyendoNoticias = false;
    string contenidoAcumulado = "";
    string temaActual = "";

    if (fichero.is_open()) {
        while (getline(fichero, linea)) {
            if (linea == "-------------------------------------") {
                if (leyendoNoticias && temas.count(temaActual)) {
                    resultado.push_back(Noticia(temaActual, contenidoAcumulado));
                }
                leyendoNoticias = !leyendoNoticias;
                contenidoAcumulado.clear();
            } else if (leyendoNoticias) {
                size_t pos = linea.find("|");
                if (pos != string::npos) {
                    temaActual = linea.substr(0, pos);
                    contenidoAcumulado = linea.substr(pos + 1);
                } else {
                    contenidoAcumulado += "\n" + linea;
                }
            }
        }
    }
    else {
        cout << "Error al abrir el fichero" << endl;
    }
    
    return resultado;
}
#include <iostream>
#include <string>

using namespace std;

class Noticia {
    private:
        string tema;
        string contenido;
    
    public:
        Noticia(const string& _tema, const string& _contenido) : tema(_tema), contenido(_contenido) {};
        string toString() const { 
            return "-------------------------------------\n" + tema + "|" + contenido + "\n-------------------------------------"; 
        }
        string getTema() const { return tema; }
        string getNoticia() const { return contenido; }
};
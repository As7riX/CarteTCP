package engine.models;

import java.util.List;

public class Mazzo {
    private List<Carta> CarteMazzo;

    public Mazzo(int type){

        //creazione mazzo
        switch (type){

            case 5: {
                for (seme : Carta.Semi) {

                }
            }

            //mazzo singolo completo
            case 0: {
                for (int i = 0; i < 4; ++i)
                    for (int k = Carta::asso; k <= Carta::jolly; k++)
                        carte_mazzo.add(Carta(static_cast<Carta::semi>(i), static_cast<Carta::valore>(k)));
            }

            //mazzo doppio completo
            case 1: {
                for (int l = 0; l < 2; l++)
                    for (int i = Carta::cuori; i <= Carta::picche; ++i)
                        for (int k = Carta::asso; k <= Carta::jolly; k++)
                            carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), static_cast<Carta::valore>(k)));
            }

            //mazzo A, 2, 3, 4, 5, 6, 7, J, Q, K (briscola)
            case 2: {
                for (int i = Carta::cuori; i <= Carta::picche; i++) {
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::asso));
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::due));
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::tre));
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::quattro));
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::cinque));
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::sei));
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::sette));
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::fante));
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::donna));
                    carte_mazzo.push_back(Carta(static_cast<Carta::semi>(i), Carta::re));
                }
            }

            default:
                break;
        }
    }


    void Randomizza(){
        srand(time(0));
        random_shuffle(carte_mazzo.begin(), carte_mazzo.end());
    }

    /**
     *
     * @return
     */
    //funzione distribuisci prima carta
    Carta Mazzo::get_firstcard() {
        Carta temp = carte_mazzo.back();
        carte_mazzo.pop_back();

        return temp;
    }

    //funzione distribuisci ultima carta
    Carta Mazzo::get_lastcard() {
        Carta temp = carte_mazzo.front();
        carte_mazzo.erase(carte_mazzo.begin());

        return temp;
    }

    //funzione valore prima carta
    Carta Mazzo::get_firstcardval() {
        Carta temp = carte_mazzo.back();
        return temp;
    }

    //funzione valore ultima carta
    Carta Mazzo::get_lastcardval() {
        Carta temp = carte_mazzo.front();
        return temp;
    }

    //funzione check se vuoto
    bool Mazzo::is_empty() {
        if (carte_mazzo.size() == 0) return true;
        else return false;
    }

    //funzione impostazione punteggio
    void Mazzo::set_punteggio(int point[14]) {
        for (int i = 0; i < carte_mazzo.size(); i++)
            carte_mazzo[i].set_punteggio(point[carte_mazzo[i].get_valore()]);
    }

    //ricevi numero carte
    int Mazzo::get_ncard() {
        return carte_mazzo.size();
    }

    //aggiungi carta mazzo
    void Mazzo::add_card(Carta c, int p) {
        carte_mazzo.insert(carte_mazzo.begin() + p, c);
    }

    //aggiungi carta cima mazzo
    void Mazzo::add_cardup(Carta c) {
        carte_mazzo.push_back(c);
    }

    //aggiungi carta fondo mazzo
    void Mazzo::add_carddown(Carta c) {
        carte_mazzo.insert(carte_mazzo.begin(), c);
    }


}

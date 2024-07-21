package dao;

import java.util.List;

public interface BazaDateInterfata<T> {
    void adauga(T t);
    void actualizeaza(T t);
    void sterge(int id);
    T obtine(int id);
    List<T> obtineTot();
}

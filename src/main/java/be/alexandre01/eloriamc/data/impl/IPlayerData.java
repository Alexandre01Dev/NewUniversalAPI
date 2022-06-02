package be.alexandre01.eloriamc.data.impl;

import be.alexandre01.eloriamc.data.TypeData;

public interface IPlayerData {


    void savePlayer();

    void add(TypeData typeData, int i);

    void remove(TypeData typeData, int i);

    void set (TypeData typeData, int i);
}

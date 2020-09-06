package pl.jdev.opes_nuntius.mapper;

abstract class AbstractOandaMapper<O, E> {
    public abstract E convertToOandaEntity(O object);

    public abstract O convertToEntity(E oandaEntity);
}

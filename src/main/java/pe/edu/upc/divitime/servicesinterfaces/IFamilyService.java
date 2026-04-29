package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.Family;

import java.util.Optional;

public interface IFamilyService {
    public Optional<Family> listId(int id);
}

// Es una plantilla para probar el event (actualizarlo)
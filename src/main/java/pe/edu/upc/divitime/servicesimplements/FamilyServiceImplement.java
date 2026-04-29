package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.Family;
import pe.edu.upc.divitime.repositories.IFamilyRepository;
import pe.edu.upc.divitime.servicesinterfaces.IFamilyService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FamilyServiceImplement implements IFamilyService {

    @Autowired
    private IFamilyRepository fR;

    @Override
    public List<Family> list() {
        return fR.findAll();
    }

    @Override
    public Family insert(Family f) {
        return fR.save(f);
    }

    @Override
    public Optional<Family> listId(int id) {
        return fR.findById(id);
    }

    @Override
    public void update(Family f) {
        fR.save(f);
    }

    @Override
    public void delete(int id) {
        fR.deleteById(id);
    }

    @Override
    public List<Family> listarFamiliasPorRangoFechasJPQL(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return fR.listarFamiliasPorRangoFechasJPQL(fechaInicio, fechaFin);
    }
}

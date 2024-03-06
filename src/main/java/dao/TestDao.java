package dao;

import java.util.List;
import metier.entities.Moto;

public class TestDao {
    public static void main(String[] args) {
        MotoDaoImpl motoDao = new MotoDaoImpl();
        Moto moto = motoDao.save(new Moto("Yamaha MT-07", 9000));
        System.out.println(moto);
        List<Moto> motos = motoDao.motosParMC("Yamaha");
        for (Moto m : motos)
            System.out.println(m);
    }
}

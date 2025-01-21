package Zivotinja;

import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class zivotinjaKontroler extends korisni.Kontroler {

    private zivotinja Zivotinja = new zivotinja();

    public void unosNoveZivotinje(zivotinja ziv) throws SQLException {
        String sql = "INSERT INTO zivotinja (ime, vrsta, datumprijema, starost) VALUES ('" + ziv.getIme() + "', '" + ziv.getVrsta() + "', "
                + "'" + ziv.getDatumprijema() + "', '" + ziv.getStarost() + "')";
        InsDelUpd(sql);
    }

}

package Zivotinja;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class zivotinja {

    private int id;
    private float starost;
    private String ime,
            vrsta;
    private Date datumprijema;

    public zivotinja() {
    }

    public zivotinja(int id, String ime, String vrsta, Date datumprijema, float starost) {
        this.id = id;
        this.ime = ime;
        this.vrsta = vrsta;
        this.datumprijema = datumprijema;
        this.starost = starost;

    }
}

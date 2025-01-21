package Zivotinja;

import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@ManagedBean
@SessionScoped

public class zivotinjaPogled {

    private float starostZivotinje;
    private Date datumPrijema;
    private String imeZivotinje, vrstaZivotinje;
    private zivotinja ziv;
    private zivotinjaKontroler zivkont = new zivotinjaKontroler();

    private void poruka(String s) {
        FacesMessage message = new FacesMessage(s);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", message);
    }

    public String unosNoveZivotinje() {
        try {
            zivkont.unosNoveZivotinje(new zivotinja(0, imeZivotinje, vrstaZivotinje, datumPrijema, starostZivotinje));
            return "pocetna?faces-redirect=true";
        } catch (SQLException ex) {
            poruka("Greška u unosu zivotinje: " + ex.getMessage() + " " + ex.getSQLState());
        }
        return null;
    }
}

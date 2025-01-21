package Korisnik;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author home
 */
@Getter
@Setter
@ManagedBean
@SessionScoped
public class korisnikPogled {
    private String korisnickoIme, korisnickaLozinka, imeIPrezime;
    
    private korisnik k;
  
    private korisnikKontroler kont = new korisnikKontroler();
    private List<String> tipovi = new ArrayList<>();
    
     private void poruka(String s){
        FacesMessage message = new FacesMessage(s);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("", message);
    }
     
    private void inicijalisiDropDown(){
        getTipovi().add("ADMINISTRATOR"); getTipovi().add("KORISNIK"); 
    }
    
    public String registracija() throws SQLException{
        
        if(kont.login(korisnickoIme, korisnickaLozinka))
        {
            k= kont.getKorisnik();   
            korisnickoIme="";
            korisnickaLozinka="";
            return "pocetna?faces-redirect=true";
        }
        else {
            poruka("Netacni podaci!!!");
            return null;
        }
    }
    
//    public void unosNovogKorisnika() throws SQLException{
//        kont.UnesiKorisnika(new korisnik(0, ime, prezime, korisnickoIme, korisnickaLozinka)); 
//        poruka("Uspješan unos novog korisnika");
//    }
    public void azuriranjeKorisnika() throws SQLException{
        kont.azurirajKorisnika(k);
         poruka("Uspješan ažuriranje korisnika");
    }
    
     public String logOff() {       
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();     
        return "/index?faces-redirect=true";        
    }  
     public String unosNovogKorisnikaWeb() {        
        try { 
            kont.UnesiKorisnika(new korisnik(0, imeIPrezime, korisnickoIme, korisnickaLozinka,"KORISNIK"));             
            return "index?faces-redirect=true";
        } catch (SQLException ex) {
            poruka("Greška u unosu korisnika: "+ ex.getMessage() + " " + ex.getSQLState());
        }
        return null;       
    }
}